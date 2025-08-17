package behavioral.observer.stockmarket;

/**
 * Concrete Observer - represents an automated trading bot with algorithmic strategies
 */
public class TradingBot implements StockObserver {
    private final String botId;
    private final String strategy;
    private final double triggerThreshold;

    public TradingBot(String botId, String strategy, double triggerThreshold) {
        this.botId = botId;
        this.strategy = strategy;
        this.triggerThreshold = triggerThreshold;
    }

    @Override
    public void update(String stockSymbol, double currentPrice, double previousPrice) {
        double changePercentage = ((currentPrice - previousPrice) / previousPrice) * 100;
        
        System.out.printf("[Trading Bot %s] %s: $%.2f (Strategy: %s)%n", 
                         botId, stockSymbol, currentPrice, strategy);

        if (Math.abs(changePercentage) >= triggerThreshold) {
            if (changePercentage > 0) {
                System.out.printf("  → Bot %s: EXECUTING BUY ORDER for %s (%.2f%% increase detected)%n", 
                                 botId, stockSymbol, changePercentage);
            } else {
                System.out.printf("  → Bot %s: EXECUTING SELL ORDER for %s (%.2f%% decrease detected)%n", 
                                 botId, stockSymbol, Math.abs(changePercentage));
            }
        } else {
            System.out.printf("  → Bot %s: No action required for %s (change %.2f%% below threshold %.2f%%)%n", 
                             botId, stockSymbol, Math.abs(changePercentage), triggerThreshold);
        }
    }
}