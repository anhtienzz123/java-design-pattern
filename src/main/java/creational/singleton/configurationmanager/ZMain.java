package creational.singleton.configurationmanager;

import java.util.Map;

/**
 * Demonstration of the Configuration Manager Singleton
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Configuration Manager Singleton Demo ===\n");
        
        // Get the singleton instance using enum approach
        ConfigurationManager config = ConfigurationManager.INSTANCE;
        
        // Verify singleton behavior - should be the same instance
        ConfigurationManager config2 = ConfigurationManager.INSTANCE;
        System.out.println("Singleton verification: " + (config == config2)); // Should be true
        System.out.println("Config hashcodes - config1: " + config.hashCode() + ", config2: " + config2.hashCode());
        System.out.println();
        
        // Display application information
        System.out.println("Application Info: " + config.getApplicationInfo());
        System.out.println();
        
        // Demonstrate basic property access
        System.out.println("=== Basic Property Access ===");
        System.out.println("App Name: " + config.getProperty("app.name"));
        System.out.println("Database Host: " + config.getProperty("database.host"));
        System.out.println("Server Port: " + config.getIntProperty("server.port", 8080));
        System.out.println("Cache Enabled: " + config.getBooleanProperty("cache.enabled", false));
        System.out.println("Non-existent property: " + config.getProperty("non.existent", "default_value"));
        System.out.println();
        
        // Demonstrate runtime property modification
        System.out.println("=== Runtime Property Modification ===");
        System.out.println("Original logging level: " + config.getProperty("logging.level"));
        
        config.setProperty("logging.level", "DEBUG");
        config.setProperty("new.runtime.property", "runtime_value");
        
        System.out.println("Updated logging level: " + config.getProperty("logging.level"));
        System.out.println("New runtime property: " + config.getProperty("new.runtime.property"));
        System.out.println();
        
        // Demonstrate prefix-based property retrieval
        System.out.println("=== Database Configuration ===");
        Map<String, String> dbConfig = config.getPropertiesWithPrefix("database.");
        dbConfig.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println();
        
        System.out.println("=== Server Configuration ===");
        Map<String, String> serverConfig = config.getPropertiesWithPrefix("server.");
        serverConfig.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println();
        
        // Demonstrate thread safety with concurrent access
        System.out.println("=== Thread Safety Demo ===");
        
        Thread configReader = new Thread(() -> {
            ConfigurationManager threadConfig = ConfigurationManager.INSTANCE;
            for (int i = 0; i < 5; i++) {
                String value = threadConfig.getProperty("app.name");
                System.out.println("Reader Thread " + i + ": " + value);
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        Thread configWriter = new Thread(() -> {
            ConfigurationManager threadConfig = ConfigurationManager.INSTANCE;
            for (int i = 0; i < 5; i++) {
                threadConfig.setProperty("thread.test." + i, "value_" + i);
                System.out.println("Writer Thread " + i + ": Set property thread.test." + i);
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        configReader.start();
        configWriter.start();
        
        try {
            configReader.join();
            configWriter.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Show properties added by writer thread
        System.out.println("\nProperties added by writer thread:");
        Map<String, String> threadProps = config.getPropertiesWithPrefix("thread.test.");
        threadProps.forEach((key, value) -> System.out.println(key + " = " + value));
        System.out.println();
        
        // Demonstrate configuration environment simulation
        System.out.println("=== Environment Configuration Simulation ===");
        
        // Simulate development environment
        simulateEnvironment(config, "development");
        
        // Simulate production environment
        simulateEnvironment(config, "production");
        
        // Display full configuration
        System.out.println("=== Full Configuration ===");
        config.printConfiguration();
        
        // Save configuration demonstration
        System.out.println("\n=== Configuration Persistence ===");
        config.saveConfiguration();
        
        System.out.println("\nConfiguration Manager Demo completed!");
    }
    
    /**
     * Simulate different environment configurations
     */
    private static void simulateEnvironment(ConfigurationManager config, String environment) {
        System.out.println("Configuring for " + environment + " environment:");
        
        config.setProperty("app.environment", environment);
        
        if ("production".equals(environment)) {
            config.setProperty("logging.level", "WARN");
            config.setProperty("database.host", "prod-db.company.com");
            config.setProperty("cache.ttl", "7200");
            config.setProperty("server.timeout", "60000");
        } else if ("development".equals(environment)) {
            config.setProperty("logging.level", "DEBUG");
            config.setProperty("database.host", "localhost");
            config.setProperty("cache.ttl", "300");
            config.setProperty("server.timeout", "15000");
        }
        
        System.out.println("  Environment: " + config.getProperty("app.environment"));
        System.out.println("  Logging Level: " + config.getProperty("logging.level"));
        System.out.println("  Database Host: " + config.getProperty("database.host"));
        System.out.println("  Cache TTL: " + config.getProperty("cache.ttl"));
        System.out.println();
    }
} 