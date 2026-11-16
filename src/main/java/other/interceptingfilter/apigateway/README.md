# Intercepting Filter Pattern

## Intent

Put a chain of **filters** in front of a request handler (the *target*), so common work like logging or authentication runs before the request reaches it — without the target knowing about it.

## Problem

Without filters, every handler repeats the same checks:

```java
String getOrders(Request req) {
    log(req);
    if (!validToken(req)) return "401";
    ... // real logic
}
```

## Solution

Move each check into its own `Filter`, and send every request through a `FilterManager`:

```java
FilterManager gateway = new FilterManager(new Target());
gateway.addFilter(new LoggingFilter());
gateway.addFilter(new AuthenticationFilter());

gateway.execute(new Request("/orders", "secret"));
```

A filter either calls `chain.proceed(request)` to pass the request on, or returns a response itself to stop it.

## Structure

```
Client -> FilterManager -> FilterChain
                              |
          LoggingFilter -> AuthenticationFilter -> Target
                               |
                               +-- wrong token: return "401", Target is never called
```

## Participants

| Class | Role |
|-------|------|
| `Filter` | Interface for one processing step |
| `LoggingFilter`, `AuthenticationFilter` | Concrete filters |
| `FilterChain` | Calls the filters in order, then the target |
| `Target` | The real handler (business logic) |
| `FilterManager` | Builds the chain; the client's entry point |

## Run

```bash
java -cp target/classes other.interceptingfilter.apigateway.ZMain
```

## Real-World Examples

Servlet `Filter`, Spring Security filter chain, Express.js middleware.
