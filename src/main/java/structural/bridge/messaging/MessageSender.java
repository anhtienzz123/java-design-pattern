package structural.bridge.messaging;

public interface MessageSender {
    void sendMessage(String recipient, String subject, String content);
    String getProviderName();
    boolean isConnectionActive();
    void disconnect();
}