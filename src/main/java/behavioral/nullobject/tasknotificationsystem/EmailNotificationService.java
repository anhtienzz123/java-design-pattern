package behavioral.nullobject.tasknotificationsystem;

/**
 * Concrete implementation for email notifications.
 * Sends actual email notifications for task-related events.
 */
public class EmailNotificationService implements NotificationService {
    private final String smtpServer;
    
    public EmailNotificationService(String smtpServer) {
        this.smtpServer = smtpServer;
    }
    
    @Override
    public void sendTaskCreated(String taskTitle, String assignedTo) {
        sendEmail(assignedTo, "New Task Assigned", 
                  String.format("You have been assigned a new task: '%s'", taskTitle));
    }
    
    @Override
    public void sendTaskCompleted(String taskTitle, String completedBy) {
        sendEmail("manager@company.com", "Task Completed", 
                  String.format("Task '%s' has been completed by %s", taskTitle, completedBy));
    }
    
    @Override
    public void sendTaskDeadlineReminder(String taskTitle, String assignedTo, String deadline) {
        sendEmail(assignedTo, "Task Deadline Reminder", 
                  String.format("Reminder: Task '%s' is due on %s", taskTitle, deadline));
    }
    
    @Override
    public void sendTaskStatusUpdate(String taskTitle, String status, String updatedBy) {
        sendEmail("team@company.com", "Task Status Update", 
                  String.format("Task '%s' status updated to '%s' by %s", taskTitle, status, updatedBy));
    }
    
    private void sendEmail(String recipient, String subject, String body) {
        System.out.printf("📧 [EMAIL via %s] TO: %s%n", smtpServer, recipient);
        System.out.printf("   SUBJECT: %s%n", subject);
        System.out.printf("   BODY: %s%n", body);
        System.out.println("   ✅ Email sent successfully");
    }
}