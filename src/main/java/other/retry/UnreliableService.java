package other.retry;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simulated unreliable services for demonstrating the Retry pattern.
 * These services simulate real-world transient failures.
 */
public final class UnreliableService {

    private UnreliableService() {}

    // ==================== HTTP SERVICE ====================

    /**
     * Simulates an HTTP API that fails intermittently.
     */
    public static class HttpApiClient {
        private final double failureRate;
        private final AtomicInteger callCount = new AtomicInteger(0);
        private final Random random = new Random();

        public HttpApiClient(double failureRate) {
            this.failureRate = failureRate;
        }

        public String fetchData(String endpoint) throws IOException {
            int attempt = callCount.incrementAndGet();
            simulateNetworkDelay();

            if (random.nextDouble() < failureRate) {
                // Simulate various network errors
                int errorType = random.nextInt(3);
                throw switch (errorType) {
                    case 0 -> new ConnectException("Connection refused to " + endpoint);
                    case 1 -> new SocketTimeoutException("Request timed out for " + endpoint);
                    default -> new IOException("HTTP 503 Service Unavailable");
                };
            }

            return "Response from %s (attempt #%d)".formatted(endpoint, attempt);
        }

        public int getCallCount() {
            return callCount.get();
        }

        public void resetCallCount() {
            callCount.set(0);
        }

        private void simulateNetworkDelay() {
            try {
                Thread.sleep(50 + random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ==================== DATABASE SERVICE ====================

    /**
     * Simulates a database that may have transient connection issues.
     */
    public static class DatabaseClient {
        private final int failuresBeforeSuccess;
        private final AtomicInteger attemptCount = new AtomicInteger(0);

        public DatabaseClient(int failuresBeforeSuccess) {
            this.failuresBeforeSuccess = failuresBeforeSuccess;
        }

        public String executeQuery(String query) {
            int attempt = attemptCount.incrementAndGet();

            if (attempt <= failuresBeforeSuccess) {
                throw new DatabaseException("Database connection pool exhausted (attempt " + attempt + ")");
            }

            return "Query result for: %s (succeeded on attempt #%d)".formatted(query, attempt);
        }

        public void reset() {
            attemptCount.set(0);
        }

        public int getAttemptCount() {
            return attemptCount.get();
        }
    }

    public static class DatabaseException extends RuntimeException {
        public DatabaseException(String message) {
            super(message);
        }
    }

    // ==================== PAYMENT SERVICE ====================

    /**
     * Simulates a payment gateway with various failure modes.
     */
    public static class PaymentGateway {
        private final AtomicInteger attemptCount = new AtomicInteger(0);
        private final int successOnAttempt;

        public PaymentGateway(int successOnAttempt) {
            this.successOnAttempt = successOnAttempt;
        }

        public PaymentResult processPayment(String orderId, double amount) {
            int attempt = attemptCount.incrementAndGet();

            if (attempt < successOnAttempt) {
                // Return a retryable failed result
                return new PaymentResult(false, "PENDING",
                        "Payment processing, please retry (attempt " + attempt + ")");
            }

            return new PaymentResult(true, "SUCCESS",
                    "Payment of $%.2f for order %s completed".formatted(amount, orderId));
        }

        public void reset() {
            attemptCount.set(0);
        }
    }

    public record PaymentResult(boolean success, String status, String message) {
        public boolean isPending() {
            return "PENDING".equals(status);
        }
    }

    // ==================== FILE SERVICE ====================

    /**
     * Simulates a file service that may encounter temporary lock issues.
     */
    public static class FileService {
        private final AtomicInteger attemptCount = new AtomicInteger(0);
        private final int lockedUntilAttempt;

        public FileService(int lockedUntilAttempt) {
            this.lockedUntilAttempt = lockedUntilAttempt;
        }

        public String readFile(String path) throws FileLockedException {
            int attempt = attemptCount.incrementAndGet();

            if (attempt < lockedUntilAttempt) {
                throw new FileLockedException("File %s is locked by another process".formatted(path));
            }

            return "Contents of %s (read on attempt #%d)".formatted(path, attempt);
        }

        public void reset() {
            attemptCount.set(0);
        }
    }

    public static class FileLockedException extends Exception {
        public FileLockedException(String message) {
            super(message);
        }
    }

    /**
     * Non-retryable exception for authorization failures.
     */
    public static class AuthorizationException extends RuntimeException {
        public AuthorizationException(String message) {
            super(message);
        }
    }
}
