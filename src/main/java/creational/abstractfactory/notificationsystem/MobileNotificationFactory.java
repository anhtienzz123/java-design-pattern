package creational.abstractfactory.notificationsystem;

public class MobileNotificationFactory extends NotificationFactory {
    @Override
    public Notification createEmailNotification() {
        return new MobileEmailNotification();
    }

    @Override
    public Notification createSMSNotification() {
        return new MobileSMSNotification();
    }

    @Override
    public Notification createPushNotification() {
        return new MobilePushNotification();
    }

    @Override
    public NotificationChannel createNotificationChannel() {
        return new MobileNotificationChannel();
    }
}