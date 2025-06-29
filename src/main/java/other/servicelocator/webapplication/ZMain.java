package other.servicelocator.webapplication;

/**
 * Demo class demonstrating the Service Locator pattern in a web application context
 */
public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Web Application Service Locator Demo ===\n");
        
        // Demonstrate first-time service access (cache miss)
        System.out.println("1. First-time service access (cache miss):");
        LoggingService logger = ServiceLocator.getLoggingService();
        logger.logInfo("Web application started");
        
        AuthenticationService auth = ServiceLocator.getAuthenticationService();
        boolean loginResult = auth.authenticate("john_doe", "password123");
        System.out.println("Login successful: " + loginResult);
        
        DatabaseService db = ServiceLocator.getDatabaseService();
        db.connect();
        String result = db.executeQuery("SELECT * FROM users");
        System.out.println("Query result: " + result);
        
        System.out.println("\nCache size after initial loads: " + ServiceLocator.getCacheSize());
        
        // Demonstrate cached service access (cache hit)
        System.out.println("\n2. Subsequent service access (cache hit):");
        LoggingService logger2 = ServiceLocator.getLoggingService();
        logger2.logDebug("This log comes from cached service");
        
        AuthenticationService auth2 = ServiceLocator.getAuthenticationService();
        auth2.logout("john_doe");
        
        DatabaseService db2 = ServiceLocator.getDatabaseService();
        db2.executeUpdate("UPDATE users SET last_login = NOW()");
        
        // Demonstrate service methods
        System.out.println("\n3. Using different service methods:");
        logger.logError("An error occurred in the application");
        logger.logDebug("Debug information");
        
        auth.isUserLoggedIn("jane_doe");
        
        db.disconnect();
        
        // Demonstrate cache management
        System.out.println("\n4. Cache management:");
        System.out.println("Cache size before clear: " + ServiceLocator.getCacheSize());
        ServiceLocator.clearCache();
        System.out.println("Cache size after clear: " + ServiceLocator.getCacheSize());
        
        // Accessing service after cache clear will trigger lookup again
        System.out.println("\n5. Service access after cache clear (cache miss again):");
        LoggingService logger3 = ServiceLocator.getLoggingService();
        logger3.logInfo("Service retrieved after cache clear");
        
        System.out.println("\n=== Demo completed ===");
    }
} 