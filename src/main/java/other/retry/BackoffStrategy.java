package other.retry;

import java.time.Duration;
import java.util.Random;

/**
 * Strategy interface for calculating delay between retry attempts.
 */
@FunctionalInterface
public interface BackoffStrategy {

    /**
     * Calculates the delay before the next retry attempt.
     *
     * @param attempt the current attempt number (1-based)
     * @param baseDelay the base delay duration
     * @return the delay duration before the next attempt
     */
    Duration calculateDelay(int attempt, Duration baseDelay);

    /**
     * Fixed delay between retries.
     */
    static BackoffStrategy fixed() {
        return (attempt, baseDelay) -> baseDelay;
    }

    /**
     * Exponential backoff: delay doubles with each attempt.
     * delay = baseDelay * 2^(attempt-1)
     */
    static BackoffStrategy exponential() {
        return (attempt, baseDelay) -> {
            long multiplier = 1L << (attempt - 1); // 2^(attempt-1)
            return baseDelay.multipliedBy(multiplier);
        };
    }

    /**
     * Exponential backoff with a maximum cap.
     */
    static BackoffStrategy exponentialWithCap(Duration maxDelay) {
        return (attempt, baseDelay) -> {
            long multiplier = 1L << (attempt - 1);
            Duration calculated = baseDelay.multipliedBy(multiplier);
            return calculated.compareTo(maxDelay) > 0 ? maxDelay : calculated;
        };
    }

    /**
     * Linear backoff: delay increases linearly.
     * delay = baseDelay * attempt
     */
    static BackoffStrategy linear() {
        return (attempt, baseDelay) -> baseDelay.multipliedBy(attempt);
    }

    /**
     * Adds random jitter to prevent thundering herd problem.
     *
     * @param jitterFactor factor between 0 and 1 (e.g., 0.2 for ±20% jitter)
     */
    default BackoffStrategy withJitter(double jitterFactor) {
        Random random = new Random();
        return (attempt, baseDelay) -> {
            Duration base = this.calculateDelay(attempt, baseDelay);
            double jitter = 1.0 + (random.nextDouble() * 2 - 1) * jitterFactor;
            return Duration.ofMillis((long) (base.toMillis() * jitter));
        };
    }
}
