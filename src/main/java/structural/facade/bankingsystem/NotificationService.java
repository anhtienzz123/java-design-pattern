package structural.facade.bankingsystem;

import java.math.BigDecimal;

// Subsystem: Notification and communication operations
public class NotificationService {
    
    public void sendSMSNotification(String phoneNumber, String message) {
        System.out.println("NotificationService: Sending SMS to " + phoneNumber + ": " + message);
    }
    
    public void sendEmailNotification(String email, String subject, String message) {
        System.out.println("NotificationService: Sending Email to " + email);
        System.out.println("  Subject: " + subject);
        System.out.println("  Message: " + message);
    }
    
    public void sendTransactionAlert(String username, String transactionType, BigDecimal amount) {
        String message = "Transaction Alert: " + transactionType + " of $" + amount + " completed.";
        // Simulate sending to both SMS and email
        sendSMSNotification(getPhoneNumber(username), message);
        sendEmailNotification(getEmail(username), "Transaction Alert", message);
    }
    
    private String getPhoneNumber(String username) {
        // Simulate phone number lookup
        return switch (username) {
            case "john_doe" -> "+1-555-0101";
            case "jane_smith" -> "+1-555-0202";
            case "bob_wilson" -> "+1-555-0303";
            default -> "+1-555-0000";
        };
    }
    
    private String getEmail(String username) {
        // Simulate email lookup
        return switch (username) {
            case "john_doe" -> "john.doe@email.com";
            case "jane_smith" -> "jane.smith@email.com";
            case "bob_wilson" -> "bob.wilson@email.com";
            default -> "unknown@email.com";
        };
    }
}