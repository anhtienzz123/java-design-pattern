package other.servicelocator.webapplication;

/**
 * Service Locator pattern implementation
 * This class provides a central point for service lookup and management
 */
public class ServiceLocator {
    private static InitialContext context = new InitialContext();
    
    /**
     * Gets a service by name. First checks cache, then performs lookup if not found.
     * @param serviceName The name of the service to retrieve
     * @return Service instance
     */
    public static Service getService(String serviceName) {
        // First, check if service is available in cache
        Service service = Cache.getService(serviceName);
        
        if (service != null) {
            return service;
        }
        
        // If not in cache, perform expensive lookup operation
        System.out.println("Cache miss for service: " + serviceName + ". Performing lookup...");
        service = context.lookup(serviceName);
        
        // Add the service to cache for future use
        Cache.addService(serviceName, service);
        
        return service;
    }
    
    /**
     * Convenience method to get logging service
     * @return LoggingService instance
     */
    public static LoggingService getLoggingService() {
        return (LoggingService) getService("LoggingService");
    }
    
    /**
     * Convenience method to get authentication service
     * @return AuthenticationService instance
     */
    public static AuthenticationService getAuthenticationService() {
        return (AuthenticationService) getService("AuthenticationService");
    }
    
    /**
     * Convenience method to get database service
     * @return DatabaseService instance
     */
    public static DatabaseService getDatabaseService() {
        return (DatabaseService) getService("DatabaseService");
    }
    
    /**
     * Clears the service cache (useful for testing or hot swapping services)
     */
    public static void clearCache() {
        Cache.clearCache();
    }
    
    /**
     * Returns the current cache size
     * @return Number of cached services
     */
    public static int getCacheSize() {
        return Cache.getCacheSize();
    }
} 