package creational.abstractfactory.notificationsystem;

public class MobileSMSNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("📱 Sending SMS to mobile number: " + recipient);
        System.out.println("📲 SMS Content: " + message);
        System.out.println("✓ SMS delivered via mobile carrier network");
    }

    @Override
    public String getType() {
        return "Mobile SMS";
    }
}