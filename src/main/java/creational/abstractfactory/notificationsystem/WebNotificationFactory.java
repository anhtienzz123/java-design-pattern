package creational.abstractfactory.notificationsystem;

public class WebNotificationFactory extends NotificationFactory {
    @Override
    public Notification createEmailNotification() {
        return new WebEmailNotification();
    }

    @Override
    public Notification createSMSNotification() {
        return new WebSMSNotification();
    }

    @Override
    public Notification createPushNotification() {
        return new WebPushNotification();
    }

    @Override
    public NotificationChannel createNotificationChannel() {
        return new WebNotificationChannel();
    }
}