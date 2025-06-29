package behavioral.chainofresponsibility.loggingsystem;

public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Chain of Responsibility Pattern: Logging System Demo ===\n");
        
        LogHandler consoleHandler = new ConsoleLogHandler();
        LogHandler fileHandler = new FileLogHandler("application.log");
        LogHandler databaseHandler = new DatabaseLogHandler("jdbc:postgresql://localhost:5432/logs");
        LogHandler emailHandler = new EmailAlertHandler(new String[]{
            "admin@company.com", "devops@company.com", "security@company.com"
        });
        
        consoleHandler.setNext(fileHandler).setNext(databaseHandler).setNext(emailHandler);
        
        LogMessage[] testMessages = {
            new LogMessage(LogMessage.LogLevel.DEBUG, "Starting application initialization", "ApplicationBootstrap"),
            new LogMessage(LogMessage.LogLevel.INFO, "User login successful for user: john.doe", "AuthenticationService"),
            new LogMessage(LogMessage.LogLevel.WARNING, "Database connection pool is 80% full", "ConnectionPoolManager"),
            new LogMessage(LogMessage.LogLevel.ERROR, "Failed to process payment transaction", "PaymentProcessor", 
                          new RuntimeException("Invalid credit card number")),
            new LogMessage(LogMessage.LogLevel.FATAL, "Critical system failure - database unavailable", "DatabaseService", 
                          new RuntimeException("Connection timeout after 30 seconds"))
        };
        
        for (int i = 0; i < testMessages.length; i++) {
            System.out.println("Processing Log Message #" + (i + 1) + " (" + testMessages[i].getLevel().getDisplayName() + "):");
            System.out.println("   Message: " + testMessages[i].getMessage());
            System.out.println("   Source: " + testMessages[i].getSource());
            System.out.println();
            
            consoleHandler.handleMessage(testMessages[i]);
            
            if (i < testMessages.length - 1) {
                System.out.println("====================================================================================================\n");
            }
        }
        
        System.out.println("=== Logging System Demo Complete ===");
    }
} 