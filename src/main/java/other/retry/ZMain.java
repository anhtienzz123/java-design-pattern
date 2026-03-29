package other.retry;

import java.io.IOException;
import java.time.Duration;

import other.retry.UnreliableService.*;

/**
 * Demonstration of the Retry Pattern.
 *
 * The Retry pattern handles transient failures by automatically retrying
 * failed operations with configurable policies:
 * - Maximum attempts
 * - Backoff strategies (fixed, exponential, linear)
 * - Jitter to prevent thundering herd
 * - Retryable/non-retryable exception filtering
 * - Result-based retry conditions
 *
 * Use Cases:
 * - Network calls (HTTP, RPC)
 * - Database operations
 * - File system operations
 * - External service integrations
 * - Message queue operations
 */
public class ZMain {

    public static void main(String[] args) {
        System.out.println("=== Retry Pattern Demo ===\n");

        example1_SimpleRetry();
        example2_ExponentialBackoff();
        example3_CustomPolicy();
        example4_RetryOnResult();
        example5_NonRetryableException();
        example6_WithCallbacks();
        example7_StaticConvenienceMethods();

        System.out.println("\n=== Retry Pattern Benefits ===");
        System.out.println("""
                1. RESILIENCE: Handles transient failures gracefully
                2. CONFIGURABLE: Flexible policies for different scenarios
                3. BACKOFF STRATEGIES: Prevents overwhelming failing services
                4. OBSERVABLE: Callbacks for logging and monitoring
                5. TYPE-SAFE: Generic implementation works with any return type
                """);
    }

    private static void example1_SimpleRetry() {
        System.out.println("--- Example 1: Simple Retry with Fixed Delay ---");

        // Database that fails first 2 attempts
        DatabaseClient db = new DatabaseClient(2);

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(100))
                .fixedDelay()
                .build();

        String result = Retry.with(policy)
                .onRetry(e -> System.out.println("  Retry attempt " + e.attempt() + ": " + e.message()))
                .execute(() -> db.executeQuery("SELECT * FROM users"));

        System.out.println("  Result: " + result);
        System.out.println("  Total attempts: " + db.getAttemptCount());
        System.out.println();
    }

    private static void example2_ExponentialBackoff() {
        System.out.println("--- Example 2: Exponential Backoff ---");

        DatabaseClient db = new DatabaseClient(3);

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(50))
                .exponentialBackoff()
                .build();

        System.out.println("  Delays: 50ms -> 100ms -> 200ms -> 400ms");

        String result = Retry.with(policy)
                .onRetry(e -> {
                    Duration nextDelay = policy.calculateDelay(e.attempt());
                    System.out.println("  Attempt " + e.attempt() + " failed, waiting " + nextDelay.toMillis() + "ms");
                })
                .execute(() -> db.executeQuery("SELECT * FROM orders"));

        System.out.println("  Result: " + result);
        System.out.println();
    }

    private static void example3_CustomPolicy() {
        System.out.println("--- Example 3: Custom Policy with Exception Filtering ---");

        FileService fileService = new FileService(3);

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(100))
                .linearBackoff()
                .retryOn(FileLockedException.class)
                .doNotRetryOn(AuthorizationException.class)
                .build();

        try {
            String result = Retry.with(policy)
                    .onRetry(e -> System.out.println("  File locked, retry #" + e.attempt()))
                    .execute(() -> {
                        try {
                            return fileService.readFile("/data/config.json");
                        } catch (FileLockedException e) {
                            throw new RuntimeException(e);
                        }
                    });

            System.out.println("  Result: " + result);
        } catch (RetryException e) {
            System.out.println("  Failed after " + e.getAttempts() + " attempts");
        }
        System.out.println();
    }

    private static void example4_RetryOnResult() {
        System.out.println("--- Example 4: Retry Based on Result ---");

        PaymentGateway gateway = new PaymentGateway(3);

        RetryPolicy<PaymentResult> policy = RetryPolicy.<PaymentResult>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(200))
                .exponentialBackoff()
                .retryOnResult(PaymentResult::isPending) // Retry if payment is pending
                .build();

        PaymentResult result = Retry.with(policy)
                .onRetry(e -> System.out.println("  Payment pending, retrying..."))
                .execute(() -> gateway.processPayment("ORD-12345", 99.99));

        System.out.println("  Status: " + result.status());
        System.out.println("  Message: " + result.message());
        System.out.println();
    }

    private static void example5_NonRetryableException() {
        System.out.println("--- Example 5: Non-Retryable Exception ---");

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(100))
                .doNotRetryOn(AuthorizationException.class)
                .build();

        try {
            Retry.with(policy)
                    .onRetry(e -> System.out.println("  Retrying..."))
                    .execute(() -> {
                        throw new AuthorizationException("Invalid API key");
                    });
        } catch (RetryException e) {
            System.out.println("  Stopped immediately: " + e.getCause().getMessage());
            System.out.println("  Attempts made: " + e.getAttempts());
        }
        System.out.println();
    }

    private static void example6_WithCallbacks() {
        System.out.println("--- Example 6: With Event Callbacks ---");

        DatabaseClient db = new DatabaseClient(2);

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(50))
                .build();

        String result = Retry.with(policy)
                .onRetry(e -> System.out.println("  [RETRY] Attempt " + e.attempt() +
                        " - " + e.exception().getClass().getSimpleName()))
                .onSuccess(e -> System.out.println("  [SUCCESS] Completed on attempt " + e.attempt()))
                .onFailure(e -> System.out.println("  [FAILURE] " + e.message()))
                .execute(() -> db.executeQuery("INSERT INTO logs VALUES (...)"));

        System.out.println("  Result: " + result);
        System.out.println();
    }

    private static void example7_StaticConvenienceMethods() {
        System.out.println("--- Example 7: Static Convenience Methods ---");

        DatabaseClient db1 = new DatabaseClient(1);
        DatabaseClient db2 = new DatabaseClient(2);

        // Simple retry with defaults
        String result1 = Retry.call(() -> db1.executeQuery("query1"));
        System.out.println("  Default policy: " + result1);

        // Retry with custom attempts and delay
        String result2 = Retry.call(
                () -> db2.executeQuery("query2"),
                5,
                Duration.ofMillis(50)
        );
        System.out.println("  Custom policy: " + result2);

        System.out.println();
    }

    // ==================== HTTP EXAMPLE (longer running) ====================

    @SuppressWarnings("unused")
    private static void exampleHttpWithJitter() {
        System.out.println("--- HTTP API with Exponential Backoff + Jitter ---");

        HttpApiClient api = new HttpApiClient(0.7); // 70% failure rate

        RetryPolicy<String> policy = RetryPolicy.<String>builder()
                .maxAttempts(5)
                .baseDelay(Duration.ofMillis(100))
                .exponentialBackoff()
                .withJitter(0.2) // Add 20% random jitter
                .maxDelay(Duration.ofSeconds(2))
                .retryOn(IOException.class)
                .build();

        try {
            String result = Retry.with(policy)
                    .onRetry(e -> System.out.println("  API call failed: " + e.exception().getMessage()))
                    .execute(() -> api.fetchData("/api/users"));

            System.out.println("  Result: " + result);
        } catch (RetryException e) {
            System.out.println("  API unavailable after " + e.getAttempts() + " attempts");
        }
    }
}
