package creational.abstractfactory.notificationsystem;

public class MobileEmailNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("📱 Sending mobile-optimized email to: " + recipient);
        System.out.println("📧 Mobile Email Content: " + message);
        System.out.println("✓ Email delivered via mobile-optimized HTML template");
    }

    @Override
    public String getType() {
        return "Mobile Email";
    }
}