package structural.bridge.messaging;

public class SlackSender implements MessageSender {
    private String workspace;
    private boolean connected = true;

    public SlackSender(String workspace) {
        this.workspace = workspace;
        System.out.println("Connected to Slack workspace: " + workspace);
    }

    @Override
    public void sendMessage(String recipient, String subject, String content) {
        if (connected) {
            System.out.printf("💬 SLACK [%s]: TO: #%s | TITLE: %s | MESSAGE: %s%n", 
                            workspace, recipient, subject, content);
        } else {
            System.out.println("❌ Slack service is disconnected");
        }
    }

    @Override
    public String getProviderName() {
        return "Slack Workspace";
    }

    @Override
    public boolean isConnectionActive() {
        return connected;
    }

    @Override
    public void disconnect() {
        connected = false;
        System.out.println("💬 Slack service disconnected");
    }
}