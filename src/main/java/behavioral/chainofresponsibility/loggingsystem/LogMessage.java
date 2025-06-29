package behavioral.chainofresponsibility.loggingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a log message that needs to be processed by the logging system.
 * Each message has a level, content, source, and timestamp.
 */
public class LogMessage {
    private final LogLevel level;
    private final String message;
    private final String source;
    private final LocalDateTime timestamp;
    private final Exception exception;
    
    public enum LogLevel {
        DEBUG(1, "DEBUG"),
        INFO(2, "INFO"),
        WARNING(3, "WARN"),
        ERROR(4, "ERROR"),
        FATAL(5, "FATAL");
        
        private final int priority;
        private final String displayName;
        
        LogLevel(int priority, String displayName) {
            this.priority = priority;
            this.displayName = displayName;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public LogMessage(LogLevel level, String message, String source) {
        this(level, message, source, null);
    }
    
    public LogMessage(LogLevel level, String message, String source, Exception exception) {
        this.level = level;
        this.message = message;
        this.source = source;
        this.exception = exception;
        this.timestamp = LocalDateTime.now();
    }
    
    public LogLevel getLevel() {
        return level;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getSource() {
        return source;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Exception getException() {
        return exception;
    }
    
    public boolean hasException() {
        return exception != null;
    }
    
    /**
     * Formats the log message for display.
     */
    public String getFormattedMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] %s [%s] %s", 
                  formattedTime, level.getDisplayName(), source, message));
        
        if (hasException()) {
            sb.append(" | Exception: ").append(exception.getClass().getSimpleName())
              .append(" - ").append(exception.getMessage());
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("LogMessage{level=%s, source='%s', message='%s', hasException=%s}", 
                           level, source, message, hasException());
    }
} 