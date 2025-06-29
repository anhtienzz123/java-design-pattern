package behavioral.chainofresponsibility.loggingsystem;

public abstract class LogHandler {
    protected LogHandler nextHandler;
    protected String handlerName;
    protected LogMessage.LogLevel minLevel;
    
    public LogHandler(String handlerName, LogMessage.LogLevel minLevel) {
        this.handlerName = handlerName;
        this.minLevel = minLevel;
    }
    
    public LogHandler setNext(LogHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    
    public final void handleMessage(LogMessage message) {
        if (canHandle(message)) {
            writeMessage(message);
        }
        
        if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
    
    protected boolean canHandle(LogMessage message) {
        return message.getLevel().getPriority() >= minLevel.getPriority();
    }
    
    protected abstract void writeMessage(LogMessage message);
} 