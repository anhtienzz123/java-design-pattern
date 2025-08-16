package structural.bridge.messaging;

public class EmailSender implements MessageSender {
    private String smtpServer;
    private boolean connected = true;

    public EmailSender(String smtpServer) {
        this.smtpServer = smtpServer;
        System.out.println("Connected to email server: " + smtpServer);
    }

    @Override
    public void sendMessage(String recipient, String subject, String content) {
        if (connected) {
            System.out.printf("📧 EMAIL [%s]: TO: %s | SUBJECT: %s | CONTENT: %s%n", 
                            smtpServer, recipient, subject, content);
        } else {
            System.out.println("❌ Email service is disconnected");
        }
    }

    @Override
    public String getProviderName() {
        return "Email SMTP";
    }

    @Override
    public boolean isConnectionActive() {
        return connected;
    }

    @Override
    public void disconnect() {
        connected = false;
        System.out.println("📧 Email service disconnected");
    }
}