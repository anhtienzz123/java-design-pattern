package behavioral.nullobject.tasknotificationsystem;

/**
 * Concrete implementation for Slack notifications.
 * Sends notifications to Slack channels and direct messages.
 */
public class SlackNotificationService implements NotificationService {
    private final String slackWebhookUrl;
    private final String defaultChannel;
    
    public SlackNotificationService(String slackWebhookUrl, String defaultChannel) {
        this.slackWebhookUrl = slackWebhookUrl;
        this.defaultChannel = defaultChannel;
    }
    
    @Override
    public void sendTaskCreated(String taskTitle, String assignedTo) {
        sendSlackMessage("#" + defaultChannel, 
                        String.format("🆕 New task assigned to @%s: *%s*", assignedTo, taskTitle));
    }
    
    @Override
    public void sendTaskCompleted(String taskTitle, String completedBy) {
        sendSlackMessage("#" + defaultChannel, 
                        String.format("✅ Task completed by @%s: *%s*", completedBy, taskTitle));
    }
    
    @Override
    public void sendTaskDeadlineReminder(String taskTitle, String assignedTo, String deadline) {
        sendSlackMessage("@" + assignedTo, 
                        String.format("⏰ Reminder: Task *%s* is due on %s", taskTitle, deadline));
    }
    
    @Override
    public void sendTaskStatusUpdate(String taskTitle, String status, String updatedBy) {
        sendSlackMessage("#" + defaultChannel, 
                        String.format("📋 Task *%s* updated to *%s* by @%s", taskTitle, status, updatedBy));
    }
    
    private void sendSlackMessage(String target, String message) {
        System.out.printf("💬 [SLACK via %s] TO: %s%n", slackWebhookUrl, target);
        System.out.printf("   MESSAGE: %s%n", message);
        System.out.println("   ✅ Slack notification sent successfully");
    }
}