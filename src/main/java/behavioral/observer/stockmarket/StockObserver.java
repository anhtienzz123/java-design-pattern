package behavioral.observer.stockmarket;

/**
 * Observer interface for stock price notifications
 */
public interface StockObserver {
    void update(String stockSymbol, double currentPrice, double previousPrice);
}