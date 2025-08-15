package creational.abstractfactory.notificationsystem;

public class MobilePushNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("📱 Sending push notification to device: " + recipient);
        System.out.println("🔔 Push Content: " + message);
        System.out.println("✓ Push notification delivered via FCM/APNs");
    }

    @Override
    public String getType() {
        return "Mobile Push";
    }
}