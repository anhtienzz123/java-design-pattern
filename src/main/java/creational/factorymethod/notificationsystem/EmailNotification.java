package creational.factorymethod.notificationsystem;

// Concrete Product
public class EmailNotification implements Notification {

	@Override
	public void send(String recipient, String message) {
		System.out.println("Sending Email to: " + recipient);
		System.out.println("Email Subject: Notification");
		System.out.println("Email Body: " + message);
		System.out.println("Email sent successfully via SMTP server");
		System.out.println();
	}
}