# Cache Manager Singleton

## Purpose

This package demonstrates the Singleton design pattern implemented using the **Bill Pugh Singleton Pattern** for a high-performance caching system. This pattern provides thread-safe, lazy initialization with TTL (Time To Live) support and automatic cleanup capabilities.

## Problem Solved

-   **Centralized Caching**: Single point for all application caching needs
-   **Memory Management**: Automatic cleanup of expired entries and LRU eviction
-   **Performance**: Fast in-memory storage with concurrent access support
-   **Resource Control**: Configurable cache size limits and TTL settings
-   **Statistics**: Built-in cache hit/miss tracking and performance monitoring

## Implementation Details

### Singleton Features

-   **Bill Pugh Pattern**: Uses static nested helper class for lazy initialization
-   **Thread Safety**: JVM guarantees for class loading ensure thread-safe singleton
-   **No Synchronization**: Better performance than traditional synchronized methods
-   **Clone Prevention**: Overrides `clone()` method to prevent duplication

### Caching Features

-   **TTL Support**: Configurable time-to-live for cache entries
-   **Automatic Cleanup**: Background thread removes expired entries
-   **LRU Eviction**: Least Recently Used eviction when cache size limit reached
-   **Statistics Tracking**: Hit rate, miss count, and access patterns
-   **Concurrent Access**: Thread-safe operations using ConcurrentHashMap
-   **Flexible Configuration**: Configurable default TTL and maximum cache size

## Key Classes

### CacheManager (Singleton)

-   **CacheManagerHelper**: Static nested class for Bill Pugh pattern
-   **CacheEntry**: Internal wrapper with expiration and access tracking
-   **Cleanup Executor**: Background thread for automatic expired entry removal
-   **Statistics Collection**: Hit/miss counting and performance metrics
-   **Configuration Management**: Runtime configuration of cache parameters

### CacheEntry (Inner Class)

-   **Value Storage**: Holds the actual cached value
-   **Expiration Tracking**: Monitors TTL and expiration time
-   **Access Statistics**: Tracks access count and last access time
-   **Thread Safety**: Atomic operations for concurrent access

### CacheStatistics (Inner Class)

-   **Performance Metrics**: Hit count, miss count, hit rate
-   **Size Information**: Current cache size and utilization
-   **Formatted Output**: Easy-to-read statistics display

### ZMain

-   **Singleton Verification**: Demonstrates single instance behavior
-   **Basic Operations**: Put, get, remove, and contains operations
-   **TTL Demonstration**: Shows expiration behavior with different TTL values
-   **Thread Safety Testing**: Concurrent access from multiple threads
-   **Configuration Demo**: Runtime configuration changes
-   **Performance Testing**: High-volume operations for performance measurement

## Usage Examples

### Basic Caching Operations

```java
// Get the singleton instance
CacheManager cache = CacheManager.getInstance();

// Store values with default TTL
cache.put("user:123", userObject);
cache.put("config:db", databaseConfig);

// Store with specific TTL (5 minutes)
cache.put("session:abc", sessionData, 300000);

// Retrieve values
User user = cache.get("user:123");
String config = cache.get("config:db");

// Check if key exists (and not expired)
if (cache.containsKey("session:abc")) {
    // Session is still valid
}
```

### TTL and Expiration

```java
// Short-lived cache entry (30 seconds)
cache.put("temp:token", authToken, 30000);

// Check if still valid after some time
Thread.sleep(35000);
String token = cache.get("temp:token"); // Returns null (expired)

// Configure default TTL for all new entries
cache.setDefaultTtl(600000); // 10 minutes
```

### Configuration and Management

```java
// Configure cache settings
cache.setMaxCacheSize(1000);    // Limit to 1000 entries
cache.setDefaultTtl(300000);    // 5 minutes default TTL
cache.setStatisticsEnabled(true); // Enable hit/miss tracking

// Get cache information
Map<String, Object> info = cache.getCacheInfo();
CacheManager.CacheStatistics stats = cache.getStatistics();

// Manual cleanup of expired entries
cache.forceCleanup();

// Clear entire cache
cache.clear();
```

## Benefits of Bill Pugh Singleton

1. **Thread Safety**: JVM handles thread safety during class loading
2. **Lazy Initialization**: Instance created only when first accessed
3. **Performance**: No synchronization overhead after initialization
4. **Simple Code**: Clean implementation without complex locking
5. **Memory Efficient**: Minimal memory footprint

## Caching Features

### Time-To-Live (TTL)

-   **Configurable Expiration**: Set TTL per entry or use default
-   **Automatic Cleanup**: Background thread removes expired entries
-   **Lazy Expiration**: Entries checked for expiration on access
-   **No Memory Leaks**: Expired entries are automatically removed

### LRU Eviction

-   **Size Limits**: Configurable maximum cache size
-   **Automatic Eviction**: Removes least recently used entries
-   **Access Tracking**: Monitors last access time for each entry
-   **Memory Control**: Prevents unlimited cache growth

### Statistics and Monitoring

-   **Hit Rate Tracking**: Percentage of successful cache hits
-   **Access Counting**: Number of cache hits and misses
-   **Size Monitoring**: Current cache size and capacity utilization
-   **Performance Metrics**: Cache effectiveness measurement

## Thread Safety Considerations

-   Uses `ConcurrentHashMap` for thread-safe storage
-   Bill Pugh pattern ensures thread-safe singleton creation
-   Atomic operations for statistics counting
-   Single background thread for cleanup operations
-   No explicit synchronization needed for cache operations

## Configuration Options

### Cache Size Management:

-   **Maximum Size**: Configurable upper limit for cache entries
-   **LRU Eviction**: Automatic removal of least recently used entries
-   **Memory Control**: Prevents out-of-memory conditions

### Time-To-Live Settings:

-   **Default TTL**: Global setting for new cache entries
-   **Per-Entry TTL**: Individual expiration times for specific entries
-   **Unlimited TTL**: Entries that never expire (TTL = 0)

### Statistics and Monitoring:

-   **Enable/Disable**: Toggle statistics collection for performance
-   **Hit Rate Calculation**: Automatic hit rate computation
-   **Access Tracking**: Monitor cache usage patterns

## Running the Demo

Execute the `ZMain` class to see:

1. Singleton instance verification
2. Basic cache operations (put, get, remove)
3. TTL functionality with expiration
4. Thread-safe concurrent access
5. Cache configuration and management
6. LRU eviction demonstration
7. Performance testing with high-volume operations
8. Statistics collection and monitoring

## Performance Characteristics

-   **Fast Access**: O(1) average time complexity for operations
-   **Concurrent Safe**: Lock-free operations using ConcurrentHashMap
-   **Memory Efficient**: Automatic cleanup and size management
-   **Low Overhead**: Minimal performance impact from singleton pattern
-   **Scalable**: Handles high-concurrency scenarios effectively

## Best Practices Demonstrated

1. **Bill Pugh Pattern**: Most efficient thread-safe singleton
2. **Concurrent Collections**: Using thread-safe data structures
3. **Background Cleanup**: Automated resource management
4. **Configuration Flexibility**: Runtime parameter adjustment
5. **Statistics Collection**: Performance monitoring and optimization
6. **Resource Management**: Proper cleanup and shutdown handling

This implementation provides a production-ready caching solution with excellent performance characteristics, thread safety, and comprehensive feature set suitable for enterprise applications.
