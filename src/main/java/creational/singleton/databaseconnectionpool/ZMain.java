package creational.singleton.databaseconnectionpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Demonstration of the Database Connection Pool Singleton
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Database Connection Pool Singleton Demo ===\n");
        
        // Get the singleton instance
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        
        // Verify singleton behavior - both references should be the same
        DatabaseConnectionPool pool2 = DatabaseConnectionPool.getInstance();
        System.out.println("Singleton verification: " + (pool == pool2)); // Should be true
        System.out.println("Pool hashcodes - pool1: " + pool.hashCode() + ", pool2: " + pool2.hashCode());
        System.out.println();
        
        // Display initial pool statistics
        System.out.println("Initial pool statistics:");
        System.out.println(pool.getPoolStatistics());
        System.out.println();
        
        // Simulate getting connections from the pool
        System.out.println("Getting connections from the pool...");
        Connection conn1 = pool.getConnection();
        Connection conn2 = pool.getConnection();
        Connection conn3 = pool.getConnection();
        
        System.out.println("After getting 3 connections:");
        System.out.println(pool.getPoolStatistics());
        System.out.println();
        
        // Simulate using the connections
        try {
            // Use conn1 to create a table and insert data
            Statement stmt1 = conn1.createStatement();
            stmt1.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(50))");
            stmt1.execute("INSERT INTO users VALUES (1, 'John Doe')");
            stmt1.execute("INSERT INTO users VALUES (2, 'Jane Smith')");
            
            // Use conn2 to query data
            Statement stmt2 = conn2.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT * FROM users");
            
            System.out.println("Query results using connection from pool:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
            System.out.println();
            
        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
        }
        
        // Return connections to the pool
        System.out.println("Returning connections to the pool...");
        pool.releaseConnection(conn1);
        pool.releaseConnection(conn2);
        pool.releaseConnection(conn3);
        
        System.out.println("After returning connections:");
        System.out.println(pool.getPoolStatistics());
        System.out.println();
        
        // Demonstrate thread safety by getting connections in multiple threads
        System.out.println("Testing thread safety with concurrent access...");
        
        Thread thread1 = new Thread(() -> {
            DatabaseConnectionPool threadPool = DatabaseConnectionPool.getInstance();
            Connection conn = threadPool.getConnection();
            System.out.println("Thread 1 got connection: " + (conn != null));
            
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            threadPool.releaseConnection(conn);
            System.out.println("Thread 1 released connection");
        });
        
        Thread thread2 = new Thread(() -> {
            DatabaseConnectionPool threadPool = DatabaseConnectionPool.getInstance();
            Connection conn = threadPool.getConnection();
            System.out.println("Thread 2 got connection: " + (conn != null));
            
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            threadPool.releaseConnection(conn);
            System.out.println("Thread 2 released connection");
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nFinal pool statistics:");
        System.out.println(pool.getPoolStatistics());
        
        // Clean up
        pool.closeAllConnections();
        System.out.println("\nAll connections closed. Demo completed.");
    }
} 