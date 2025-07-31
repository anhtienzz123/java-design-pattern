# Null Object Pattern - Customer Service Logger

## Overview

This implementation demonstrates the **Null Object Pattern** through a customer service logging system. The pattern provides an object with neutral (null) behavior, eliminating the need for null checks and providing a seamless way to handle optional functionality.

## Problem Solved

In traditional implementations, when a logger is optional, you would need to perform null checks before each logging operation:

```java
// Traditional approach with null checks
if (logger != null) {
    logger.log("Processing request");
}
if (logger != null) {
    logger.logError("Error occurred");
}
```

The Null Object pattern eliminates these checks by providing a default implementation that does nothing.

## Implementation Details

### Core Components

1. **Logger Interface** (`Logger.java`)
   - Defines the contract for all logger implementations
   - Methods: `log()`, `logError()`, `logWarning()`, `isEnabled()`

2. **Concrete Loggers**
   - **ConsoleLogger** (`ConsoleLogger.java`): Outputs logs to console with timestamps
   - **FileLogger** (`FileLogger.java`): Writes logs to a specified file
   - **NullLogger** (`NullLogger.java`): The null object - does nothing when called

3. **Client** (`CustomerServiceSystem.java`)
   - Uses logger without any null checks
   - Processes customer requests and logs activities seamlessly

### Key Benefits Demonstrated

1. **No Null Checks Required**: The client code never needs to check if the logger is null
2. **Consistent Interface**: All logger implementations follow the same interface
3. **Easy Configuration**: Switch between logging and no-logging by changing the logger instance
4. **Maintainable Code**: Reduces complexity and potential null pointer exceptions

## Usage Example

```java
// With logging
CustomerServiceSystem system1 = new CustomerServiceSystem(new ConsoleLogger());
system1.handleCustomerRequest("John", "Help request");

// Without logging (using null object)
CustomerServiceSystem system2 = new CustomerServiceSystem(new NullLogger());
system2.handleCustomerRequest("Jane", "Another request"); // No output
```

## Running the Example

Execute the main class:
```bash
java -cp target/classes behavioral.nullobject.customerservicelogger.ZMain
```

Or using Maven:
```bash
mvn exec:java -Dexec.mainClass="behavioral.nullobject.customerservicelogger.ZMain"
```

## When to Use

- When you have optional services or components
- To eliminate repetitive null checks in client code
- When you want to provide default "do nothing" behavior
- To maintain polymorphic behavior even when no action is needed

## Pattern Structure

```
Logger (Interface)
├── ConsoleLogger (Concrete Implementation)
├── FileLogger (Concrete Implementation)
└── NullLogger (Null Object Implementation)
```

The null object implementation provides safe, neutral behavior that matches the expected interface but performs no actual work.