# Fluent Interface Pattern

## Intent

Design an API so calls can be **chained** and read like a sentence: each method returns an object (usually `this`) that the next call works on.

## Problem

A normal API makes the caller repeat the variable on every line, and the intent gets lost:

```java
ShoppingCart cart = new ShoppingCart();
cart.add("Coffee", 4.50, 2);
cart.add("Croissant", 3.00, 3);
cart.applyDiscount(10);
cart.printReceipt();
```

## Solution

Make each method return `this`, so the calls flow into each other:

```java
double total = new ShoppingCart()
        .add("Coffee", 4.50, 2)
        .add("Croissant", 3.00, 3)
        .applyDiscount(10)
        .printReceipt()
        .total();      // returns a value, so the chain ends here
```

A fluent interface is not only for builders. Here the cart is a live object: every call does real work right away. The `querybuilder` and `httprequestbuilder` use cases next to this one show the builder style, where the chain collects settings and `build()` creates the result.

## Structure

```
Client --> ShoppingCart
             add(...)          : ShoppingCart  (returns this)
             remove(...)       : ShoppingCart  (returns this)
             applyDiscount(...) : ShoppingCart (returns this)
             printReceipt()    : ShoppingCart  (returns this)
             total()           : double        (ends the chain)
```

## Participants

| Class | Role |
|-------|------|
| `ShoppingCart` | The fluent API. Chainable methods return `this`; `total()` ends the chain |
| `Item` | Simple data record for one cart line |
| `ZMain` | Client that writes the whole interaction as one chain |

## Run

```bash
java -cp target/classes other.fluentinterface.shoppingcart.ZMain
```

## Real-World Examples

Java `StringBuilder`, `Stream` API, AssertJ `assertThat(...).isNotNull().hasSize(3)`, `HttpRequest.newBuilder()`, jOOQ.
