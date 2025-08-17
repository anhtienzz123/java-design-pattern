package behavioral.observer.stockmarket;

/**
 * Concrete Observer - represents an institutional investor with different trading strategies
 */
public class InstitutionalInvestor implements StockObserver {
    private final String institutionName;
    private final double volumeThreshold;
    private final double riskTolerance;

    public InstitutionalInvestor(String institutionName, double volumeThreshold, double riskTolerance) {
        this.institutionName = institutionName;
        this.volumeThreshold = volumeThreshold;
        this.riskTolerance = riskTolerance;
    }

    @Override
    public void update(String stockSymbol, double currentPrice, double previousPrice) {
        double changePercentage = Math.abs((currentPrice - previousPrice) / previousPrice) * 100;
        
        System.out.printf("[Institution %s] %s: $%.2f (%.2f%% volatility)%n", 
                         institutionName, stockSymbol, currentPrice, changePercentage);

        if (changePercentage > riskTolerance) {
            System.out.printf("  → %s: HIGH VOLATILITY ALERT! Reviewing position in %s%n", 
                             institutionName, stockSymbol);
        } else if (currentPrice > volumeThreshold) {
            System.out.printf("  → %s: Executing large volume trades for %s%n", 
                             institutionName, stockSymbol);
        } else {
            System.out.printf("  → %s: Monitoring %s within acceptable parameters%n", 
                             institutionName, stockSymbol);
        }
    }
}