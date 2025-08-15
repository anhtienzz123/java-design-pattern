package creational.abstractfactory.notificationsystem;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Abstract Factory Pattern - Notification System ===\n");

        // Client code that uses different notification factories
        demonstrateNotificationSystem("Mobile", new MobileNotificationFactory());
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateNotificationSystem("Web", new WebNotificationFactory());
    }

    private static void demonstrateNotificationSystem(String platform, NotificationFactory factory) {
        System.out.println("🚀 Configuring " + platform + " Notification System");
        
        // Create notification channel
        NotificationChannel channel = factory.createNotificationChannel();
        channel.configure("API_KEY_123", "https://api." + platform.toLowerCase() + ".notifications.com");
        System.out.println("📡 Channel Info: " + channel.getChannelInfo());
        System.out.println("🟢 Channel Active: " + channel.isActive());
        System.out.println();

        // Create and send different types of notifications
        System.out.println("📬 Sending notifications via " + platform + " platform:");
        
        // Email notification
        Notification emailNotification = factory.createEmailNotification();
        emailNotification.send("user@example.com", "Welcome to our platform!");
        System.out.println();

        // SMS notification
        Notification smsNotification = factory.createSMSNotification();
        smsNotification.send("+1234567890", "Your verification code is: 123456");
        System.out.println();

        // Push notification
        Notification pushNotification = factory.createPushNotification();
        pushNotification.send("device_token_abc123", "You have a new message!");
        System.out.println();

        // Show notification types
        System.out.println("📋 Platform Notification Types:");
        System.out.println("   • " + emailNotification.getType());
        System.out.println("   • " + smsNotification.getType());
        System.out.println("   • " + pushNotification.getType());
    }
}