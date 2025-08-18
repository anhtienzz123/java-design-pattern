package behavioral.nullobject.tasknotificationsystem;

/**
 * Null Object implementation for NotificationService.
 * This class provides a "do-nothing" implementation that can be used
 * when notifications are disabled or not required, eliminating the need
 * for null checks in client code.
 */
public class NullNotificationService implements NotificationService {
    
    @Override
    public void sendTaskCreated(String taskTitle, String assignedTo) {
        // Do nothing - no notification sent
    }
    
    @Override
    public void sendTaskCompleted(String taskTitle, String completedBy) {
        // Do nothing - no notification sent
    }
    
    @Override
    public void sendTaskDeadlineReminder(String taskTitle, String assignedTo, String deadline) {
        // Do nothing - no notification sent
    }
    
    @Override
    public void sendTaskStatusUpdate(String taskTitle, String status, String updatedBy) {
        // Do nothing - no notification sent
    }
}