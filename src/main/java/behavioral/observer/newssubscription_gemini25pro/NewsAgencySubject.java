package behavioral.observer.newssubscription_gemini25pro;

// Subject
public interface NewsAgencySubject {
    void subscribe(NewsSubscriberObserver subscriber);
    void unsubscribe(NewsSubscriberObserver subscriber);
    void notifySubscribers(String newsArticle);
} 