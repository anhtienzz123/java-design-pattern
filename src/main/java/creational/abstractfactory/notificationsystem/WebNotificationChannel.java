package creational.abstractfactory.notificationsystem;

public class WebNotificationChannel implements NotificationChannel {
    private String apiKey;
    private String endpoint;
    private boolean active;

    @Override
    public void configure(String apiKey, String endpoint) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.active = true;
        System.out.println("🌐 Web notification channel configured");
        System.out.println("🔧 Web API Endpoint: " + endpoint);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getChannelInfo() {
        return "Web Platform Channel - Browser notifications and web services integrated";
    }
}