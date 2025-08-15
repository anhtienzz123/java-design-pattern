package creational.factorymethod.notificationsystem;

// Concrete Creator
public class EmailService extends NotificationService {

	@Override
	Notification createNotification() {
		return new EmailNotification();
	}
}