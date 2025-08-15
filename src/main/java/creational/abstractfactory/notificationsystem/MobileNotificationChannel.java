package creational.abstractfactory.notificationsystem;

public class MobileNotificationChannel implements NotificationChannel {
    private String apiKey;
    private String endpoint;
    private boolean active;

    @Override
    public void configure(String apiKey, String endpoint) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.active = true;
        System.out.println("📱 Mobile notification channel configured");
        System.out.println("🔧 Mobile API Endpoint: " + endpoint);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getChannelInfo() {
        return "Mobile Platform Channel - FCM/APNs integrated";
    }
}