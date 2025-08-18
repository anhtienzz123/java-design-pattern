package behavioral.nullobject.tasknotificationsystem;

/**
 * Abstract interface for notification services.
 * Defines the contract that all notification implementations must follow.
 */
public interface NotificationService {
    void sendTaskCreated(String taskTitle, String assignedTo);
    void sendTaskCompleted(String taskTitle, String completedBy);
    void sendTaskDeadlineReminder(String taskTitle, String assignedTo, String deadline);
    void sendTaskStatusUpdate(String taskTitle, String status, String updatedBy);
}