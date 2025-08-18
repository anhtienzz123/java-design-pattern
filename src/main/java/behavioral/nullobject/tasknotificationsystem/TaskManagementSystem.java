package behavioral.nullobject.tasknotificationsystem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Core task management system that handles task operations.
 * Uses the NotificationService interface to send notifications,
 * without needing to know if notifications are enabled or disabled.
 */
public class TaskManagementSystem {
    private final Map<String, Task> tasks;
    private final NotificationService notificationService;
    
    public TaskManagementSystem(NotificationService notificationService) {
        this.tasks = new HashMap<>();
        this.notificationService = notificationService;
    }
    
    public void createTask(String id, String title, String description, String assignedTo, LocalDate deadline) {
        Task task = new Task(id, title, description, assignedTo, deadline);
        tasks.put(id, task);
        
        System.out.printf("📝 Task created: %s%n", task);
        
        // Send notification without null checks - Null Object pattern handles it
        notificationService.sendTaskCreated(title, assignedTo);
        
        System.out.println();
    }
    
    public void completeTask(String taskId, String completedBy) {
        Task task = tasks.get(taskId);
        if (task == null) {
            System.out.printf("❌ Task not found: %s%n", taskId);
            return;
        }
        
        task.updateStatus("Completed", completedBy);
        System.out.printf("✅ Task completed: %s%n", task);
        
        // Send notification without null checks
        notificationService.sendTaskCompleted(task.getTitle(), completedBy);
        
        System.out.println();
    }
    
    public void updateTaskStatus(String taskId, String newStatus, String updatedBy) {
        Task task = tasks.get(taskId);
        if (task == null) {
            System.out.printf("❌ Task not found: %s%n", taskId);
            return;
        }
        
        String oldStatus = task.getStatus();
        task.updateStatus(newStatus, updatedBy);
        System.out.printf("📋 Task status updated: %s (from '%s' to '%s')%n", task, oldStatus, newStatus);
        
        // Send notification without null checks
        notificationService.sendTaskStatusUpdate(task.getTitle(), newStatus, updatedBy);
        
        System.out.println();
    }
    
    public void sendDeadlineReminders() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        
        System.out.println("🔍 Checking for tasks due tomorrow...");
        
        for (Task task : tasks.values()) {
            if (task.getDeadline().equals(tomorrow) && !"Completed".equals(task.getStatus())) {
                System.out.printf("⏰ Found task due tomorrow: %s%n", task);
                
                // Send reminder notification without null checks
                notificationService.sendTaskDeadlineReminder(
                    task.getTitle(), 
                    task.getAssignedTo(), 
                    task.getFormattedDeadline()
                );
            }
        }
        
        System.out.println();
    }
    
    public void listAllTasks() {
        System.out.println("📊 Current Tasks:");
        if (tasks.isEmpty()) {
            System.out.println("   No tasks found.");
        } else {
            tasks.values().forEach(task -> System.out.printf("   • %s%n", task));
        }
        System.out.println();
    }
}