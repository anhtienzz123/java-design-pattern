package behavioral.nullobject.tasknotificationsystem;

import java.time.LocalDate;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Null Object Pattern - Task Notification System Demo ===\n");
        
        // Demonstration 1: Task Management with Email Notifications
        System.out.println("1. Task Management System with Email Notifications:");
        System.out.println("=".repeat(55));
        TaskManagementSystem emailSystem = new TaskManagementSystem(
            new EmailNotificationService("smtp.company.com")
        );
        
        emailSystem.createTask("TASK-001", "Implement Login Feature", 
                              "Create user authentication system", "john.doe", 
                              LocalDate.now().plusDays(7));
        
        emailSystem.createTask("TASK-002", "Fix Database Bug", 
                              "Resolve connection timeout issue", "jane.smith", 
                              LocalDate.now().plusDays(1));
        
        emailSystem.updateTaskStatus("TASK-001", "In Progress", "john.doe");
        emailSystem.completeTask("TASK-002", "jane.smith");
        emailSystem.sendDeadlineReminders();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demonstration 2: Task Management with Slack Notifications
        System.out.println("2. Task Management System with Slack Notifications:");
        System.out.println("=".repeat(54));
        TaskManagementSystem slackSystem = new TaskManagementSystem(
            new SlackNotificationService("https://hooks.slack.com/webhook123", "dev-team")
        );
        
        slackSystem.createTask("TASK-003", "Code Review", 
                              "Review pull request #42", "alice.brown", 
                              LocalDate.now().plusDays(2));
        
        slackSystem.updateTaskStatus("TASK-003", "Completed", "alice.brown");
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Demonstration 3: Task Management WITHOUT Notifications (Null Object)
        System.out.println("3. Task Management System with NO Notifications:");
        System.out.println("=".repeat(50));
        TaskManagementSystem silentSystem = new TaskManagementSystem(
            new NullNotificationService()  // Null Object - no notifications sent
        );
        
        silentSystem.createTask("TASK-004", "Update Documentation", 
                               "Update API documentation", "bob.wilson", 
                               LocalDate.now().plusDays(3));
        
        silentSystem.createTask("TASK-005", "Performance Testing", 
                               "Load test the new features", "charlie.davis", 
                               LocalDate.now().plusDays(1));
        
        silentSystem.updateTaskStatus("TASK-004", "In Progress", "bob.wilson");
        silentSystem.completeTask("TASK-005", "charlie.davis");
        silentSystem.sendDeadlineReminders();
        silentSystem.listAllTasks();
        
        System.out.println("Notice: All task operations completed successfully");
        System.out.println("No notification output because NullNotificationService was used");
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Key Benefits Summary
        System.out.println("=== Key Benefits of Null Object Pattern ===");
        System.out.println("✅ NO null checks required in TaskManagementSystem");
        System.out.println("✅ Seamless handling of notification preferences");
        System.out.println("✅ Easy switching between notification types");
        System.out.println("✅ Simplified client code - no conditional logic");
        System.out.println("✅ Consistent behavior across all configurations");
        System.out.println("✅ Follows Open/Closed Principle - easily extensible");
        
        System.out.println("\n=== Pattern Structure ===");
        System.out.println("NotificationService (Interface)");
        System.out.println("├── EmailNotificationService (Real Object)");
        System.out.println("├── SlackNotificationService (Real Object)");
        System.out.println("└── NullNotificationService (Null Object)");
        System.out.println("");
        System.out.println("TaskManagementSystem (Client) uses NotificationService");
        System.out.println("without needing to check for null references");
        
        System.out.println("\n=== When to Use This Pattern ===");
        System.out.println("• When you have optional dependencies that may not always be present");
        System.out.println("• To eliminate repetitive null checks in client code");
        System.out.println("• When you want to provide default 'do nothing' behavior");
        System.out.println("• To maintain polymorphic consistency in your design");
        System.out.println("• When configuration determines whether a feature is enabled/disabled");
    }
}