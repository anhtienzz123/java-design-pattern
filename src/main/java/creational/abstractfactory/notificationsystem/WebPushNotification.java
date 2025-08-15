package creational.abstractfactory.notificationsystem;

public class WebPushNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("🌐 Sending web push notification to browser: " + recipient);
        System.out.println("🔔 Web Push Content: " + message);
        System.out.println("✓ Push notification delivered via Web Push Protocol (service worker)");
    }

    @Override
    public String getType() {
        return "Web Push";
    }
}