package behavioral.observer.newssubscription_gemini25pro;

import java.util.ArrayList;
import java.util.List;

// Concrete Subject
public class NewsAgency implements NewsAgencySubject {
    private final String agencyName;
    private final List<NewsSubscriberObserver> subscribers = new ArrayList<>();
    private final List<String> newsArticles = new ArrayList<>();

    public NewsAgency(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public void subscribe(NewsSubscriberObserver subscriber) {
        subscribers.add(subscriber);
        System.out.println(subscriber + " subscribed to " + agencyName);
    }

    @Override
    public void unsubscribe(NewsSubscriberObserver subscriber) {
        subscribers.remove(subscriber);
        System.out.println(subscriber + " unsubscribed from " + agencyName);
    }

    @Override
    public void notifySubscribers(String newsArticle) {
        System.out.println("\n" + agencyName + " is publishing a new article: '" + newsArticle + "'");
        for (NewsSubscriberObserver subscriber : subscribers) {
            subscriber.update(newsArticle);
        }
    }

    public void publishNews(String newsArticle) {
        newsArticles.add(newsArticle);
        notifySubscribers(newsArticle);
    }
} 