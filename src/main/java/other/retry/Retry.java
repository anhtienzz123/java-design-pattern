package other.retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Retry Pattern Implementation.
 *
 * Provides automatic retry functionality for operations that may fail
 * due to transient errors. Supports configurable retry policies with
 * various backoff strategies.
 *
 * @param <T> the type of result returned by the operation
 */
public final class Retry<T> {

    private final RetryPolicy<T> policy;
    private Consumer<RetryEvent> onRetry;
    private Consumer<RetryEvent> onSuccess;
    private Consumer<RetryEvent> onFailure;

    private Retry(RetryPolicy<T> policy) {
        this.policy = policy;
    }

    /**
     * Creates a Retry instance with the given policy.
     */
    public static <T> Retry<T> with(RetryPolicy<T> policy) {
        return new Retry<>(policy);
    }

    /**
     * Creates a Retry instance with default policy.
     */
    public static <T> Retry<T> withDefaults() {
        return new Retry<>(RetryPolicy.defaultPolicy());
    }

    /**
     * Registers a callback for retry events.
     */
    public Retry<T> onRetry(Consumer<RetryEvent> callback) {
        this.onRetry = callback;
        return this;
    }

    /**
     * Registers a callback for successful completion.
     */
    public Retry<T> onSuccess(Consumer<RetryEvent> callback) {
        this.onSuccess = callback;
        return this;
    }

    /**
     * Registers a callback for final failure.
     */
    public Retry<T> onFailure(Consumer<RetryEvent> callback) {
        this.onFailure = callback;
        return this;
    }

    /**
     * Executes the operation with retry logic.
     *
     * @param operation the operation to execute
     * @return the result of the operation
     * @throws RetryException if all attempts fail
     */
    public T execute(Callable<T> operation) {
        List<Throwable> exceptions = new ArrayList<>();
        int attempt = 0;

        while (attempt < policy.maxAttempts()) {
            attempt++;

            try {
                T result = operation.call();

                // Check if we should retry based on result
                if (policy.shouldRetryOnResult(result)) {
                    if (attempt < policy.maxAttempts()) {
                        notifyRetry(attempt, null, "Retry due to unsatisfactory result");
                        sleep(policy.calculateDelay(attempt));
                        continue;
                    }
                }

                notifySuccess(attempt);
                return result;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RetryException("Retry interrupted", attempt, e);

            } catch (Throwable e) {
                exceptions.add(e);

                if (!policy.shouldRetryOnException(e)) {
                    notifyFailure(attempt, e, "Non-retryable exception");
                    throw new RetryException("Non-retryable exception: " + e.getMessage(), attempt, exceptions);
                }

                if (attempt >= policy.maxAttempts()) {
                    notifyFailure(attempt, e, "Max attempts reached");
                    throw new RetryException("Operation failed: " + e.getMessage(), attempt, exceptions);
                }

                Duration delay = policy.calculateDelay(attempt);
                notifyRetry(attempt, e, "Retrying after " + delay.toMillis() + "ms");
                sleep(delay);
            }
        }

        throw new RetryException("Max attempts reached", attempt, exceptions);
    }

    /**
     * Executes a Runnable operation with retry logic.
     */
    public void run(Runnable operation) {
        execute(() -> {
            operation.run();
            return null;
        });
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RetryException("Retry interrupted during sleep", 0, e);
        }
    }

    private void notifyRetry(int attempt, Throwable exception, String message) {
        if (onRetry != null) {
            onRetry.accept(new RetryEvent(attempt, exception, message));
        }
    }

    private void notifySuccess(int attempt) {
        if (onSuccess != null) {
            onSuccess.accept(new RetryEvent(attempt, null, "Operation succeeded"));
        }
    }

    private void notifyFailure(int attempt, Throwable exception, String message) {
        if (onFailure != null) {
            onFailure.accept(new RetryEvent(attempt, exception, message));
        }
    }

    // ==================== STATIC CONVENIENCE METHODS ====================

    /**
     * Executes an operation with default retry policy.
     */
    public static <T> T call(Callable<T> operation) {
        return Retry.<T>withDefaults().execute(operation);
    }

    /**
     * Executes an operation with specified max attempts and delay.
     */
    public static <T> T call(Callable<T> operation, int maxAttempts, Duration delay) {
        RetryPolicy<T> policy = RetryPolicy.<T>builder()
                .maxAttempts(maxAttempts)
                .baseDelay(delay)
                .build();
        return Retry.with(policy).execute(operation);
    }

    /**
     * Executes an operation with exponential backoff.
     */
    public static <T> T callWithExponentialBackoff(Callable<T> operation, int maxAttempts, Duration baseDelay) {
        return Retry.with(RetryPolicy.<T>exponentialBackoff(maxAttempts, baseDelay))
                .execute(operation);
    }

    // ==================== RETRY EVENT ====================

    /**
     * Event object containing retry attempt information.
     */
    public record RetryEvent(int attempt, Throwable exception, String message) {
        @Override
        public String toString() {
            return "RetryEvent{attempt=%d, message='%s', exception=%s}"
                    .formatted(attempt, message, exception != null ? exception.getClass().getSimpleName() : "none");
        }
    }
}
