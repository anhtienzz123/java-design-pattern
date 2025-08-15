package creational.factorymethod.notificationsystem;

// Concrete Creator
public class PushNotificationService extends NotificationService {

	@Override
	Notification createNotification() {
		return new PushNotification();
	}
}