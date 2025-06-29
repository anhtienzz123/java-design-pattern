package behavioral.chainofresponsibility.loggingsystem;

public class ConsoleLogHandler extends LogHandler {
    
    public ConsoleLogHandler() {
        super("Console Logger", LogMessage.LogLevel.DEBUG);
    }
    
    @Override
    protected void writeMessage(LogMessage message) {
        String output = String.format("🖥️  [CONSOLE] %s", message.getFormattedMessage());
        
        switch (message.getLevel()) {
            case DEBUG:
                System.out.println("\u001B[37m" + output + "\u001B[0m"); // White
                break;
            case INFO:
                System.out.println("\u001B[36m" + output + "\u001B[0m"); // Cyan
                break;
            case WARNING:
                System.out.println("\u001B[33m" + output + "\u001B[0m"); // Yellow
                break;
            case ERROR:
                System.out.println("\u001B[31m" + output + "\u001B[0m"); // Red
                break;
            case FATAL:
                System.out.println("\u001B[35m" + output + "\u001B[0m"); // Magenta
                break;
        }
        
        if (message.hasException()) {
            System.out.println("   Stack trace: " + message.getException().getStackTrace()[0]);
        }
    }
}