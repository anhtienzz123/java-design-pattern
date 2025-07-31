package behavioral.nullobject.customerservicelogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void log(String message) {
        System.out.println(formatMessage("INFO", message));
    }
    
    @Override
    public void logError(String error) {
        System.err.println(formatMessage("ERROR", error));
    }
    
    @Override
    public void logWarning(String warning) {
        System.out.println(formatMessage("WARNING", warning));
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    private String formatMessage(String level, String message) {
        return String.format("[%s] %s - %s", 
            LocalDateTime.now().format(FORMATTER), level, message);
    }
}