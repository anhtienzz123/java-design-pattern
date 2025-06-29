package other.servicelocator.webapplication;

/**
 * Base interface for all services in the web application
 */
public interface Service {
    /**
     * Returns the name of the service
     * @return Service name
     */
    String getServiceName();
    
    /**
     * Executes the service operation
     */
    void execute();
} 