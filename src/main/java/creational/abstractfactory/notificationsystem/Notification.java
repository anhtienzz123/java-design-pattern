package creational.abstractfactory.notificationsystem;

public interface Notification {
    void send(String recipient, String message);
    String getType();
}