# Logging System - Chain of Responsibility Pattern

## Overview

This package demonstrates the **Chain of Responsibility** design pattern through a comprehensive logging system. The system routes log messages through different handlers based on severity levels, with each handler deciding whether to process the message and whether to pass it along the chain.

## Purpose

In this logging system implementation, the Chain of Responsibility pattern allows log messages to be processed by multiple handlers simultaneously. Unlike traditional single-handler approaches, this pattern enables:

-   Multiple output destinations (console, file, database, email)
-   Severity-based filtering at each handler level
-   Easy addition or removal of logging destinations
-   Independent configuration of each handler

## How the Chain of Responsibility is Used

### Pattern Structure

1. **Handler Abstract Class**: `LogHandler`

    - Defines the interface for handling log messages
    - Maintains reference to the next handler in the chain
    - Implements the chain traversal logic
    - Provides level-based filtering capability

2. **Concrete Handlers**:

    - `ConsoleLogHandler` - Outputs all messages (DEBUG+) to console with color coding
    - `FileLogHandler` - Writes important messages (INFO+) to log files
    - `DatabaseLogHandler` - Stores critical messages (WARNING+) in database
    - `EmailAlertHandler` - Sends alerts for severe messages (ERROR+) to administrators

3. **Message Object**: `LogMessage`
    - Contains log level, message content, source, timestamp, and optional exception
    - Supports different severity levels: DEBUG, INFO, WARNING, ERROR, FATAL
    - Provides formatted output for different handler types

### Chain Configuration

The logging chain is configured in order of decreasing verbosity:

```
Console (DEBUG+) → File (INFO+) → Database (WARNING+) → Email (ERROR+)
```

**Key Difference**: Unlike traditional Chain of Responsibility where only one handler processes the request, this logging implementation allows **multiple handlers** to process the same message. Each handler:

-   Checks if the message meets its minimum severity level
-   If yes, processes the message (logs it)
-   Always forwards the message to the next handler (if exists)
-   This enables simultaneous logging to multiple destinations

### Severity Levels and Handler Behavior

| Level   | Priority | Console | File | Database | Email |
| ------- | -------- | ------- | ---- | -------- | ----- |
| DEBUG   | 1        | ✅      | ❌   | ❌       | ❌    |
| INFO    | 2        | ✅      | ✅   | ❌       | ❌    |
| WARNING | 3        | ✅      | ✅   | ✅       | ❌    |
| ERROR   | 4        | ✅      | ✅   | ✅       | ✅    |
| FATAL   | 5        | ✅      | ✅   | ✅       | ✅    |

## Implementation Details

### Handler Responsibilities

-   **ConsoleLogHandler**: Provides immediate feedback with color-coded output for different log levels
-   **FileLogHandler**: Persists important operational information for later analysis
-   **DatabaseLogHandler**: Stores structured log data for reporting and monitoring
-   **EmailAlertHandler**: Notifies administrators immediately of critical issues

### Message Processing Flow

1. Log message is created with level, content, source, and optional exception
2. Message enters the chain at the first handler (Console)
3. Each handler in sequence:
    - Evaluates if message severity meets its threshold
    - If yes, processes the message according to its specific output method
    - Forwards message to next handler regardless of processing decision
4. Process continues until all handlers have had opportunity to process the message

### Key Benefits

1. **Separation of Concerns**: Each handler focuses on one specific output method
2. **Configurable Filtering**: Each handler can set its own minimum severity threshold
3. **Easy Extension**: New handlers can be added without modifying existing code
4. **Multiple Outputs**: Single log message can be processed by multiple handlers simultaneously
5. **Loose Coupling**: Handlers don't need to know about each other's existence

## Usage Example

```java
// Create and configure the logging chain
LogHandler consoleHandler = new ConsoleLogHandler();
LogHandler fileHandler = new FileLogHandler("application.log");
LogHandler databaseHandler = new DatabaseLogHandler("jdbc:postgresql://localhost:5432/logs");
LogHandler emailHandler = new EmailAlertHandler(new String[]{"admin@company.com"});

// Build the chain
consoleHandler.setNext(fileHandler)
             .setNext(databaseHandler)
             .setNext(emailHandler);

// Create and process log messages
LogMessage debugMsg = new LogMessage(LogMessage.LogLevel.DEBUG, "Debug info", "TestClass");
LogMessage errorMsg = new LogMessage(LogMessage.LogLevel.ERROR, "Critical error", "PaymentService",
                                   new RuntimeException("Payment failed"));

// Process messages - they'll be handled by appropriate handlers based on severity
consoleHandler.handleMessage(debugMsg);  // Only console output
consoleHandler.handleMessage(errorMsg);  // All handlers process this
```

## Running the Demo

Execute the `LoggingSystemDemo` class to see the pattern in action:

```bash
java designpattern.behavioral.chainofresponsibility.loggingsystem.LoggingSystemDemo
```

The demo showcases:

-   Different message severity levels
-   Multiple simultaneous outputs
-   Exception handling in log messages
-   Color-coded console output
-   Realistic logging scenarios

## Real-World Applications

This logging pattern is widely used in:

### Software Development

-   Application logging frameworks (Log4j, Logback, SLF4J)
-   Microservices logging pipelines
-   Error tracking and monitoring systems

### System Administration

-   Server monitoring and alerting
-   Security event logging
-   Performance metrics collection

### Business Applications

-   Audit trail systems
-   Compliance logging
-   Business intelligence data collection

## Pattern Variations

This implementation demonstrates a **multi-processing** variant of Chain of Responsibility where:

-   Multiple handlers can process the same request
-   Processing doesn't stop after first handler
-   Each handler filters based on its own criteria

This differs from the traditional **single-processing** variant where only one handler processes the request and the chain stops.

## Extensions and Improvements

Potential enhancements could include:

-   Asynchronous processing for performance
-   Message queuing for high-volume scenarios
-   Configuration-based handler setup
-   Custom formatting for different output types
-   Log rotation and archival policies
-   Integration with external monitoring systems
