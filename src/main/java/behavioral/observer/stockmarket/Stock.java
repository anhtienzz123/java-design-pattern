package behavioral.observer.stockmarket;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject - represents a stock with price tracking capabilities
 */
public class Stock implements StockSubject {
    private final String symbol;
    private double currentPrice;
    private double previousPrice;
    private final List<StockObserver> observers;

    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.currentPrice = initialPrice;
        this.previousPrice = initialPrice;
        this.observers = new ArrayList<>();
    }

    @Override
    public void attach(StockObserver observer) {
        observers.add(observer);
        System.out.println("Observer attached to " + symbol);
    }

    @Override
    public void detach(StockObserver observer) {
        observers.remove(observer);
        System.out.println("Observer detached from " + symbol);
    }

    @Override
    public void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(symbol, currentPrice, previousPrice);
        }
    }

    public void setPrice(double newPrice) {
        if (newPrice != currentPrice) {
            previousPrice = currentPrice;
            currentPrice = newPrice;
            System.out.println("\n" + symbol + " price changed: $" + previousPrice + " → $" + currentPrice);
            notifyObservers();
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }
}