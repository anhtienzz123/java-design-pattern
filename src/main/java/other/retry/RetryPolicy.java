package other.retry;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Configuration for retry behavior.
 * Uses the Builder pattern for flexible configuration.
 *
 * @param <T> the type of result expected from the operation
 */
public final class RetryPolicy<T> {

    private final int maxAttempts;
    private final Duration baseDelay;
    private final BackoffStrategy backoffStrategy;
    private final Set<Class<? extends Throwable>> retryableExceptions;
    private final Set<Class<? extends Throwable>> nonRetryableExceptions;
    private final Predicate<T> retryOnResult;
    private final Duration maxDelay;

    private RetryPolicy(Builder<T> builder) {
        this.maxAttempts = builder.maxAttempts;
        this.baseDelay = builder.baseDelay;
        this.backoffStrategy = builder.backoffStrategy;
        this.retryableExceptions = Set.copyOf(builder.retryableExceptions);
        this.nonRetryableExceptions = Set.copyOf(builder.nonRetryableExceptions);
        this.retryOnResult = builder.retryOnResult;
        this.maxDelay = builder.maxDelay;
    }

    public int maxAttempts() {
        return maxAttempts;
    }

    public Duration baseDelay() {
        return baseDelay;
    }

    public Duration maxDelay() {
        return maxDelay;
    }

    public Duration calculateDelay(int attempt) {
        Duration delay = backoffStrategy.calculateDelay(attempt, baseDelay);
        if (maxDelay != null && delay.compareTo(maxDelay) > 0) {
            return maxDelay;
        }
        return delay;
    }

    public boolean shouldRetryOnException(Throwable throwable) {
        Class<? extends Throwable> exClass = throwable.getClass();

        // Check non-retryable first
        for (Class<? extends Throwable> nonRetryable : nonRetryableExceptions) {
            if (nonRetryable.isAssignableFrom(exClass)) {
                return false;
            }
        }

        // If retryable list is empty, retry all (except non-retryable)
        if (retryableExceptions.isEmpty()) {
            return true;
        }

        // Check if exception is retryable
        for (Class<? extends Throwable> retryable : retryableExceptions) {
            if (retryable.isAssignableFrom(exClass)) {
                return true;
            }
        }

        return false;
    }

    public boolean shouldRetryOnResult(T result) {
        return retryOnResult != null && retryOnResult.test(result);
    }

    @Override
    public String toString() {
        return "RetryPolicy{maxAttempts=%d, baseDelay=%s, backoff=%s}"
                .formatted(maxAttempts, baseDelay, backoffStrategy.getClass().getSimpleName());
    }

    // ==================== BUILDER ====================

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Creates a default policy: 3 attempts with 1 second fixed delay.
     */
    public static <T> RetryPolicy<T> defaultPolicy() {
        return RetryPolicy.<T>builder().build();
    }

    /**
     * Creates a policy with exponential backoff.
     */
    public static <T> RetryPolicy<T> exponentialBackoff(int maxAttempts, Duration baseDelay) {
        return RetryPolicy.<T>builder()
                .maxAttempts(maxAttempts)
                .baseDelay(baseDelay)
                .exponentialBackoff()
                .build();
    }

    public static final class Builder<T> {
        private int maxAttempts = 3;
        private Duration baseDelay = Duration.ofSeconds(1);
        private BackoffStrategy backoffStrategy = BackoffStrategy.fixed();
        private final Set<Class<? extends Throwable>> retryableExceptions = new HashSet<>();
        private final Set<Class<? extends Throwable>> nonRetryableExceptions = new HashSet<>();
        private Predicate<T> retryOnResult;
        private Duration maxDelay;

        private Builder() {}

        public Builder<T> maxAttempts(int maxAttempts) {
            if (maxAttempts < 1) {
                throw new IllegalArgumentException("maxAttempts must be at least 1");
            }
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder<T> baseDelay(Duration baseDelay) {
            this.baseDelay = baseDelay;
            return this;
        }

        public Builder<T> maxDelay(Duration maxDelay) {
            this.maxDelay = maxDelay;
            return this;
        }

        public Builder<T> backoffStrategy(BackoffStrategy strategy) {
            this.backoffStrategy = strategy;
            return this;
        }

        public Builder<T> fixedDelay() {
            this.backoffStrategy = BackoffStrategy.fixed();
            return this;
        }

        public Builder<T> exponentialBackoff() {
            this.backoffStrategy = BackoffStrategy.exponential();
            return this;
        }

        public Builder<T> linearBackoff() {
            this.backoffStrategy = BackoffStrategy.linear();
            return this;
        }

        public Builder<T> withJitter(double jitterFactor) {
            this.backoffStrategy = this.backoffStrategy.withJitter(jitterFactor);
            return this;
        }

        @SafeVarargs
        public final Builder<T> retryOn(Class<? extends Throwable>... exceptions) {
            for (Class<? extends Throwable> ex : exceptions) {
                this.retryableExceptions.add(ex);
            }
            return this;
        }

        @SafeVarargs
        public final Builder<T> doNotRetryOn(Class<? extends Throwable>... exceptions) {
            for (Class<? extends Throwable> ex : exceptions) {
                this.nonRetryableExceptions.add(ex);
            }
            return this;
        }

        public Builder<T> retryOnResult(Predicate<T> predicate) {
            this.retryOnResult = predicate;
            return this;
        }

        public RetryPolicy<T> build() {
            return new RetryPolicy<>(this);
        }
    }
}
