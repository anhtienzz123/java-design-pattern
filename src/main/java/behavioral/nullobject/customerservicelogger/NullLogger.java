package behavioral.nullobject.customerservicelogger;

public class NullLogger implements Logger {
    
    @Override
    public void log(String message) {
        // Do nothing - this is the null object implementation
    }
    
    @Override
    public void logError(String error) {
        // Do nothing - this is the null object implementation
    }
    
    @Override
    public void logWarning(String warning) {
        // Do nothing - this is the null object implementation
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}