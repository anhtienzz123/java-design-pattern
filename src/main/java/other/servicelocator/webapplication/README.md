# Service Locator Pattern - Web Application Use Case

## Overview

This package demonstrates the **Service Locator design pattern** in the context of a web application that needs to access various services like logging, authentication, and database operations.

## What is the Service Locator Pattern?

The Service Locator pattern is a design pattern that encapsulates the processes involved in obtaining a service. It provides a central point of access for services, hiding the complexity of service lookup and instantiation from the client code.

## Key Components

### 1. **Service Interface** (`Service.java`)

-   Base interface that all services must implement
-   Defines common contract for service name and execution

### 2. **Concrete Services**

-   **LoggingService**: Handles application logging with different log levels
-   **AuthenticationService**: Manages user authentication and session handling
-   **DatabaseService**: Provides database connectivity and query execution

### 3. **InitialContext** (`InitialContext.java`)

-   Simulates JNDI lookup functionality
-   Creates and returns appropriate service instances based on service name
-   In real applications, this would be replaced with actual JNDI context

### 4. **Cache** (`Cache.java`)

-   Stores service instances to avoid expensive repeated lookups
-   Provides methods for adding, retrieving, and managing cached services
-   Improves performance by reducing service instantiation overhead

### 5. **ServiceLocator** (`ServiceLocator.java`)

-   Main class implementing the Service Locator pattern
-   Provides unified interface for service access
-   Manages cache operations transparently
-   Offers convenience methods for commonly used services

## How It Works

1. **First Access**: When a service is requested for the first time:

    - ServiceLocator checks the cache
    - Cache miss occurs, so it performs expensive lookup via InitialContext
    - Service instance is created and stored in cache
    - Service is returned to client

2. **Subsequent Access**: When the same service is requested again:
    - ServiceLocator checks the cache
    - Cache hit occurs, service is returned immediately
    - No expensive lookup or instantiation required

## Benefits

1. **Performance**: Caching reduces expensive service lookup operations
2. **Centralization**: Single point of access for all services
3. **Abstraction**: Hides service lookup complexity from clients
4. **Flexibility**: Easy to change service implementations without affecting clients
5. **Loose Coupling**: Clients don't need to know about service instantiation details

## Potential Drawbacks

1. **Single Point of Failure**: ServiceLocator becomes a critical component
2. **Hidden Dependencies**: Makes service dependencies less explicit
3. **Testing Complexity**: Can make unit testing more challenging
4. **Global State**: Cache introduces shared global state

## Usage Example

```java
// Get services through ServiceLocator
LoggingService logger = ServiceLocator.getLoggingService();
logger.logInfo("Application started");

AuthenticationService auth = ServiceLocator.getAuthenticationService();
boolean result = auth.authenticate("username", "password");

DatabaseService db = ServiceLocator.getDatabaseService();
db.connect();
String data = db.executeQuery("SELECT * FROM users");
```

## When to Use

-   When you have multiple services that need to be accessed from various parts of your application
-   When service lookup is expensive and you want to cache service instances
-   When you want to centralize service management and configuration
-   In enterprise applications with JNDI or similar service lookup mechanisms

## Running the Demo

Execute the `WebApplicationDemo.main()` method to see the Service Locator pattern in action. The demo shows:

-   First-time service access (cache miss)
-   Subsequent service access (cache hit)
-   Various service operations
-   Cache management operations
