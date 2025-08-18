package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Audit logging system for access control events.
 * Provides comprehensive logging and statistics for security monitoring.
 */
public class AccessAuditLog {
    private final ConcurrentLinkedQueue<AccessLogEntry> logEntries;
    private final AtomicLong totalAttempts;
    private final AtomicLong successfulAccesses;
    private final AtomicLong deniedAccesses;
    private final int maxLogEntries;
    
    public AccessAuditLog(int maxLogEntries) {
        this.logEntries = new ConcurrentLinkedQueue<>();
        this.totalAttempts = new AtomicLong(0);
        this.successfulAccesses = new AtomicLong(0);
        this.deniedAccesses = new AtomicLong(0);
        this.maxLogEntries = maxLogEntries;
    }
    
    public AccessAuditLog() {
        this(10000); // Default max entries
    }
    
    /**
     * Logs an access attempt.
     */
    public void logAccess(String domain, AccessContext context, 
                         AccessControlManager.AccessControlResult result, 
                         long evaluationTimeMs) {
        AccessLogEntry entry = new AccessLogEntry(
            LocalDateTime.now(),
            domain,
            context.subject().id(),
            context.subject().name(),
            context.object().id(),
            context.object().type(),
            context.action().verb(),
            result.isAllowed(),
            result.getAccessResult().reason(),
            evaluationTimeMs,
            context.environment().clientIp(),
            context.environment().userAgent()
        );
        
        // Add entry to log (with size limit)
        logEntries.offer(entry);
        if (logEntries.size() > maxLogEntries) {
            logEntries.poll(); // Remove oldest entry
        }
        
        // Update statistics
        totalAttempts.incrementAndGet();
        if (result.isAllowed()) {
            successfulAccesses.incrementAndGet();
        } else {
            deniedAccesses.incrementAndGet();
        }
    }
    
    /**
     * Gets recent log entries.
     */
    public List<AccessLogEntry> getRecentEntries(int count) {
        return logEntries.stream()
                        .skip(Math.max(0, logEntries.size() - count))
                        .toList();
    }
    
    /**
     * Gets all log entries.
     */
    public List<AccessLogEntry> getAllEntries() {
        return new ArrayList<>(logEntries);
    }
    
    /**
     * Gets log entries for a specific subject.
     */
    public List<AccessLogEntry> getEntriesForSubject(String subjectId) {
        return logEntries.stream()
                        .filter(entry -> entry.subjectId().equals(subjectId))
                        .toList();
    }
    
    /**
     * Gets log entries for a specific object.
     */
    public List<AccessLogEntry> getEntriesForObject(String objectId) {
        return logEntries.stream()
                        .filter(entry -> entry.objectId().equals(objectId))
                        .toList();
    }
    
    /**
     * Gets denied access entries.
     */
    public List<AccessLogEntry> getDeniedAccessEntries() {
        return logEntries.stream()
                        .filter(entry -> !entry.accessGranted())
                        .toList();
    }
    
    /**
     * Gets total number of access attempts.
     */
    public long getTotalAccessAttempts() {
        return totalAttempts.get();
    }
    
    /**
     * Gets number of successful accesses.
     */
    public long getSuccessfulAccesses() {
        return successfulAccesses.get();
    }
    
    /**
     * Gets number of denied accesses.
     */
    public long getDeniedAccesses() {
        return deniedAccesses.get();
    }
    
    /**
     * Clears all log entries and resets statistics.
     */
    public void clear() {
        logEntries.clear();
        totalAttempts.set(0);
        successfulAccesses.set(0);
        deniedAccesses.set(0);
    }
    
    /**
     * Prints recent audit log entries.
     */
    public void printRecentEntries(int count) {
        System.out.println("=== Recent Access Log Entries ===");
        getRecentEntries(count).forEach(entry -> {
            System.out.printf("[%s] %s@%s %s %s:%s -> %s (%s) [%dms]%n",
                entry.timestamp(),
                entry.subjectName(),
                entry.clientIp(),
                entry.actionVerb(),
                entry.objectType(),
                entry.objectId(),
                entry.accessGranted() ? "GRANTED" : "DENIED",
                entry.reason(),
                entry.evaluationTimeMs());
        });
        System.out.println();
    }
    
    /**
     * Represents a single access log entry.
     */
    public record AccessLogEntry(
        LocalDateTime timestamp,
        String domain,
        String subjectId,
        String subjectName,
        String objectId,
        String objectType,
        String actionVerb,
        boolean accessGranted,
        String reason,
        long evaluationTimeMs,
        String clientIp,
        String userAgent
    ) {}
}