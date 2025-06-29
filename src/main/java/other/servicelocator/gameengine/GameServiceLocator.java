package other.servicelocator.gameengine;

/**
 * Game Service Locator pattern implementation
 * Provides centralized access to game engine services with caching
 */
public class GameServiceLocator {
    
    /**
     * Gets a game service by name. Checks cache first, then creates if needed.
     * @param serviceName The name of the service to retrieve
     * @return GameService instance
     */
    public static GameService getService(String serviceName) {
        // First, check if service is available in cache
        GameService service = GameServiceCache.getService(serviceName);
        
        if (service != null) {
            return service;
        }
        
        // If not in cache, create new service
        System.out.println("Cache miss for game service: " + serviceName + ". Creating new service...");
        service = createService(serviceName);
        
        // Initialize the service
        service.initialize();
        
        // Add the service to cache for future use
        GameServiceCache.addService(serviceName, service);
        
        return service;
    }
    
    /**
     * Creates a new service instance based on service name
     * @param serviceName The name of the service to create
     * @return GameService instance
     */
    private static GameService createService(String serviceName) {
        switch (serviceName.toLowerCase()) {
            case "renderingservice":
                return new RenderingService();
            case "audioservice":
                return new AudioService();
            case "physicsservice":
                return new PhysicsService();
            case "inputservice":
                return createInputService();
            default:
                throw new RuntimeException("Game service not found: " + serviceName);
        }
    }
    
    /**
     * Creates InputService inline
     */
    private static GameService createInputService() {
        return new GameService() {
            private boolean active = false;
            
            @Override
            public String getServiceName() { return "InputService"; }
            
            @Override
            public void initialize() {
                System.out.println("Initializing InputService...");
                System.out.println("Setting up input listeners and device handlers");
                active = true;
            }
            
            @Override
            public void update(float deltaTime) { /* Input processing */ }
            
            @Override
            public void shutdown() {
                System.out.println("Shutting down InputService...");
                active = false;
            }
            
            @Override
            public boolean isActive() { return active; }
        };
    }
    
    /**
     * Convenience method to get rendering service
     */
    public static RenderingService getRenderingService() {
        return (RenderingService) getService("RenderingService");
    }
    
    /**
     * Convenience method to get audio service
     */
    public static AudioService getAudioService() {
        return (AudioService) getService("AudioService");
    }
    
    /**
     * Convenience method to get physics service
     */
    public static PhysicsService getPhysicsService() {
        return (PhysicsService) getService("PhysicsService");
    }
    
    /**
     * Convenience method to get input service
     */
    public static GameService getInputService() {
        return getService("InputService");
    }
    
    /**
     * Updates all active services
     */
    public static void updateAllServices(float deltaTime) {
        GameServiceCache.updateAllServices(deltaTime);
    }
    
    /**
     * Shuts down all services and clears cache
     */
    public static void shutdownAll() {
        GameServiceCache.shutdownAll();
    }
    
    /**
     * Returns the current cache size
     */
    public static int getCacheSize() {
        return GameServiceCache.getCacheSize();
    }
}