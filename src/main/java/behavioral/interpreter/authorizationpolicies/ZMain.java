package behavioral.interpreter.authorizationpolicies;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Demonstration of the Interpreter pattern for Authorization Policies.
 * 
 * This example shows how to use the Interpreter pattern to parse and evaluate
 * complex authorization rules using a domain-specific language.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Authorization Policies using Interpreter Pattern ===\n");
        
        AuthorizationEngine engine = new AuthorizationEngine();
        
        // Define various authorization policies
        engine.addPolicy("Admin Full Access", 
            "ROLE(admin)");
            
        engine.addPolicy("Manager Business Hours", 
            "ROLE(manager) AND TIME(business_hours)");
            
        engine.addPolicy("Developer API Access", 
            "ROLE(developer) AND RESOURCE(/api/*) AND (PERMISSION(read) OR PERMISSION(write))");
            
        engine.addPolicy("Internal Users Only", 
            "IP(internal) AND (ROLE(employee) OR ROLE(contractor))");
            
        engine.addPolicy("Emergency Override", 
            "PERMISSION(emergency_access) AND NOT TIME(business_hours)");
            
        engine.addPolicy("Sensitive Data Access", 
            "ROLE(data_analyst) AND PERMISSION(sensitive_read) AND TIME(business_hours) AND IP(internal)");
        
        System.out.println("Registered Policies:");
        for (var policy : engine.getPolicies()) {
            System.out.println("- " + policy.name() + ": " + policy.originalExpression());
        }
        System.out.println();
        
        // Test scenarios
        testScenario1(engine);
        testScenario2(engine);
        testScenario3(engine);
        testScenario4(engine);
        testScenario5(engine);
        
        // Demonstrate single policy evaluation
        System.out.println("=== Single Policy Evaluation ===");
        SecurityContext devContext = new SecurityContext(
            "dev001", "developer", Set.of("read", "write"),
            "/api/users", "GET", LocalDateTime.of(2024, 1, 15, 14, 30),
            "192.168.1.100"
        );
        
        boolean result = engine.evaluatePolicy(
            "ROLE(developer) AND PERMISSION(read) AND RESOURCE(/api/*)", 
            devContext
        );
        
        System.out.println("Developer API access policy result: " + (result ? "ALLOWED" : "DENIED"));
    }
    
    private static void testScenario1(AuthorizationEngine engine) {
        System.out.println("=== Scenario 1: Admin User ===");
        SecurityContext adminContext = new SecurityContext(
            "admin001", "admin", Set.of("read", "write", "delete", "admin"),
            "/admin/users", "DELETE", LocalDateTime.of(2024, 1, 15, 10, 30),
            "192.168.1.50"
        );
        
        var result = engine.authorize(adminContext);
        result.printDetails();
        System.out.println();
    }
    
    private static void testScenario2(AuthorizationEngine engine) {
        System.out.println("=== Scenario 2: Manager During Business Hours ===");
        SecurityContext managerContext = new SecurityContext(
            "mgr001", "manager", Set.of("read", "write", "approve"),
            "/reports/financial", "GET", LocalDateTime.of(2024, 1, 15, 14, 0),
            "192.168.1.75"
        );
        
        var result = engine.authorize(managerContext);
        result.printDetails();
        System.out.println();
    }
    
    private static void testScenario3(AuthorizationEngine engine) {
        System.out.println("=== Scenario 3: Developer API Access ===");
        SecurityContext devContext = new SecurityContext(
            "dev001", "developer", Set.of("read", "write", "debug"),
            "/api/products", "POST", LocalDateTime.of(2024, 1, 15, 16, 45),
            "192.168.1.100"
        );
        
        var result = engine.authorize(devContext);
        result.printDetails();
        System.out.println();
    }
    
    private static void testScenario4(AuthorizationEngine engine) {
        System.out.println("=== Scenario 4: External User (Should be Denied) ===");
        SecurityContext externalContext = new SecurityContext(
            "ext001", "guest", Set.of("read"),
            "/public/info", "GET", LocalDateTime.of(2024, 1, 15, 12, 0),
            "203.0.113.45" // External IP
        );
        
        var result = engine.authorize(externalContext);
        result.printDetails();
        System.out.println();
    }
    
    private static void testScenario5(AuthorizationEngine engine) {
        System.out.println("=== Scenario 5: Emergency Access After Hours ===");
        SecurityContext emergencyContext = new SecurityContext(
            "ops001", "operator", Set.of("read", "emergency_access", "system_restart"),
            "/system/restart", "POST", LocalDateTime.of(2024, 1, 15, 22, 30),
            "192.168.1.200"
        );
        
        var result = engine.authorize(emergencyContext);
        result.printDetails();
        System.out.println();
    }
}