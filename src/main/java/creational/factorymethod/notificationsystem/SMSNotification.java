package creational.factorymethod.notificationsystem;

// Concrete Product
public class SMSNotification implements Notification {

	@Override
	public void send(String recipient, String message) {
		System.out.println("Sending SMS to: " + recipient);
		System.out.println("SMS Content: " + message);
		System.out.println("SMS sent successfully via carrier network");
		System.out.println();
	}
}