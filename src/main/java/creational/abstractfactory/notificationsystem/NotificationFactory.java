package creational.abstractfactory.notificationsystem;

public abstract class NotificationFactory {
    public abstract Notification createEmailNotification();
    public abstract Notification createSMSNotification();
    public abstract Notification createPushNotification();
    public abstract NotificationChannel createNotificationChannel();
}