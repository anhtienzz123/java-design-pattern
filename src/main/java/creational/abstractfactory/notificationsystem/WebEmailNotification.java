package creational.abstractfactory.notificationsystem;

public class WebEmailNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("🌐 Sending web-optimized email to: " + recipient);
        System.out.println("📧 Web Email Content: " + message);
        System.out.println("✓ Email delivered via rich HTML web template with attachments support");
    }

    @Override
    public String getType() {
        return "Web Email";
    }
}