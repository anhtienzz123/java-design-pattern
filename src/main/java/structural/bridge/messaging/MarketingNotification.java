package structural.bridge.messaging;

public class MarketingNotification extends Notification {
    private String campaignId;
    private boolean includeUnsubscribeLink;

    public MarketingNotification(MessageSender messageSender, String campaignId) {
        super(messageSender, "NORMAL");
        this.campaignId = campaignId;
        this.includeUnsubscribeLink = true;
    }

    @Override
    public void send(String recipient, String message) {
        String subject = String.format("[Marketing] Campaign %s", campaignId);
        String content = formatMarketingContent(message);
        
        messageSender.sendMessage(recipient, subject, content);
        logNotification("MARKETING", recipient);
        trackEngagement(recipient);
    }

    private String formatMarketingContent(String message) {
        StringBuilder content = new StringBuilder();
        content.append("🎉 ").append(message);
        
        if (includeUnsubscribeLink) {
            content.append(" | Unsubscribe: link/unsubscribe/").append(campaignId);
        }
        
        return content.toString();
    }

    private void trackEngagement(String recipient) {
        System.out.printf("📊 Marketing engagement tracked for %s in campaign %s%n", recipient, campaignId);
    }

    public void sendPromotionalOffer(String recipient, String offerDetails, String discountCode) {
        String subject = String.format("[Special Offer] %s", discountCode);
        String content = String.format("🛍️ %s | Use code: %s", offerDetails, discountCode);
        
        messageSender.sendMessage(recipient, subject, formatMarketingContent(content));
        logNotification("PROMOTIONAL", recipient);
    }

    public void setIncludeUnsubscribeLink(boolean includeUnsubscribeLink) {
        this.includeUnsubscribeLink = includeUnsubscribeLink;
    }
}