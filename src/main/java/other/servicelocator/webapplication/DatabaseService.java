package other.servicelocator.webapplication;

/**
 * Concrete implementation of database service
 */
public class DatabaseService implements Service {
    private static final String SERVICE_NAME = "DatabaseService";
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void execute() {
        System.out.println("Executing " + SERVICE_NAME + ": Connecting to database");
    }
    
    public void connect() {
        System.out.println("Connecting to the database...");
    }
    
    public void disconnect() {
        System.out.println("Disconnecting from the database...");
    }
    
    public String executeQuery(String query) {
        System.out.println("Executing query: " + query);
        return "Query result for: " + query;
    }
    
    public boolean executeUpdate(String updateStatement) {
        System.out.println("Executing update: " + updateStatement);
        return true; // Simplified for demo
    }
} 