package behavioral.nullobject.customerservicelogger;

public interface Logger {
    void log(String message);
    void logError(String error);
    void logWarning(String warning);
    boolean isEnabled();
}