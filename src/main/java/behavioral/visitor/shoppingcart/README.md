# Shopping Cart - Visitor Pattern Implementation

This implementation demonstrates the **Visitor Pattern** for calculating total prices of products in a shopping cart with different pricing rules for each product type.

## Pattern Overview

The Visitor Pattern allows you to define new operations without changing the classes of the elements on which it operates. It separates algorithms from the object structure they operate on.

## Key Components

### 1. Visitor Interface

-   **`ProductVisitor`**: Declares visit methods for each product type

### 2. Element Interface

-   **`Product`**: Defines the `accept()` method and common product properties

### 3. Concrete Elements

-   **`Book`**: Educational books get 15% discount
-   **`Electronics`**: 8% tax applied, extended warranty discount for 24+ months
-   **`Clothing`**: 20% discount for out-of-season items
-   **`Grocery`**: 30% discount for items expiring within 3 days, expired items excluded

### 4. Concrete Visitors

-   **`PriceCalculatorVisitor`**: Calculates total price with different rules per product type
-   **`ProductInfoVisitor`**: Collects statistics and product information

### 5. Context

-   **`ShoppingCart`**: Contains products and accepts visitors

## Pricing Rules

| Product Type    | Pricing Rules                                          |
| --------------- | ------------------------------------------------------ |
| **Books**       | Educational books: 15% discount                        |
| **Electronics** | 8% tax, Extended warranty (24+ months): 5% discount    |
| **Clothing**    | Out-of-season items: 20% discount                      |
| **Grocery**     | Near expiry (≤3 days): 30% discount, Expired: excluded |

## Benefits of Using Visitor Pattern

1. **Extensibility**: Easy to add new operations (visitors) without modifying product classes
2. **Separation of Concerns**: Pricing logic is separated from product data
3. **Single Responsibility**: Each visitor handles one specific operation
4. **Open/Closed Principle**: Open for extension (new visitors), closed for modification

## Usage Example

```java
// Create shopping cart
ShoppingCart cart = new ShoppingCart();

// Add products
cart.addProduct(new Book("Java Book", new BigDecimal("50.00"), 1, "123", true));
cart.addProduct(new Electronics("Laptop", new BigDecimal("1000.00"), 1, 36, "laptop"));

// Calculate total price
PriceCalculatorVisitor priceCalculator = new PriceCalculatorVisitor();
cart.acceptVisitor(priceCalculator);

System.out.println("Total: $" + priceCalculator.getTotalPrice());
```

## Running the Demo

```bash
# Compile
javac designpattern/behavioral/visitor/shoppingcart/*.java

# Run
java designpattern.behavioral.visitor.shoppingcart.ShoppingCartDemo
```

The demo shows:

-   Different products with various pricing rules
-   Educational book discount
-   Electronics with tax and warranty discount
-   Seasonal clothing discount
-   Near-expiry grocery discount
-   Product statistics collection

## Key Design Principles Applied

-   **SOLID Principles**: Single responsibility, Open/closed principle
-   **DRY**: No repetition of pricing logic
-   **KISS**: Simple, clear interface design
-   **YAGNI**: Only implemented necessary features
-   **Java 21 Features**: Switch expressions, records-style immutability
