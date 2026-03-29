package other.retry;

import java.util.List;

/**
 * Exception thrown when all retry attempts have been exhausted.
 */
public class RetryException extends RuntimeException {

    private final int attempts;
    private final List<Throwable> allExceptions;

    public RetryException(String message, int attempts, List<Throwable> allExceptions) {
        super(message, allExceptions.isEmpty() ? null : allExceptions.getLast());
        this.attempts = attempts;
        this.allExceptions = List.copyOf(allExceptions);
    }

    public RetryException(String message, int attempts, Throwable lastException) {
        super(message, lastException);
        this.attempts = attempts;
        this.allExceptions = lastException != null ? List.of(lastException) : List.of();
    }

    /**
     * Returns the number of attempts made before giving up.
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Returns all exceptions that occurred during retry attempts.
     */
    public List<Throwable> getAllExceptions() {
        return allExceptions;
    }

    @Override
    public String getMessage() {
        return "%s (after %d attempts)".formatted(super.getMessage(), attempts);
    }
}
