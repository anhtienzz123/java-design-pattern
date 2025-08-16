package structural.bridge.messaging;

public abstract class Notification {
    protected MessageSender messageSender;
    protected String priority;

    public Notification(MessageSender messageSender, String priority) {
        this.messageSender = messageSender;
        this.priority = priority;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public abstract void send(String recipient, String message);

    protected void logNotification(String type, String recipient) {
        System.out.printf("🔔 [%s] %s notification sent to %s via %s%n", 
                        priority, type, recipient, messageSender.getProviderName());
    }
}