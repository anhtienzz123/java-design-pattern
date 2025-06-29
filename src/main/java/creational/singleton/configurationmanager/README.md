# Configuration Manager Singleton

## Purpose

This package demonstrates the Singleton design pattern implemented using the **Enum-based approach** for managing application configuration. This pattern ensures that configuration settings are accessed through a single, globally available instance throughout the application lifecycle.

## Problem Solved

-   **Global Configuration Access**: Single point of access for all configuration settings
-   **Thread Safety**: Safe concurrent access to configuration properties
-   **Serialization Safety**: Enum-based implementation prevents deserialization issues
-   **Runtime Configuration**: Ability to modify configuration at runtime
-   **Environment Management**: Easy switching between different environment configurations

## Implementation Details

### Singleton Features

-   **Enum-based Singleton**: Uses Java enum for inherently thread-safe singleton
-   **Serialization Safe**: Enum naturally handles serialization/deserialization
-   **Lazy Initialization**: Configuration loaded when first accessed
-   **Clone Prevention**: Enum cannot be cloned by default

### Configuration Features

-   **Default Configuration**: Built-in default values for common settings
-   **File Loading**: Loads configuration from properties files
-   **Runtime Modification**: Set and remove properties during execution
-   **Type Conversion**: Helper methods for int and boolean properties
-   **Prefix Filtering**: Get configuration groups by prefix
-   **Persistence**: Save configuration back to file

## Key Classes

### ConfigurationManager (Enum)

-   **INSTANCE**: Single enum constant providing singleton access
-   **Thread-Safe Operations**: ConcurrentHashMap for concurrent access
-   **Property Management**: Get, set, remove configuration properties
-   **Type Helpers**: Methods for different data types (int, boolean)
-   **Persistence**: Load from and save to properties files

### ZMain

-   **Singleton Verification**: Shows enum singleton behavior
-   **Property Operations**: Demonstrates get/set operations
-   **Thread Safety Testing**: Concurrent read/write operations
-   **Environment Simulation**: Shows configuration for different environments
-   **Persistence Demo**: Shows saving and loading configuration

## Usage Examples

### Basic Property Access

```java
// Get the singleton instance
ConfigurationManager config = ConfigurationManager.INSTANCE;

// Get string property
String appName = config.getProperty("app.name");

// Get with default value
String host = config.getProperty("database.host", "localhost");

// Get typed properties
int port = config.getIntProperty("server.port", 8080);
boolean cacheEnabled = config.getBooleanProperty("cache.enabled", true);
```

### Runtime Configuration

```java
// Set property at runtime
config.setProperty("logging.level", "DEBUG");

// Remove property
config.removeProperty("temporary.setting");

// Check if property exists
if (config.hasProperty("feature.enabled")) {
    // Use feature
}
```

### Environment-Specific Configuration

```java
// Get database configuration
Map<String, String> dbConfig = config.getPropertiesWithPrefix("database.");

// Configure for different environments
if ("production".equals(environment)) {
    config.setProperty("logging.level", "WARN");
    config.setProperty("database.host", "prod-server");
}
```

## Benefits of Enum-based Singleton

1. **Thread Safety**: No synchronization needed, handled by JVM
2. **Serialization Safe**: Enum handles serialization automatically
3. **Simple Implementation**: No complex double-checked locking
4. **Reflection Proof**: Cannot be instantiated via reflection
5. **Memory Efficient**: Single instance guaranteed by JVM

## Configuration Properties

### Default Properties Included:

-   **Application**: name, version, environment
-   **Database**: host, port, name
-   **Logging**: level, file
-   **Cache**: enabled, TTL
-   **Server**: port, timeout

## Thread Safety Features

-   Uses `ConcurrentHashMap` for thread-safe property storage
-   Enum singleton is inherently thread-safe
-   All operations are atomic and thread-safe
-   No manual synchronization required

## Running the Demo

Execute the `ZMain` class to see:

1. Enum singleton verification
2. Basic property access and modification
3. Prefix-based configuration retrieval
4. Thread-safe concurrent operations
5. Environment configuration simulation
6. Configuration persistence

## Best Practices Demonstrated

1. **Enum Singleton**: Most effective singleton implementation in Java
2. **Concurrent Collections**: Using thread-safe collections
3. **Default Values**: Providing sensible defaults
4. **Type Safety**: Helper methods for type conversion
5. **Error Handling**: Graceful handling of configuration errors

This implementation provides a robust, thread-safe configuration management system suitable for enterprise applications.
