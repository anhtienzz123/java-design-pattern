# Database Connection Pool Singleton

## Purpose

This package demonstrates the Singleton design pattern applied to a database connection pool. The pattern ensures that only one connection pool instance exists throughout the application lifecycle, providing centralized management of database connections.

## Problem Solved

-   **Resource Management**: Database connections are expensive to create and destroy
-   **Performance**: Reusing connections improves application performance
-   **Single Point of Control**: One instance manages all database connections
-   **Thread Safety**: Safe concurrent access to the connection pool

## Implementation Details

### Singleton Features

-   **Double-Checked Locking**: Thread-safe singleton creation with minimal synchronization overhead
-   **Volatile Instance**: Ensures visibility of the instance across threads
-   **Clone Prevention**: Overrides `clone()` method to prevent duplication
-   **Private Constructor**: Prevents direct instantiation

### Connection Pool Features

-   **Initial Pool Size**: Starts with 10 connections
-   **Maximum Pool Size**: Can grow up to 20 connections
-   **Connection Reuse**: Efficiently manages available and used connections
-   **Statistics Tracking**: Provides pool usage information

## Key Classes

### DatabaseConnectionPool

-   **Singleton Instance Management**: Uses double-checked locking pattern
-   **Connection Pool Management**: Maintains available and used connection lists
-   **Thread-Safe Operations**: All public methods are synchronized
-   **Resource Cleanup**: Provides methods to close all connections

### ZMain

-   **Singleton Verification**: Demonstrates that only one instance exists
-   **Connection Operations**: Shows getting and releasing connections
-   **Thread Safety Testing**: Concurrent access from multiple threads
-   **Statistics Display**: Shows pool state throughout the demo

## Usage Example

```java
// Get the singleton instance
DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

// Get a connection from the pool
Connection connection = pool.getConnection();

// Use the connection for database operations
// ... database operations ...

// Return the connection to the pool
pool.releaseConnection(connection);

// Check pool statistics
System.out.println(pool.getPoolStatistics());
```

## Benefits of Singleton in This Context

1. **Memory Efficiency**: Only one connection pool exists in memory
2. **Consistent State**: All parts of the application share the same pool
3. **Resource Control**: Centralized management prevents connection leaks
4. **Configuration Consistency**: Single point for pool configuration

## Thread Safety Considerations

-   Uses `volatile` keyword for the instance variable
-   Synchronizes critical sections for pool operations
-   Double-checked locking minimizes synchronization overhead
-   All connection operations are thread-safe

## Running the Demo

Execute the `ZMain` class to see:

1. Singleton instance verification
2. Connection pool operations
3. Thread-safe concurrent access
4. Pool statistics throughout the lifecycle

This implementation provides a robust, thread-safe singleton connection pool suitable for production use.
