package creational.factorymethod.notificationsystem;

public class ZMain {

	public static void main(String[] args) {
		// Client chooses Email notification
		NotificationService emailService = new EmailService();
		emailService.notifyUser("user@example.com", "Welcome to our platform!");

		// Client chooses SMS notification
		NotificationService smsService = new SMSService();
		smsService.notifyUser("+1234567890", "Your verification code is 123456");

		// Client chooses Push notification
		NotificationService pushService = new PushNotificationService();
		pushService.notifyUser("device-token-abc123", "You have a new message!");
	}
}