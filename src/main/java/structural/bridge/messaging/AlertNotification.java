package structural.bridge.messaging;

public class AlertNotification extends Notification {
    private String alertLevel;

    public AlertNotification(MessageSender messageSender, String alertLevel) {
        super(messageSender, "HIGH");
        this.alertLevel = alertLevel;
    }

    @Override
    public void send(String recipient, String message) {
        String alertSubject = String.format("[%s ALERT]", alertLevel.toUpperCase());
        String alertContent = String.format("⚠️ URGENT: %s", message);
        
        messageSender.sendMessage(recipient, alertSubject, alertContent);
        logNotification("ALERT", recipient);
        
        if (alertLevel.equalsIgnoreCase("CRITICAL")) {
            escalateAlert(recipient, message);
        }
    }

    private void escalateAlert(String recipient, String message) {
        System.out.printf("🚨 ESCALATION: Critical alert '%s' escalated for %s%n", message, recipient);
    }

    public void sendSystemAlert(String recipient, String systemName, String errorDetails) {
        String subject = String.format("[SYSTEM ALERT] %s", systemName);
        String content = String.format("System: %s | Error: %s | Time: %s", 
                                     systemName, errorDetails, java.time.LocalDateTime.now());
        
        messageSender.sendMessage(recipient, subject, content);
        logNotification("SYSTEM ALERT", recipient);
    }
}