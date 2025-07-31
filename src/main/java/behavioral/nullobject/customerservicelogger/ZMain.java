package behavioral.nullobject.customerservicelogger;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Null Object Pattern - Customer Service Logger Demo ===\n");
        
        // Demonstration 1: Using ConsoleLogger
        System.out.println("1. Customer Service System with Console Logger:");
        System.out.println("-----------------------------------------------");
        CustomerServiceSystem systemWithConsoleLogger = new CustomerServiceSystem(new ConsoleLogger());
        systemWithConsoleLogger.handleCustomerRequest("John Doe", "I need help with my account");
        systemWithConsoleLogger.handleCustomerRequest("Jane Smith", "URGENT: My payment failed");
        systemWithConsoleLogger.handleCustomerRequest("Bob Wilson", "I have a complaint about the service");
        systemWithConsoleLogger.generateDailyReport();
        
        System.out.println("\n");
        
        // Demonstration 2: Using FileLogger
        System.out.println("2. Customer Service System with File Logger:");
        System.out.println("--------------------------------------------");
        CustomerServiceSystem systemWithFileLogger = new CustomerServiceSystem(new FileLogger("customer_service.log"));
        systemWithFileLogger.handleCustomerRequest("Alice Brown", "How do I reset my password?");
        systemWithFileLogger.handleCustomerRequest("Charlie Davis", "URGENT: Account locked");
        System.out.println("Logs written to customer_service.log file");
        
        System.out.println("\n");
        
        // Demonstration 3: Using NullLogger (No logging)
        System.out.println("3. Customer Service System with Null Logger (No Logging):");
        System.out.println("----------------------------------------------------------");
        CustomerServiceSystem systemWithoutLogging = new CustomerServiceSystem(new NullLogger());
        systemWithoutLogging.handleCustomerRequest("Mike Johnson", "General inquiry");
        systemWithoutLogging.handleCustomerRequest("Sarah Lee", "Complaint about billing");
        systemWithoutLogging.generateDailyReport();
        System.out.println("No logging output - all operations completed silently");
        
        System.out.println("\n=== Key Benefits of Null Object Pattern ===");
        System.out.println("• Eliminates null checks in client code");
        System.out.println("• Provides default 'do nothing' behavior");
        System.out.println("• Simplifies code maintenance and reduces bugs");
        System.out.println("• Maintains polymorphic behavior across all implementations");
    }
}