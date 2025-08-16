package structural.bridge.messaging;

public class SMSSender implements MessageSender {
    private String apiKey;
    private boolean connected = true;

    public SMSSender(String apiKey) {
        this.apiKey = apiKey;
        System.out.println("Connected to SMS gateway with API key: " + apiKey.substring(0, 8) + "...");
    }

    @Override
    public void sendMessage(String recipient, String subject, String content) {
        if (connected) {
            System.out.printf("📱 SMS [API]: TO: %s | MESSAGE: %s %s%n", 
                            recipient, subject, content);
        } else {
            System.out.println("❌ SMS service is disconnected");
        }
    }

    @Override
    public String getProviderName() {
        return "SMS Gateway";
    }

    @Override
    public boolean isConnectionActive() {
        return connected;
    }

    @Override
    public void disconnect() {
        connected = false;
        System.out.println("📱 SMS service disconnected");
    }
}