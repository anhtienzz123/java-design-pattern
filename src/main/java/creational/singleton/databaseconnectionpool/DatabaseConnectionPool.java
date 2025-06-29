package creational.singleton.databaseconnectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Database Connection Pool
 * Manages a pool of database connections using the Singleton pattern
 * to ensure only one connection pool exists throughout the application.
 */
public class DatabaseConnectionPool {
    // Volatile keyword ensures visibility of changes across threads
    private static volatile DatabaseConnectionPool instance;
    
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final int INITIAL_POOL_SIZE = 10;
    private final int MAX_POOL_SIZE = 20;
    
    // Database connection parameters
    private final String url = "jdbc:h2:mem:testdb";
    private final String username = "sa";
    private final String password = "";
    
    // Private constructor prevents instantiation from outside
    private DatabaseConnectionPool() {
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        initializeConnectionPool();
    }
    
    /**
     * Double-checked locking singleton implementation
     * Thread-safe and performance efficient
     */
    public static DatabaseConnectionPool getInstance() {
        // First check without synchronization for performance
        if (instance == null) {
            // Synchronize only when creating instance
            synchronized (DatabaseConnectionPool.class) {
                // Double-check to prevent race condition
                if (instance == null) {
                    instance = new DatabaseConnectionPool();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize the connection pool with initial connections
     */
    private void initializeConnectionPool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }
    
    /**
     * Create a new database connection
     */
    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating database connection", e);
        }
    }
    
    /**
     * Get a connection from the pool
     */
    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }
        
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }
    
    /**
     * Return a connection back to the pool
     */
    public synchronized boolean releaseConnection(Connection connection) {
        if (connection != null && usedConnections.remove(connection)) {
            connectionPool.add(connection);
            return true;
        }
        return false;
    }
    
    /**
     * Get current pool statistics
     */
    public String getPoolStatistics() {
        return String.format("Available connections: %d, Used connections: %d, Total capacity: %d",
                connectionPool.size(), usedConnections.size(), MAX_POOL_SIZE);
    }
    
    /**
     * Close all connections in the pool
     */
    public synchronized void closeAllConnections() {
        closeConnections(connectionPool);
        closeConnections(usedConnections);
    }
    
    private void closeConnections(List<Connection> connections) {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        connections.clear();
    }
    
    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned");
    }
} 