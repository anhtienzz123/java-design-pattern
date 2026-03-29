# Specification Pattern

## Intent

The Specification pattern encapsulates **business rules** as reusable, composable objects that can be combined using boolean logic (AND, OR, NOT) to create complex selection criteria.

## Problem

Business logic for filtering, validation, or querying often becomes scattered across the codebase:

```java
// Without Specification - logic scattered and duplicated
public List<Product> findAffordableElectronics(List<Product> products) {
    List<Product> result = new ArrayList<>();
    for (Product p : products) {
        if (p.getCategory() == ELECTRONICS
            && p.getPrice() <= 100
            && p.isInStock()) {
            result.add(p);
        }
    }
    return result;
}

// Same logic duplicated elsewhere with slight variations...
```

## Solution

Encapsulate each business rule as a Specification object:

```java
// With Specification - composable and reusable
Specification<Product> spec = inCategory(ELECTRONICS)
    .and(priceLessThanOrEqual(100))
    .and(inStock());

List<Product> result = spec.filter(products);
```

## Structure

```
┌─────────────────────────────────────────────────────────┐
│                   Specification<T>                       │
├─────────────────────────────────────────────────────────┤
│  + isSatisfiedBy(candidate: T): boolean                 │
│  + and(other: Specification<T>): Specification<T>       │
│  + or(other: Specification<T>): Specification<T>        │
│  + not(): Specification<T>                              │
│  + filter(collection: Collection<T>): List<T>           │
└─────────────────────────────────────────────────────────┘
                           △
                           │ implements
         ┌─────────────────┼─────────────────┐
         │                 │                 │
┌────────┴────────┐ ┌──────┴──────┐ ┌────────┴────────┐
│ PriceSpec       │ │ CategorySpec│ │ InStockSpec     │
│ isSatisfiedBy() │ │             │ │                 │
└─────────────────┘ └─────────────┘ └─────────────────┘
```

## Implementation

### Core Interface

```java
@FunctionalInterface
public interface Specification<T> {
    boolean isSatisfiedBy(T candidate);

    default Specification<T> and(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate)
                         && other.isSatisfiedBy(candidate);
    }

    default Specification<T> or(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate)
                         || other.isSatisfiedBy(candidate);
    }

    default Specification<T> not() {
        return candidate -> !this.isSatisfiedBy(candidate);
    }
}
```

### Concrete Specifications

```java
public static Specification<Product> inCategory(Category category) {
    return product -> product.category() == category;
}

public static Specification<Product> priceLessThanOrEqual(double max) {
    return product -> product.price().compareTo(BigDecimal.valueOf(max)) <= 0;
}

public static Specification<Product> inStock() {
    return Product::isInStock;
}
```

### Combining Specifications

```java
// Simple AND
Specification<Product> cheapElectronics =
    inCategory(ELECTRONICS).and(priceLessThanOrEqual(50));

// Complex combination
Specification<Product> holidayDeals =
    featured()
        .or(highlyRated()
            .and(priceLessThanOrEqual(100))
            .and(inStock()));

// Using NOT
Specification<Product> notClothing = inCategory(CLOTHING).not();
```

## Usage Examples

### Filtering Collections

```java
List<Product> products = getProducts();

// Filter using specification
List<Product> results = inStock()
    .and(priceBetween(20, 100))
    .filter(products);
```

### Aggregate Operations

```java
Specification<Product> spec = highlyRated().and(inStock());

long count = spec.count(products);          // Count matches
boolean any = spec.anyMatch(products);      // Any matches?
boolean all = spec.allMatch(products);      // All match?
```

### With Java Streams

```java
products.stream()
    .filter(inStock().and(featured()).toPredicate())
    .forEach(System.out::println);
```

## When to Use

| Use Case | Example |
|----------|---------|
| **Filtering** | Product search with multiple criteria |
| **Validation** | Order validation rules |
| **Authorization** | Access control policies |
| **Querying** | Dynamic query building |
| **Business Rules** | Discount eligibility, promotions |

## Benefits

| Benefit | Description |
|---------|-------------|
| **Composable** | Build complex rules from simple ones |
| **Reusable** | Define once, use across the application |
| **Readable** | Rules read like natural language |
| **Testable** | Each spec can be unit tested in isolation |
| **Open/Closed** | Add new specs without modifying existing ones |

## Comparison with Alternatives

| Approach | Pros | Cons |
|----------|------|------|
| **Inline predicates** | Simple | Not reusable, scattered logic |
| **Query methods** | Encapsulated | Explosion of method combinations |
| **Specification** | Composable, reusable | Slightly more complex setup |

## Running the Demo

```bash
# Compile
mvn compile

# Run demonstration
mvn exec:java -Dexec.mainClass="other.specification.ZMain"
```

## Real-World Applications

- **E-commerce**: Product filtering, discount rules, inventory alerts
- **Banking**: Loan eligibility, fraud detection rules
- **HR Systems**: Employee search, policy compliance
- **CMS**: Content filtering, access control
- **Gaming**: Achievement criteria, matchmaking rules

## Related Patterns

- **Strategy**: Specification can be seen as a specialized Strategy for boolean conditions
- **Composite**: AND/OR specifications form a composite structure
- **Interpreter**: Complex specifications can form a mini-DSL
