package behavioral.observer.stockmarket;

/**
 * Subject interface for stock price changes
 */
public interface StockSubject {
    void attach(StockObserver observer);
    void detach(StockObserver observer);
    void notifyObservers();
}