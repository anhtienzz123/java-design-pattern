package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Caching system for access control decisions to improve performance.
 * Uses time-based expiration and configurable cache size limits.
 */
public class AccessCache {
    private final ConcurrentMap<String, CacheEntry> cache;
    private final long cacheTtlMs;
    private final int maxCacheSize;
    private final AtomicLong hits;
    private final AtomicLong misses;
    
    public AccessCache(long cacheTtlMs, int maxCacheSize) {
        this.cache = new ConcurrentHashMap<>();
        this.cacheTtlMs = cacheTtlMs;
        this.maxCacheSize = maxCacheSize;
        this.hits = new AtomicLong(0);
        this.misses = new AtomicLong(0);
    }
    
    public AccessCache() {
        this(300_000, 10_000); // 5 minutes TTL, 10k entries max
    }
    
    /**
     * Gets a cached access control result.
     */
    public AccessControlManager.AccessControlResult get(String domain, AccessContext context) {
        String cacheKey = generateCacheKey(domain, context);
        CacheEntry entry = cache.get(cacheKey);
        
        if (entry == null || isExpired(entry)) {
            misses.incrementAndGet();
            if (entry != null) {
                cache.remove(cacheKey); // Remove expired entry
            }
            return null;
        }
        
        hits.incrementAndGet();
        return entry.result();
    }
    
    /**
     * Puts an access control result in the cache.
     */
    public void put(String domain, AccessContext context, AccessControlManager.AccessControlResult result) {
        // Only cache successful evaluations (not errors)
        if (result.hasError()) {
            return;
        }
        
        String cacheKey = generateCacheKey(domain, context);
        CacheEntry entry = new CacheEntry(result, System.currentTimeMillis());
        
        // Check cache size limit
        if (cache.size() >= maxCacheSize) {
            evictOldestEntries();
        }
        
        cache.put(cacheKey, entry);
    }
    
    /**
     * Clears all cached entries.
     */
    public void clear() {
        cache.clear();
    }
    
    /**
     * Gets cache statistics.
     */
    public CacheStats getStats() {
        long totalRequests = hits.get() + misses.get();
        double hitRate = totalRequests > 0 ? (double) hits.get() / totalRequests : 0.0;
        
        return new CacheStats(
            hits.get(),
            misses.get(),
            totalRequests,
            hitRate,
            cache.size(),
            maxCacheSize
        );
    }
    
    private String generateCacheKey(String domain, AccessContext context) {
        return String.format("%s:%s:%s:%s:%s:%s", 
            domain,
            context.subject().id(),
            context.object().id(),
            context.action().verb(),
            context.environment().clientIp(),
            context.environment().requestTime().toLocalDate()
        );
    }
    
    private boolean isExpired(CacheEntry entry) {
        return System.currentTimeMillis() - entry.timestamp() > cacheTtlMs;
    }
    
    private void evictOldestEntries() {
        long currentTime = System.currentTimeMillis();
        int evictCount = maxCacheSize / 10; // Evict 10% of entries
        
        cache.entrySet().stream()
             .sorted((e1, e2) -> Long.compare(e1.getValue().timestamp(), e2.getValue().timestamp()))
             .limit(evictCount)
             .forEach(entry -> cache.remove(entry.getKey()));
    }
    
    /**
     * Cache entry with result and timestamp.
     */
    private record CacheEntry(AccessControlManager.AccessControlResult result, long timestamp) {}
    
    /**
     * Cache statistics.
     */
    public record CacheStats(
        long hits,
        long misses,
        long totalRequests,
        double hitRate,
        int currentSize,
        int maxSize
    ) {
        @Override
        public String toString() {
            return String.format("CacheStats{hits=%d, misses=%d, hitRate=%.2f%%, size=%d/%d}", 
                hits, misses, hitRate * 100, currentSize, maxSize);
        }
    }
}