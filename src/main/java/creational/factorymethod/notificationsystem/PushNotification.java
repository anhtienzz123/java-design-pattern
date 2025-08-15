package creational.factorymethod.notificationsystem;

// Concrete Product
public class PushNotification implements Notification {

	@Override
	public void send(String recipient, String message) {
		System.out.println("Sending Push Notification to device: " + recipient);
		System.out.println("Push Title: Alert");
		System.out.println("Push Message: " + message);
		System.out.println("Push notification sent successfully via FCM/APNs");
		System.out.println();
	}
}