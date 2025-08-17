package behavioral.observer.stockmarket;

/**
 * Concrete Observer - represents a retail investor who monitors stock prices
 */
public class RetailInvestor implements StockObserver {
    private final String name;
    private final double buyThreshold;
    private final double sellThreshold;

    public RetailInvestor(String name, double buyThreshold, double sellThreshold) {
        this.name = name;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }

    @Override
    public void update(String stockSymbol, double currentPrice, double previousPrice) {
        double changePercentage = ((currentPrice - previousPrice) / previousPrice) * 100;
        
        System.out.printf("[Retail Investor %s] %s: $%.2f (%.2f%% change)%n", 
                         name, stockSymbol, currentPrice, changePercentage);

        if (currentPrice <= buyThreshold) {
            System.out.printf("  → %s considering BUYING %s at $%.2f (below buy threshold $%.2f)%n", 
                             name, stockSymbol, currentPrice, buyThreshold);
        } else if (currentPrice >= sellThreshold) {
            System.out.printf("  → %s considering SELLING %s at $%.2f (above sell threshold $%.2f)%n", 
                             name, stockSymbol, currentPrice, sellThreshold);
        } else {
            System.out.printf("  → %s HOLDING %s (price within thresholds)%n", 
                             name, stockSymbol);
        }
    }
}