package creational.singleton.cachemanager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton Cache Manager using Bill Pugh Singleton Pattern
 * Thread-safe caching system with TTL (Time To Live) support
 */
public class CacheManager {
    
    // Bill Pugh singleton pattern - inner static helper class
    private static class CacheManagerHelper {
        private static final CacheManager INSTANCE = new CacheManager();
    }
    
    /**
     * Cache entry wrapper with expiration time
     */
    private static class CacheEntry {
        private final Object value;
        private final long expirationTime;
        private final long creationTime;
        private volatile long lastAccessTime;
        private final AtomicLong accessCount;
        
        CacheEntry(Object value, long ttlMillis) {
            this.value = value;
            this.creationTime = System.currentTimeMillis();
            this.expirationTime = ttlMillis > 0 ? creationTime + ttlMillis : Long.MAX_VALUE;
            this.lastAccessTime = creationTime;
            this.accessCount = new AtomicLong(0);
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
        
        Object getValue() {
            lastAccessTime = System.currentTimeMillis();
            accessCount.incrementAndGet();
            return value;
        }
        
        long getLastAccessTime() {
            return lastAccessTime;
        }
        
        long getAccessCount() {
            return accessCount.get();
        }
        
        long getCreationTime() {
            return creationTime;
        }
    }
    
    private final ConcurrentHashMap<String, CacheEntry> cache;
    private final ScheduledExecutorService cleanupExecutor;
    private final AtomicLong hitCount;
    private final AtomicLong missCount;
    
    // Default configurations
    private long defaultTtlMillis = 300000; // 5 minutes
    private int maxCacheSize = 1000;
    private boolean enableStatistics = true;
    
    // Private constructor prevents external instantiation
    private CacheManager() {
        this.cache = new ConcurrentHashMap<>();
        this.hitCount = new AtomicLong(0);
        this.missCount = new AtomicLong(0);
        
        // Create cleanup executor with daemon thread
        this.cleanupExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "CacheManager-Cleanup");
            t.setDaemon(true);
            return t;
        });
        
        // Schedule periodic cleanup every 30 seconds
        startCleanupTask();
        
        // Add shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }
    
    /**
     * Get the singleton instance
     */
    public static CacheManager getInstance() {
        return CacheManagerHelper.INSTANCE;
    }
    
    /**
     * Start the periodic cleanup task
     */
    private void startCleanupTask() {
        cleanupExecutor.scheduleAtFixedRate(this::cleanupExpiredEntries, 30, 30, TimeUnit.SECONDS);
    }
    
    /**
     * Put a value in cache with default TTL
     */
    public void put(String key, Object value) {
        put(key, value, defaultTtlMillis);
    }
    
    /**
     * Put a value in cache with specific TTL
     */
    public void put(String key, Object value, long ttlMillis) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        
        // Check cache size limit
        if (cache.size() >= maxCacheSize && !cache.containsKey(key)) {
            evictLeastRecentlyUsed();
        }
        
        cache.put(key, new CacheEntry(value, ttlMillis));
    }
    
    /**
     * Get a value from cache
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (key == null) {
            return null;
        }
        
        CacheEntry entry = cache.get(key);
        
        if (entry == null) {
            if (enableStatistics) {
                missCount.incrementAndGet();
            }
            return null;
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            if (enableStatistics) {
                missCount.incrementAndGet();
            }
            return null;
        }
        
        if (enableStatistics) {
            hitCount.incrementAndGet();
        }
        
        return (T) entry.getValue();
    }
    
    /**
     * Remove a value from cache
     */
    public boolean remove(String key) {
        return cache.remove(key) != null;
    }
    
    /**
     * Check if key exists in cache and is not expired
     */
    public boolean containsKey(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) {
            return false;
        }
        
        if (entry.isExpired()) {
            cache.remove(key);
            return false;
        }
        
        return true;
    }
    
    /**
     * Clear all entries from cache
     */
    public void clear() {
        cache.clear();
        hitCount.set(0);
        missCount.set(0);
    }
    
    /**
     * Get current cache size
     */
    public int size() {
        return cache.size();
    }
    
    /**
     * Check if cache is empty
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    /**
     * Get cache hit rate (0.0 to 1.0)
     */
    public double getHitRate() {
        long hits = hitCount.get();
        long misses = missCount.get();
        long total = hits + misses;
        
        return total == 0 ? 0.0 : (double) hits / total;
    }
    
    /**
     * Get cache statistics
     */
    public CacheStatistics getStatistics() {
        return new CacheStatistics(
                cache.size(),
                hitCount.get(),
                missCount.get(),
                getHitRate()
        );
    }
    
    /**
     * Configure default TTL
     */
    public void setDefaultTtl(long ttlMillis) {
        this.defaultTtlMillis = ttlMillis;
    }
    
    /**
     * Configure maximum cache size
     */
    public void setMaxCacheSize(int maxSize) {
        this.maxCacheSize = maxSize;
        
        // Evict entries if current size exceeds new limit
        while (cache.size() > maxSize) {
            evictLeastRecentlyUsed();
        }
    }
    
    /**
     * Enable or disable statistics collection
     */
    public void setStatisticsEnabled(boolean enabled) {
        this.enableStatistics = enabled;
        if (!enabled) {
            hitCount.set(0);
            missCount.set(0);
        }
    }
    
    /**
     * Get all cache keys
     */
    public java.util.Set<String> getKeys() {
        return cache.keySet();
    }
    
    /**
     * Cleanup expired entries
     */
    private void cleanupExpiredEntries() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
    
    /**
     * Evict least recently used entry
     */
    private void evictLeastRecentlyUsed() {
        if (cache.isEmpty()) {
            return;
        }
        
        String lruKey = cache.entrySet().stream()
                .min((e1, e2) -> Long.compare(
                        e1.getValue().getLastAccessTime(),
                        e2.getValue().getLastAccessTime()))
                .map(Map.Entry::getKey)
                .orElse(null);
        
        if (lruKey != null) {
            cache.remove(lruKey);
        }
    }
    
    /**
     * Get detailed cache information
     */
    public Map<String, Object> getCacheInfo() {
        Map<String, Object> info = new ConcurrentHashMap<>();
        info.put("size", cache.size());
        info.put("maxSize", maxCacheSize);
        info.put("defaultTtlMillis", defaultTtlMillis);
        info.put("hitCount", hitCount.get());
        info.put("missCount", missCount.get());
        info.put("hitRate", getHitRate());
        info.put("statisticsEnabled", enableStatistics);
        return info;
    }
    
    /**
     * Force cleanup of expired entries
     */
    public void forceCleanup() {
        cleanupExpiredEntries();
    }
    
    /**
     * Shutdown cache manager and cleanup resources
     */
    public void shutdown() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        clear();
    }
    
    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned");
    }
    
    /**
     * Cache statistics class
     */
    public static class CacheStatistics {
        private final int size;
        private final long hitCount;
        private final long missCount;
        private final double hitRate;
        
        CacheStatistics(int size, long hitCount, long missCount, double hitRate) {
            this.size = size;
            this.hitCount = hitCount;
            this.missCount = missCount;
            this.hitRate = hitRate;
        }
        
        public int getSize() { return size; }
        public long getHitCount() { return hitCount; }
        public long getMissCount() { return missCount; }
        public double getHitRate() { return hitRate; }
        
        @Override
        public String toString() {
            return String.format("CacheStatistics{size=%d, hits=%d, misses=%d, hitRate=%.2f%%}",
                    size, hitCount, missCount, hitRate * 100);
        }
    }
} 