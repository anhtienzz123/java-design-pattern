package other.servicelocator.gameengine;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache for game services to improve performance and manage service lifecycle
 */
public class GameServiceCache {
    private static final Map<String, GameService> services = new HashMap<>();
    
    /**
     * Gets a service from cache
     * @param serviceName The name of the service
     * @return GameService instance if found in cache, null otherwise
     */
    public static GameService getService(String serviceName) {
        GameService service = services.get(serviceName.toLowerCase());
        if (service != null) {
            System.out.println("Cache hit for game service: " + serviceName);
        }
        return service;
    }
    
    /**
     * Adds a service to cache
     * @param serviceName The name of the service
     * @param service The service instance
     */
    public static void addService(String serviceName, GameService service) {
        System.out.println("Adding game service to cache: " + serviceName);
        services.put(serviceName.toLowerCase(), service);
    }
    
    /**
     * Removes a service from cache and shuts it down
     * @param serviceName The name of the service to remove
     */
    public static void removeService(String serviceName) {
        GameService service = services.remove(serviceName.toLowerCase());
        if (service != null) {
            System.out.println("Removing game service from cache: " + serviceName);
            service.shutdown();
        }
    }
    
    /**
     * Shuts down all services and clears cache
     */
    public static void shutdownAll() {
        System.out.println("Shutting down all game services...");
        for (GameService service : services.values()) {
            if (service.isActive()) {
                service.shutdown();
            }
        }
        services.clear();
    }
    
    /**
     * Returns the number of services in cache
     * @return Cache size
     */
    public static int getCacheSize() {
        return services.size();
    }
    
    /**
     * Updates all cached services
     * @param deltaTime Time elapsed since last update
     */
    public static void updateAllServices(float deltaTime) {
        for (GameService service : services.values()) {
            if (service.isActive()) {
                service.update(deltaTime);
            }
        }
    }
} 