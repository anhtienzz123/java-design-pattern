package creational.factorymethod.notificationsystem;

// Concrete Creator
public class SMSService extends NotificationService {

	@Override
	Notification createNotification() {
		return new SMSNotification();
	}
}