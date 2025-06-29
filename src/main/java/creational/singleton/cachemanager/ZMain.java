package creational.singleton.cachemanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstration of the Cache Manager Singleton
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Cache Manager Singleton Demo ===\n");
        
        // Get the singleton instance
        CacheManager cache = CacheManager.getInstance();
        
        // Verify singleton behavior - should be the same instance
        CacheManager cache2 = CacheManager.getInstance();
        System.out.println("Singleton verification: " + (cache == cache2)); // Should be true
        System.out.println("Cache hashcodes - cache1: " + cache.hashCode() + ", cache2: " + cache2.hashCode());
        System.out.println();
        
        // Demonstrate basic cache operations
        System.out.println("=== Basic Cache Operations ===");
        
        // Put some values in cache
        cache.put("user:123", "John Doe");
        cache.put("user:456", "Jane Smith");
        cache.put("config:database", "jdbc:postgresql://localhost:5432/mydb");
        cache.put("session:abc123", "active_session_data");
        
        System.out.println("Added 4 items to cache");
        System.out.println("Cache size: " + cache.size());
        System.out.println();
        
        // Retrieve values from cache
        System.out.println("=== Cache Retrieval ===");
        String user = cache.get("user:123");
        String config = cache.get("config:database");
        String session = cache.get("session:abc123");
        String nonExistent = cache.get("user:999");
        
        System.out.println("user:123 = " + user);
        System.out.println("config:database = " + config);
        System.out.println("session:abc123 = " + session);
        System.out.println("user:999 (non-existent) = " + nonExistent);
        System.out.println();
        
        // Demonstrate cache statistics
        System.out.println("=== Cache Statistics ===");
        CacheManager.CacheStatistics stats = cache.getStatistics();
        System.out.println(stats);
        System.out.println("Hit rate: " + String.format("%.2f%%", stats.getHitRate() * 100));
        System.out.println();
        
        // Demonstrate TTL (Time To Live) functionality
        System.out.println("=== TTL Demonstration ===");
        
        // Put value with short TTL (2 seconds)
        cache.put("temp:short", "This will expire soon", 2000);
        // Put value with longer TTL (10 seconds)
        cache.put("temp:long", "This will last longer", 10000);
        
        System.out.println("Added items with TTL:");
        System.out.println("temp:short (2s TTL): " + cache.get("temp:short"));
        System.out.println("temp:long (10s TTL): " + cache.get("temp:long"));
        
        // Wait for short TTL to expire
        try {
            System.out.println("Waiting 3 seconds for short TTL to expire...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("After 3 seconds:");
        System.out.println("temp:short (expired): " + cache.get("temp:short"));
        System.out.println("temp:long (still valid): " + cache.get("temp:long"));
        System.out.println();
        
        // Demonstrate thread safety with concurrent access
        System.out.println("=== Thread Safety Demo ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Thread 1: Writer thread
        executor.submit(() -> {
            CacheManager threadCache = CacheManager.getInstance();
            for (int i = 0; i < 10; i++) {
                threadCache.put("thread1:item" + i, "value" + i);
                System.out.println("Thread 1 wrote: thread1:item" + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Thread 2: Reader thread
        executor.submit(() -> {
            CacheManager threadCache = CacheManager.getInstance();
            for (int i = 0; i < 10; i++) {
                String value = threadCache.get("user:123");
                System.out.println("Thread 2 read user:123 = " + value);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Thread 3: Mixed operations thread
        executor.submit(() -> {
            CacheManager threadCache = CacheManager.getInstance();
            for (int i = 0; i < 5; i++) {
                threadCache.put("thread3:mixed" + i, "mixed_value" + i, 5000);
                String retrieved = threadCache.get("thread3:mixed" + i);
                System.out.println("Thread 3 put and got: thread3:mixed" + i + " = " + retrieved);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
        
        // Demonstrate cache configuration
        System.out.println("=== Cache Configuration Demo ===");
        
        System.out.println("Current cache info:");
        cache.getCacheInfo().forEach((key, value) -> 
            System.out.println("  " + key + ": " + value));
        
        // Configure cache settings
        cache.setDefaultTtl(60000); // 1 minute default TTL
        cache.setMaxCacheSize(50);   // Limit to 50 items
        
        System.out.println("\nUpdated configuration:");
        System.out.println("Default TTL: 60 seconds");
        System.out.println("Max cache size: 50 items");
        System.out.println();
        
        // Demonstrate cache size limit and LRU eviction
        System.out.println("=== Cache Size Limit and LRU Eviction ===");
        
        System.out.println("Current cache size: " + cache.size());
        
        // Fill cache to trigger eviction
        for (int i = 0; i < 60; i++) {
            cache.put("bulk:item" + i, "bulk_value" + i);
        }
        
        System.out.println("After adding 60 bulk items:");
        System.out.println("Cache size (should be limited to 50): " + cache.size());
        
        // Check if original items were evicted
        System.out.println("Original user:123 still in cache: " + cache.containsKey("user:123"));
        System.out.println("Last bulk item in cache: " + cache.containsKey("bulk:item59"));
        System.out.println();
        
        // Demonstrate manual cleanup
        System.out.println("=== Manual Cleanup Demo ===");
        
        System.out.println("Cache size before cleanup: " + cache.size());
        cache.forceCleanup();
        System.out.println("Cache size after cleanup: " + cache.size());
        
        // Demonstrate cache performance
        System.out.println("\n=== Performance Demo ===");
        
        long startTime = System.currentTimeMillis();
        
        // Perform many cache operations
        for (int i = 0; i < 1000; i++) {
            cache.put("perf:test" + i, "performance_test_value_" + i);
        }
        
        for (int i = 0; i < 1000; i++) {
            cache.get("perf:test" + i);
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Performed 1000 puts and 1000 gets in " + 
                          (endTime - startTime) + " milliseconds");
        
        // Final statistics
        System.out.println("\n=== Final Statistics ===");
        CacheManager.CacheStatistics finalStats = cache.getStatistics();
        System.out.println(finalStats);
        
        System.out.println("\nDetailed cache info:");
        cache.getCacheInfo().forEach((key, value) -> 
            System.out.println("  " + key + ": " + value));
        
        // Demonstrate cache clearing
        System.out.println("\n=== Cache Clear Demo ===");
        System.out.println("Cache size before clear: " + cache.size());
        cache.clear();
        System.out.println("Cache size after clear: " + cache.size());
        System.out.println("Statistics after clear: " + cache.getStatistics());
        
        System.out.println("\nCache Manager Demo completed!");
        
        // Note: Cache manager will be automatically shut down via shutdown hook
    }
}