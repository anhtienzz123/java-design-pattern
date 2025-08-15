package creational.abstractfactory.notificationsystem;

public interface NotificationChannel {
    void configure(String apiKey, String endpoint);
    boolean isActive();
    String getChannelInfo();
}