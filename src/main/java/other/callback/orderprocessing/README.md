# Callback Pattern — Order Processing

## Intent

Pass **success** and **failure** callbacks to a service, so the service only decides the outcome and the client decides what happens next.

This example uses Java's built-in `Consumer<T>` as the callback type — no custom interface needed. (See `../filedownload/` for a custom callback interface and `../fileupload/` for a progress callback.)

## Problem

Without callbacks, the order service has to know every follow-up action:

```java
void placeOrder(Order order) {
    ... // check stock, charge payment
    emailService.sendConfirmation(order);   // what about the mobile app?
}
```

## Solution

The service takes two callbacks and calls exactly one of them:

```java
service.placeOrder(order,
        o -> sendEmail(o),        // onSuccess
        reason -> showError(reason)); // onFailure
```

A web shop and a mobile app can use the same `OrderService` with different callbacks.

## Structure

```
Client --placeOrder(order, onSuccess, onFailure)--> OrderService
                                                        |
                                    in stock?  --yes--> onSuccess.accept(order)
                                               --no---> onFailure.accept(reason)
```

## Participants

| Class | Role |
|-------|------|
| `Consumer<Order>` / `Consumer<String>` | Success / failure callbacks (built-in functional interfaces) |
| `OrderService` | Processes the order, then calls one callback |
| `Order` | Data passed to the success callback |
| `ZMain` | Client: chooses the callbacks |

## Run

```bash
java -cp target/classes other.callback.orderprocessing.ZMain
```

## Real-World Examples

`CompletableFuture.whenComplete`, `Optional.ifPresentOrElse(onValue, onEmpty)`, Retrofit `Callback.onResponse / onFailure`.
