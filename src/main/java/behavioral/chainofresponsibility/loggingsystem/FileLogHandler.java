package behavioral.chainofresponsibility.loggingsystem;

public class FileLogHandler extends LogHandler {
    private final String fileName;
    
    public FileLogHandler(String fileName) {
        super("File Logger", LogMessage.LogLevel.INFO);
        this.fileName = fileName;
    }
    
    @Override
    protected void writeMessage(LogMessage message) {
        System.out.println(String.format("📁 [FILE: %s] %s", fileName, message.getFormattedMessage()));
        
        if (message.hasException()) {
            System.out.println(String.format("   📁 [FILE: %s] Exception details: %s", 
                               fileName, message.getException().toString()));
        }
    }
} 