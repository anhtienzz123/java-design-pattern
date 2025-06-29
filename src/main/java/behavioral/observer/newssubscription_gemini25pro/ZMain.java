package behavioral.observer.newssubscription_gemini25pro;

public class ZMain {
	public static void main(String[] args) {
		// Create a news agency (subject)
		NewsAgency bbcNews = new NewsAgency("BBC News");

		// Create subscribers (observers)
		NewsSubscriberObserver emailSub1 = new EmailSubscriber("john.doe@example.com");
		NewsSubscriberObserver emailSub2 = new EmailSubscriber("jane.doe@example.com");
		NewsSubscriberObserver smsSub1 = new SMSSubscriber("+1234567890");

		// Register subscribers
		bbcNews.subscribe(emailSub1);
		bbcNews.subscribe(smsSub1);

		// Publish a news article, notifying subscribers
		bbcNews.publishNews("Global markets see a significant surge.");

		// A new subscriber joins
		bbcNews.subscribe(emailSub2);

		// Publish another article
		bbcNews.publishNews("New breakthrough in renewable energy technology.");

		// One subscriber leaves
		bbcNews.unsubscribe(smsSub1);

		// Publish a final article
		bbcNews.publishNews("International peace treaty signed.");

//        == Output: 
//        EmailSubscriber(john.doe@example.com) subscribed to BBC News
//        SMSSubscriber(+1234567890) subscribed to BBC News
//
//        BBC News is publishing a new article: 'Global markets see a significant surge.'
//        EMAIL to john.doe@example.com: New article published - 'Global markets see a significant surge.'
//        SMS to +1234567890: Breaking News - 'Global markets see a significant surge....'
//        EmailSubscriber(jane.doe@example.com) subscribed to BBC News
//
//        BBC News is publishing a new article: 'New breakthrough in renewable energy technology.'
//        EMAIL to john.doe@example.com: New article published - 'New breakthrough in renewable energy technology.'
//        SMS to +1234567890: Breaking News - 'New breakthrough in renewable energy tec...'
//        EMAIL to jane.doe@example.com: New article published - 'New breakthrough in renewable energy technology.'
//        SMSSubscriber(+1234567890) unsubscribed from BBC News
//
//        BBC News is publishing a new article: 'International peace treaty signed.'
//        EMAIL to john.doe@example.com: New article published - 'International peace treaty signed.'
//        EMAIL to jane.doe@example.com: New article published - 'International peace treaty signed.'
	}
}