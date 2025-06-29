package other.servicelocator.webapplication;

/**
 * InitialContext simulates JNDI lookup for services
 * In a real application, this would be replaced with actual JNDI context
 */
public class InitialContext {
    
    /**
     * Looks up a service by name and returns the appropriate service instance
     * @param serviceName The name of the service to lookup
     * @return Service instance
     * @throws RuntimeException if service is not found
     */
    public Service lookup(String serviceName) {
        System.out.println("Looking up service: " + serviceName);
        
        switch (serviceName.toLowerCase()) {
            case "loggingservice":
                return new LoggingService();
            case "authenticationservice":
                return new AuthenticationService();
            case "databaseservice":
                return new DatabaseService();
            default:
                throw new RuntimeException("Service not found: " + serviceName);
        }
    }
} 