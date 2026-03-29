# Retry Pattern

## Intent

The Retry pattern handles **transient failures** by automatically retrying failed operations with configurable policies. It provides resilience against temporary issues like network timeouts, database connection drops, or service unavailability.

## Problem

Distributed systems and external service calls can fail temporarily:

```java
// Without Retry - single failure causes complete failure
try {
    String data = httpClient.fetchData("/api/users");
} catch (IOException e) {
    // Operation failed - no recovery
    throw new ServiceException("API unavailable", e);
}
```

## Solution

Wrap operations with retry logic that handles transient failures:

```java
// With Retry - automatic recovery from transient failures
String data = Retry.with(RetryPolicy.<String>builder()
        .maxAttempts(5)
        .baseDelay(Duration.ofSeconds(1))
        .exponentialBackoff()
        .build())
    .execute(() -> httpClient.fetchData("/api/users"));
```

## Structure

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│     Retry<T>    │────▶│  RetryPolicy<T> │────▶│ BackoffStrategy │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ execute()       │     │ maxAttempts     │     │ fixed()         │
│ onRetry()       │     │ baseDelay       │     │ exponential()   │
│ onSuccess()     │     │ calculateDelay()│     │ linear()        │
│ onFailure()     │     │ shouldRetry()   │     │ withJitter()    │
└─────────────────┘     └─────────────────┘     └─────────────────┘
         │
         ▼
┌─────────────────┐
│ RetryException  │
├─────────────────┤
│ attempts        │
│ allExceptions   │
└─────────────────┘
```

## Implementation

### Basic Usage

```java
RetryPolicy<String> policy = RetryPolicy.<String>builder()
    .maxAttempts(3)
    .baseDelay(Duration.ofSeconds(1))
    .build();

String result = Retry.with(policy)
    .execute(() -> riskyOperation());
```

### Backoff Strategies

```java
// Fixed delay: always wait the same amount
.fixedDelay()  // 1s -> 1s -> 1s

// Exponential backoff: delay doubles each time
.exponentialBackoff()  // 1s -> 2s -> 4s -> 8s

// Linear backoff: delay increases linearly
.linearBackoff()  // 1s -> 2s -> 3s -> 4s

// With jitter to prevent thundering herd
.exponentialBackoff()
.withJitter(0.2)  // Adds ±20% randomness
```

### Exception Filtering

```java
RetryPolicy<String> policy = RetryPolicy.<String>builder()
    .maxAttempts(5)
    .retryOn(IOException.class, TimeoutException.class)
    .doNotRetryOn(AuthenticationException.class)
    .build();
```

### Retry on Result

```java
// Retry if the result indicates a pending state
RetryPolicy<PaymentResult> policy = RetryPolicy.<PaymentResult>builder()
    .maxAttempts(5)
    .retryOnResult(result -> result.isPending())
    .build();
```

### Event Callbacks

```java
Retry.with(policy)
    .onRetry(e -> log.warn("Retry attempt {}", e.attempt()))
    .onSuccess(e -> log.info("Succeeded on attempt {}", e.attempt()))
    .onFailure(e -> log.error("Failed after {} attempts", e.attempt()))
    .execute(() -> operation());
```

## Backoff Strategy Comparison

| Strategy | Pattern | Use Case |
|----------|---------|----------|
| **Fixed** | 1s → 1s → 1s | Simple cases, predictable delays |
| **Exponential** | 1s → 2s → 4s → 8s | Rate-limited APIs, overloaded services |
| **Linear** | 1s → 2s → 3s → 4s | Moderate backoff needs |
| **With Jitter** | Random ±X% | Multiple clients, prevent thundering herd |

## Configuration Options

| Option | Description | Default |
|--------|-------------|---------|
| `maxAttempts` | Maximum retry attempts | 3 |
| `baseDelay` | Initial delay between retries | 1 second |
| `maxDelay` | Maximum delay cap | No limit |
| `backoffStrategy` | Delay calculation strategy | Fixed |
| `retryOn` | Exceptions to retry on | All exceptions |
| `doNotRetryOn` | Exceptions to fail immediately | None |
| `retryOnResult` | Predicate to retry based on result | None |

## When to Use

| Scenario | Example |
|----------|---------|
| **Network calls** | HTTP requests, RPC calls |
| **Database operations** | Connection pool exhaustion |
| **File operations** | Temporary file locks |
| **External services** | Payment gateways, email services |
| **Message queues** | Temporary broker unavailability |

## When NOT to Use

| Scenario | Reason |
|----------|--------|
| **Invalid input** | Will never succeed |
| **Authentication failures** | Credentials won't magically become valid |
| **Business rule violations** | Logic errors need fixing, not retrying |
| **Resource not found** | 404 errors won't resolve themselves |

## Benefits

| Benefit | Description |
|---------|-------------|
| **Resilience** | Handles transient failures gracefully |
| **Configurable** | Flexible policies for different scenarios |
| **Observable** | Event callbacks for monitoring |
| **Type-safe** | Generic implementation |
| **Composable** | Works with any `Callable`, `Supplier`, or `Runnable` |

## Running the Demo

```bash
# Compile
mvn compile

# Run demonstration
mvn exec:java -Dexec.mainClass="other.retry.ZMain"
```

## Real-World Applications

- **Microservices**: Inter-service communication
- **Cloud Applications**: AWS/GCP API calls
- **Payment Systems**: Transaction processing
- **Data Pipelines**: ETL operations
- **IoT**: Device communication

## Related Patterns

- **Circuit Breaker**: Prevents calling failing services repeatedly
- **Timeout**: Sets maximum wait time for operations
- **Fallback**: Provides alternative when retries fail
- **Bulkhead**: Isolates failures to prevent cascade
