# Step Builder Pattern

## Intent

The Step Builder pattern is a variation of the Builder pattern that **enforces a specific order of method calls at compile time**. It guarantees that all required fields are set in the correct sequence before an object can be built.

## Problem

The traditional Builder pattern has a weakness: it relies on **runtime validation** to ensure required fields are set. This means:

```java
// Traditional Builder - compiles but fails at runtime!
Car car = new Car.Builder()
    .color("Red")      // Oops, forgot model!
    .build();          // RuntimeException: "Model is required"
```

## Solution

The Step Builder uses **separate interfaces for each step**, where each interface only exposes the next valid method:

```java
// Step Builder - won't compile if steps are missed!
LoanApplication loan = LoanApplicationStepBuilder
    .newApplication()
    .applicant("John")     // Returns AmountStep
    .amount(50000)         // Returns TermStep
    .termMonths(36)        // Returns RateStep
    .interestRate(5.5)     // Returns OptionalStep
    .build();              // Only available after all required steps
```

## Structure

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  ApplicantStep  │────▶│   AmountStep    │────▶│    TermStep     │
│  applicant()    │     │   amount()      │     │  termMonths()   │
└─────────────────┘     └─────────────────┘     └─────────────────┘
                                                         │
                                                         ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│ LoanApplication │◀────│  OptionalStep   │◀────│    RateStep     │
│    (Product)    │     │   purpose()     │     │ interestRate()  │
└─────────────────┘     │   collateral()  │     └─────────────────┘
                        │   withCoSigner()|
                        │   build()       │
                        └─────────────────┘
```

## Implementation

### Step Interfaces

```java
public interface ApplicantStep {
    AmountStep applicant(String name);
}

public interface AmountStep {
    TermStep amount(BigDecimal loanAmount);
}

public interface TermStep {
    RateStep termMonths(int months);
}

public interface RateStep {
    OptionalStep interestRate(BigDecimal rate);
}

public interface OptionalStep {
    OptionalStep purpose(String purpose);
    OptionalStep collateral(String collateral);
    OptionalStep withCoSigner();
    LoanApplication build();
}
```

### Builder Implementation

A single builder class implements all interfaces:

```java
private static final class Builder implements
        ApplicantStep, AmountStep, TermStep, RateStep, OptionalStep {

    private String applicantName;
    private BigDecimal loanAmount;
    // ... other fields

    @Override
    public AmountStep applicant(String name) {
        this.applicantName = name;
        return this;  // Returns AmountStep interface
    }

    // ... other step implementations
}
```

## When to Use

- **Mandatory ordered construction**: When fields must be set in a specific sequence
- **Complex object creation wizards**: Step-by-step user interfaces
- **API design**: When you want self-documenting, guided APIs
- **Compile-time safety**: When runtime validation is not acceptable

## Benefits

| Benefit | Description |
|---------|-------------|
| **Compile-time safety** | Invalid construction order is caught by the compiler |
| **Self-documenting** | IDE autocompletion shows only valid next steps |
| **No invalid states** | Impossible to create partially constructed objects |
| **Clear API** | Users are guided through the construction process |

## Trade-offs

| Consideration | Impact |
|---------------|--------|
| **More interfaces** | One interface per required step |
| **Less flexible** | Order is strictly enforced (can be a benefit) |
| **More complex** | Implementation is more involved than traditional Builder |

## Comparison: Traditional vs Step Builder

| Aspect | Traditional Builder | Step Builder |
|--------|---------------------|--------------|
| Required field validation | Runtime | Compile-time |
| Method call order | Any order | Enforced order |
| IDE guidance | Limited | Excellent |
| Implementation complexity | Simple | Moderate |
| Flexibility | High | Structured |

## Running the Demo

```bash
# Compile
mvn compile

# Run demonstration
mvn exec:java -Dexec.mainClass="other.stepbuilder.ZMain"
```

## Real-World Applications

- **SQL Query Builders**: SELECT → FROM → WHERE → ORDER BY
- **HTTP Request Builders**: Method → URL → Headers → Body
- **Form Wizards**: Personal Info → Address → Payment → Confirm
- **Configuration Objects**: Required settings → Optional settings → Build
