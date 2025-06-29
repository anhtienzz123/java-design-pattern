package other.servicelocator.gameengine;

/**
 * Base interface for all game engine services
 */
public interface GameService {
    /**
     * Returns the name of the game service
     * @return Service name
     */
    String getServiceName();
    
    /**
     * Initializes the service
     */
    void initialize();
    
    /**
     * Updates the service (called every frame)
     * @param deltaTime Time elapsed since last update in milliseconds
     */
    void update(float deltaTime);
    
    /**
     * Shuts down the service and cleans up resources
     */
    void shutdown();
    
    /**
     * Returns whether the service is currently running
     * @return true if service is active, false otherwise
     */
    boolean isActive();
} 