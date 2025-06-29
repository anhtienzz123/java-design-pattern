package creational.singleton.configurationmanager;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Singleton Configuration Manager using Enum-based implementation
 * Thread-safe and serialization-safe singleton for managing application configuration
 */
public enum ConfigurationManager {
    INSTANCE;
    
    private final Map<String, String> configCache;
    private final Properties properties;
    private final String configFileName = "application.properties";
    
    // Enum constructor is called only once, ensuring singleton behavior
    ConfigurationManager() {
        this.configCache = new ConcurrentHashMap<>();
        this.properties = new Properties();
        loadDefaultConfiguration();
        loadConfigurationFromFile();
    }
    
    /**
     * Load default configuration values
     */
    private void loadDefaultConfiguration() {
        // Default application settings
        configCache.put("app.name", "Design Pattern Demo");
        configCache.put("app.version", "1.0.0");
        configCache.put("app.environment", "development");
        configCache.put("database.host", "localhost");
        configCache.put("database.port", "5432");
        configCache.put("database.name", "designpatterns");
        configCache.put("logging.level", "INFO");
        configCache.put("logging.file", "application.log");
        configCache.put("cache.enabled", "true");
        configCache.put("cache.ttl", "3600");
        configCache.put("server.port", "8080");
        configCache.put("server.timeout", "30000");
    }
    
    /**
     * Load configuration from properties file if it exists
     */
    private void loadConfigurationFromFile() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input != null) {
                properties.load(input);
                // Override default values with file values
                for (String key : properties.stringPropertyNames()) {
                    configCache.put(key, properties.getProperty(key));
                }
                System.out.println("Configuration loaded from " + configFileName);
            } else {
                System.out.println("Configuration file not found, using default values");
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }
    
    /**
     * Get configuration value by key
     */
    public String getProperty(String key) {
        return configCache.get(key);
    }
    
    /**
     * Get configuration value with default fallback
     */
    public String getProperty(String key, String defaultValue) {
        return configCache.getOrDefault(key, defaultValue);
    }
    
    /**
     * Get integer property value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = configCache.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value for key " + key + ": " + value);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get boolean property value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = configCache.get(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    /**
     * Set configuration property at runtime
     */
    public void setProperty(String key, String value) {
        configCache.put(key, value);
    }
    
    /**
     * Remove configuration property
     */
    public void removeProperty(String key) {
        configCache.remove(key);
    }
    
    /**
     * Check if property exists
     */
    public boolean hasProperty(String key) {
        return configCache.containsKey(key);
    }
    
    /**
     * Get all configuration properties
     */
    public Map<String, String> getAllProperties() {
        return new ConcurrentHashMap<>(configCache);
    }
    
    /**
     * Get configuration for a specific component
     */
    public Map<String, String> getPropertiesWithPrefix(String prefix) {
        Map<String, String> filteredProperties = new ConcurrentHashMap<>();
        for (Map.Entry<String, String> entry : configCache.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                filteredProperties.put(entry.getKey(), entry.getValue());
            }
        }
        return filteredProperties;
    }
    
    /**
     * Save current configuration to properties file
     */
    public void saveConfiguration() {
        try (OutputStream output = new FileOutputStream(configFileName)) {
            Properties saveProps = new Properties();
            saveProps.putAll(configCache);
            saveProps.store(output, "Application Configuration - Generated by ConfigurationManager");
            System.out.println("Configuration saved to " + configFileName);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }
    
    /**
     * Reload configuration from file
     */
    public void reloadConfiguration() {
        configCache.clear();
        loadDefaultConfiguration();
        loadConfigurationFromFile();
        System.out.println("Configuration reloaded");
    }
    
    /**
     * Print all configuration properties
     */
    public void printConfiguration() {
        System.out.println("=== Current Configuration ===");
        configCache.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue()));
    }
    
    /**
     * Get application information
     */
    public String getApplicationInfo() {
        return String.format("%s v%s (%s environment)",
                getProperty("app.name", "Unknown App"),
                getProperty("app.version", "Unknown Version"),
                getProperty("app.environment", "Unknown Environment"));
    }
} 