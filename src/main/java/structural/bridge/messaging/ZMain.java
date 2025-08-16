package structural.bridge.messaging;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Bridge Pattern: Messaging/Notification System ===\n");

        // Create different message senders (implementations)
        MessageSender emailSender = new EmailSender("smtp.company.com");
        MessageSender smsSender = new SMSSender("api_key_123456789");
        MessageSender slackSender = new SlackSender("company-workspace");

        System.out.println("\n--- Alert Notifications ---");
        
        // Create alert notifications with different senders
        AlertNotification emailAlert = new AlertNotification(emailSender, "CRITICAL");
        AlertNotification smsAlert = new AlertNotification(smsSender, "WARNING");
        
        emailAlert.send("admin@company.com", "Database connection failed");
        smsAlert.send("+1234567890", "Server CPU usage above 90%");
        emailAlert.sendSystemAlert("admin@company.com", "Payment Service", "Connection timeout");

        System.out.println("\n--- Marketing Notifications ---");
        
        // Create marketing notifications
        MarketingNotification emailMarketing = new MarketingNotification(emailSender, "SUMMER2024");
        MarketingNotification slackMarketing = new MarketingNotification(slackSender, "INTERNAL2024");
        
        emailMarketing.send("customer@example.com", "Summer Sale - 50% off all products!");
        slackMarketing.setIncludeUnsubscribeLink(false); // Internal campaigns don't need unsubscribe
        slackMarketing.send("general", "Team lunch this Friday at 12 PM");
        emailMarketing.sendPromotionalOffer("vip@example.com", "Exclusive VIP discount", "VIP30");

        System.out.println("\n--- Runtime Implementation Switching ---");
        
        // Demonstrate bridge flexibility - switch implementations at runtime
        System.out.println("Switching email alert to use SMS sender:");
        emailAlert.setMessageSender(smsSender);
        emailAlert.send("+1234567890", "Alert now sent via SMS instead of email");
        
        System.out.println("\nSwitching marketing campaign to use Slack:");
        emailMarketing.setMessageSender(slackSender);
        emailMarketing.send("marketing", "Campaign now broadcasts to Slack channel");

        System.out.println("\n--- Connection Management ---");
        
        // Demonstrate connection status
        System.out.println("Email connection active: " + emailSender.isConnectionActive());
        emailSender.disconnect();
        emailAlert.setMessageSender(emailSender);
        emailAlert.send("admin@company.com", "This message won't be sent");
        
        System.out.println("\n=== Bridge Pattern Demo Complete ===");
    }
}