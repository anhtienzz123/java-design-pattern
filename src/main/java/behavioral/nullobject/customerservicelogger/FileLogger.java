package behavioral.nullobject.customerservicelogger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger implements Logger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String filename;
    
    public FileLogger(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void log(String message) {
        writeToFile(formatMessage("INFO", message));
    }
    
    @Override
    public void logError(String error) {
        writeToFile(formatMessage("ERROR", error));
    }
    
    @Override
    public void logWarning(String warning) {
        writeToFile(formatMessage("WARNING", warning));
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    private String formatMessage(String level, String message) {
        return String.format("[%s] %s - %s%n", 
            LocalDateTime.now().format(FORMATTER), level, message);
    }
    
    private void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(message);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}