package structural.facade.bankingsystem;

import java.util.HashMap;
import java.util.Map;

// Subsystem: Security and authentication operations
public class SecurityService {
    private Map<String, String> customerCredentials = new HashMap<>();
    private Map<String, String> accountToCustomer = new HashMap<>();

    public SecurityService() {
        // Initialize with sample data
        customerCredentials.put("john_doe", "password123");
        customerCredentials.put("jane_smith", "securePass456");
        customerCredentials.put("bob_wilson", "myPassword789");
        
        accountToCustomer.put("12345", "john_doe");
        accountToCustomer.put("67890", "jane_smith");
        accountToCustomer.put("11111", "bob_wilson");
    }

    public boolean authenticateCustomer(String username, String password) {
        boolean isAuthenticated = customerCredentials.containsKey(username) && 
                                 customerCredentials.get(username).equals(password);
        System.out.println("SecurityService: Authentication for " + username + " - " + 
                          (isAuthenticated ? "Success" : "Failed"));
        return isAuthenticated;
    }

    public boolean authorizeAccountAccess(String username, String accountNumber) {
        boolean isAuthorized = accountToCustomer.containsKey(accountNumber) && 
                              accountToCustomer.get(accountNumber).equals(username);
        System.out.println("SecurityService: Authorization for " + username + " to access account " + 
                          accountNumber + " - " + (isAuthorized ? "Granted" : "Denied"));
        return isAuthorized;
    }

    public void logTransaction(String username, String operation, String details) {
        System.out.println("SecurityService: Logging transaction - User: " + username + 
                          ", Operation: " + operation + ", Details: " + details);
    }
}