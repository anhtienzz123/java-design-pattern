package creational.singleton.loggersystem;

/**
 * Demonstration of the Logger Singleton
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Logger Singleton Demo ===\n");
        
        // Get the singleton instance
        Logger logger = Logger.getInstance();
        
        // Verify singleton behavior - should be the same instance
        Logger logger2 = Logger.getInstance();
        System.out.println("Singleton verification: " + (logger == logger2)); // Should be true
        System.out.println("Logger hashcodes - logger1: " + logger.hashCode() + ", logger2: " + logger2.hashCode());
        System.out.println();
        
        // Demonstrate basic logging functionality
        System.out.println("=== Basic Logging Demo ===");
        logger.info("Application started - Logger singleton initialized");
        logger.debug("This debug message won't be shown (default log level is INFO)");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        
        // Wait a moment for async processing
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
        
        // Demonstrate log level changes
        System.out.println("=== Log Level Configuration Demo ===");
        logger.info("Current log level: %s", logger.getLogLevel());
        
        logger.setLogLevel(Logger.LogLevel.DEBUG);
        logger.debug("Now debug messages will be shown!");
        logger.info("Info message after changing log level");
        
        logger.setLogLevel(Logger.LogLevel.ERROR);
        logger.info("This info message won't be shown (log level is ERROR)");
        logger.error("Only error and fatal messages will be shown now");
        
        // Reset to INFO level for remaining demos
        logger.setLogLevel(Logger.LogLevel.INFO);
        
        // Wait for processing
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
        
        // Demonstrate error logging with exceptions
        System.out.println("=== Exception Logging Demo ===");
        try {
            // Simulate an error
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("Division by zero error occurred", e);
        }
        
        // Demonstrate formatted logging
        System.out.println("\n=== Formatted Logging Demo ===");
        String userName = "John Doe";
        int userAge = 25;
        logger.info("User %s logged in, age: %d", userName, userAge);
        logger.warn("User %s has %d failed login attempts", userName, 3);
        
        // Demonstrate thread safety with concurrent logging
        System.out.println("\n=== Thread Safety Demo ===");
        
        Thread loggerThread1 = new Thread(() -> {
            Logger threadLogger = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                threadLogger.info("Thread 1 - Message %d", i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        Thread loggerThread2 = new Thread(() -> {
            Logger threadLogger = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                threadLogger.warn("Thread 2 - Warning %d", i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        Thread loggerThread3 = new Thread(() -> {
            Logger threadLogger = Logger.getInstance();
            for (int i = 0; i < 5; i++) {
                threadLogger.error("Thread 3 - Error %d", i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        loggerThread1.start();
        loggerThread2.start();
        loggerThread3.start();
        
        try {
            loggerThread1.join();
            loggerThread2.join();
            loggerThread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Demonstrate output configuration
        System.out.println("\n=== Output Configuration Demo ===");
        logger.info("Logging to both console and file");
        
        logger.setConsoleOutput(false);
        logger.info("This message will only go to the file");
        
        logger.setConsoleOutput(true);
        logger.setFileOutput(false);
        logger.info("This message will only show on console");
        
        logger.setFileOutput(true);
        logger.info("Back to logging to both console and file");
        
        // Demonstrate performance with high-volume logging
        System.out.println("\n=== Performance Demo ===");
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            logger.debug("Performance test message %d", i);
        }
        
        long endTime = System.currentTimeMillis();
        logger.info("Logged 1000 messages in %d milliseconds", (endTime - startTime));
        
        // Demonstrate monitoring capabilities
        System.out.println("\n=== Monitoring Demo ===");
        logger.info("Buffer size: %d messages", logger.getBufferSize());
        logger.info("Log file location: %s", logger.getLogFileName());
        logger.info("Current log level: %s", logger.getLogLevel());
        logger.info("Debug level enabled: %s", logger.isLevelEnabled(Logger.LogLevel.DEBUG));
        
        // Simulate application lifecycle
        System.out.println("\n=== Application Lifecycle Demo ===");
        simulateApplicationLifecycle(logger);
        
        // Force flush to ensure all messages are processed
        logger.flush();
        
        // Wait for final processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        logger.info("Logger demo completed successfully!");
        System.out.println("\nCheck the application.log file to see all logged messages.");
        
        // Note: Logger will be automatically shut down via shutdown hook
    }
    
    /**
     * Simulate a typical application lifecycle with logging
     */
    private static void simulateApplicationLifecycle(Logger logger) {
        logger.info("=== Application Lifecycle Simulation ===");
        
        // Application startup
        logger.info("Application starting up...");
        logger.debug("Loading configuration...");
        logger.debug("Initializing database connections...");
        logger.info("Application startup completed");
        
        // Normal operation
        logger.info("Processing user request: getUserProfile");
        logger.debug("Validating user session...");
        logger.debug("Fetching user data from database...");
        logger.info("User profile retrieved successfully");
        
        // Warning scenario
        logger.warn("Database connection pool is 80%% full");
        logger.info("Creating additional database connections");
        
        // Error scenario
        logger.error("Failed to connect to external API");
        logger.info("Falling back to cached data");
        logger.warn("API connection will be retried in 5 minutes");
        
        // Application shutdown
        logger.info("Application shutdown initiated");
        logger.debug("Closing database connections...");
        logger.debug("Saving application state...");
        logger.info("Application shutdown completed gracefully");
    }
}