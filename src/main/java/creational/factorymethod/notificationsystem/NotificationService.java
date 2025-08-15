package creational.factorymethod.notificationsystem;

// Creator
public abstract class NotificationService {

	// Factory method
	abstract Notification createNotification();

	// Business logic using the Product
	public void notifyUser(String recipient, String message) {
		Notification notification = this.createNotification();
		System.out.println("Processing notification request...");
		notification.send(recipient, message);
	}
}