package behavioral.nullobject.tasknotificationsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task in the system.
 * This is a simple data class to hold task information.
 */
public class Task {
    private final String id;
    private final String title;
    private final String description;
    private final String assignedTo;
    private final LocalDate deadline;
    private String status;
    private String lastUpdatedBy;
    
    public Task(String id, String title, String description, String assignedTo, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.deadline = deadline;
        this.status = "Created";
        this.lastUpdatedBy = "System";
    }
    
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAssignedTo() { return assignedTo; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    
    public void updateStatus(String newStatus, String updatedBy) {
        this.status = newStatus;
        this.lastUpdatedBy = updatedBy;
    }
    
    public String getFormattedDeadline() {
        return deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    @Override
    public String toString() {
        return String.format("Task{id='%s', title='%s', assignedTo='%s', status='%s', deadline='%s'}", 
                           id, title, assignedTo, status, getFormattedDeadline());
    }
}