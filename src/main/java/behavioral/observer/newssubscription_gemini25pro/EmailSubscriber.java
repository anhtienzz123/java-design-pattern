package behavioral.observer.newssubscription_gemini25pro;

// Concrete Observer
public class EmailSubscriber implements NewsSubscriberObserver {
    private final String subscriberEmail;

    public EmailSubscriber(String subscriberEmail) {
        this.subscriberEmail = subscriberEmail;
    }

    @Override
    public void update(String newsArticle) {
        System.out.println("EMAIL to " + subscriberEmail + ": New article published - '" + newsArticle + "'");
    }

    @Override
    public String toString() {
        return "EmailSubscriber(" + subscriberEmail + ")";
    }
}