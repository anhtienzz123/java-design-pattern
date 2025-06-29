package other.servicelocator.webapplication;

/**
 * Concrete implementation of logging service
 */
public class LoggingService implements Service {
    private static final String SERVICE_NAME = "LoggingService";
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void execute() {
        System.out.println("Executing " + SERVICE_NAME + ": Writing logs to file system");
    }
    
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
    
    public void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
    
    public void logDebug(String message) {
        System.out.println("[DEBUG] " + message);
    }
} 