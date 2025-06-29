package behavioral.observer.newssubscription_gemini25pro;

// Concrete Observer
public class SMSSubscriber implements NewsSubscriberObserver {
    private final String phoneNumber;

    public SMSSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(String newsArticle) {
        System.out.println("SMS to " + phoneNumber + ": Breaking News - '" + newsArticle.substring(0, Math.min(newsArticle.length(), 40)) + "...'");
    }

    @Override
    public String toString() {
        return "SMSSubscriber(" + phoneNumber + ")";
    }
} 