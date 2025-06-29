package behavioral.chainofresponsibility.loggingsystem;

public class DatabaseLogHandler extends LogHandler {
    private final String connectionString;
    
    public DatabaseLogHandler(String connectionString) {
        super("Database Logger", LogMessage.LogLevel.WARNING);
        this.connectionString = connectionString;
    }
    
    @Override
    protected void writeMessage(LogMessage message) {
        System.out.println("🗄️  [DATABASE] Storing log entry in database...");
        System.out.println(String.format("   Connection: %s", connectionString));
        System.out.println(String.format("   Table: system_logs"));
        System.out.println(String.format("   Level: %s | Source: %s | Timestamp: %s", 
                           message.getLevel().getDisplayName(), 
                           message.getSource(), 
                           message.getTimestamp()));
        System.out.println(String.format("   Message: %s", message.getMessage()));
        
        if (message.hasException()) {
            System.out.println(String.format("   Exception_Type: %s", message.getException().getClass().getSimpleName()));
            System.out.println(String.format("   Exception_Message: %s", message.getException().getMessage()));
        }
        
        System.out.println("   ✅ Log entry stored successfully in database");
    }
} 