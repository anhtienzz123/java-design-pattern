# Boolean Logic Interpreter Pattern

This implementation demonstrates the Interpreter Pattern for evaluating boolean logic expressions.

## Components

-   **BooleanExpression**: Abstract expression interface
-   **VariableExpression**: Terminal expression for variables
-   **LiteralExpression**: Terminal expression for true/false literals
-   **AndExpression**: Non-terminal for AND operations
-   **OrExpression**: Non-terminal for OR operations
-   **NotExpression**: Non-terminal for NOT operations
-   **BooleanContext**: Stores variable values
-   **BooleanExpressionParser**: Helper to build expressions
-   **BooleanLogicDemo**: Demonstration class
-   **BooleanLogicInterpreterTest**: Comprehensive test suite

## Usage

```java
// Create context
BooleanContext context = new BooleanContext();
context.setVariable("A", true);
context.setVariable("B", false);

// Create expression: A AND B
BooleanExpression expr = new AndExpression(
    new VariableExpression("A"),
    new VariableExpression("B")
);

// Evaluate
boolean result = expr.interpret(context); // false
```

## Features

-   Short-circuit evaluation for AND/OR
-   Debugging trace output
-   Practical use cases (access control, business rules)
-   De Morgan's Laws verification
-   Comprehensive test suite with 36+ test cases

## Run Demo

```bash
javac designpattern/behavioral/interpreter/booleanlogic/*.java
java designpattern.behavioral.interpreter.booleanlogic.BooleanLogicDemo
```

## Run Tests

```bash
javac designpattern/behavioral/interpreter/booleanlogic/*.java
java designpattern.behavioral.interpreter.booleanlogic.BooleanLogicInterpreterTest
```

## Test Coverage

The test suite includes:

-   Terminal expressions testing (variables, literals)
-   Non-terminal expressions testing (AND, OR, NOT)
-   Context functionality testing
-   Complex nested expressions
-   Practical use cases (access control, business rules)
-   Edge cases and error conditions
-   **Result**: 36/36 tests passed (100% success rate)
