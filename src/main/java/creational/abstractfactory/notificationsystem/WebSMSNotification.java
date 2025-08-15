package creational.abstractfactory.notificationsystem;

public class WebSMSNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("🌐 Sending SMS via web gateway to: " + recipient);
        System.out.println("📲 Web SMS Content: " + message);
        System.out.println("✓ SMS delivered via web-based SMS gateway service");
    }

    @Override
    public String getType() {
        return "Web SMS";
    }
}