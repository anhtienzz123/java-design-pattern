package other.servicelocator.webapplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache to store services and avoid expensive lookup operations
 */
public class Cache {
    private static final Map<String, Service> services = new HashMap<>();
    
    /**
     * Gets a service from cache
     * @param serviceName The name of the service
     * @return Service instance if found in cache, null otherwise
     */
    public static Service getService(String serviceName) {
        Service service = services.get(serviceName.toLowerCase());
        if (service != null) {
            System.out.println("Cache hit for service: " + serviceName);
        }
        return service;
    }
    
    /**
     * Adds a service to cache
     * @param serviceName The name of the service
     * @param service The service instance
     */
    public static void addService(String serviceName, Service service) {
        System.out.println("Adding service to cache: " + serviceName);
        services.put(serviceName.toLowerCase(), service);
    }
    
    /**
     * Removes a service from cache
     * @param serviceName The name of the service to remove
     */
    public static void removeService(String serviceName) {
        System.out.println("Removing service from cache: " + serviceName);
        services.remove(serviceName.toLowerCase());
    }
    
    /**
     * Clears all services from cache
     */
    public static void clearCache() {
        System.out.println("Clearing all services from cache");
        services.clear();
    }
    
    /**
     * Returns the number of services in cache
     * @return Cache size
     */
    public static int getCacheSize() {
        return services.size();
    }
} 