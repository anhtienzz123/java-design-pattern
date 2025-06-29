package other.servicelocator.webapplication;

/**
 * Concrete implementation of authentication service
 */
public class AuthenticationService implements Service {
    private static final String SERVICE_NAME = "AuthenticationService";
    
    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }
    
    @Override
    public void execute() {
        System.out.println("Executing " + SERVICE_NAME + ": Validating user credentials");
    }
    
    public boolean authenticate(String username, String password) {
        // Simplified authentication logic
        System.out.println("Authenticating user: " + username);
        return username != null && password != null && username.length() > 0;
    }
    
    public void logout(String username) {
        System.out.println("Logging out user: " + username);
    }
    
    public boolean isUserLoggedIn(String username) {
        System.out.println("Checking login status for user: " + username);
        return true; // Simplified for demo
    }
} 