package behavioral.chainofresponsibility.loggingsystem;

public class EmailAlertHandler extends LogHandler {
    private final String[] recipients;
    
    public EmailAlertHandler(String[] recipients) {
        super("Email Alert Handler", LogMessage.LogLevel.ERROR);
        this.recipients = recipients;
    }
    
    @Override
    protected void writeMessage(LogMessage message) {
        System.out.println("📧 [EMAIL ALERT] Sending critical alert to administrators:");
        for (String recipient : recipients) {
            System.out.println(String.format("   → %s", recipient));
        }
        System.out.println(String.format("   Subject: [%s] Critical System Alert from %s", 
                           message.getLevel().getDisplayName(), message.getSource()));
        System.out.println(String.format("   Body: %s", message.getMessage()));
        
        if (message.hasException()) {
            System.out.println(String.format("   Exception: %s", message.getException().getMessage()));
        }
        System.out.println("   ✉️ Email sent successfully");
    }
}