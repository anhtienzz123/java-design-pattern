package behavioral.observer.stockmarket;

/**
 * Demonstration of Observer pattern with Stock Market monitoring system
 * 
 * This example shows how different types of investors and trading systems
 * can observe stock price changes and react according to their strategies.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Stock Market Observer Pattern Demo ===\n");

        // Create stocks (subjects)
        Stock appleStock = new Stock("AAPL", 150.00);
        Stock teslaStock = new Stock("TSLA", 250.00);

        // Create different types of observers
        RetailInvestor alice = new RetailInvestor("Alice", 140.00, 160.00);
        RetailInvestor bob = new RetailInvestor("Bob", 145.00, 155.00);
        
        InstitutionalInvestor vanguard = new InstitutionalInvestor("Vanguard", 200.00, 5.0);
        InstitutionalInvestor blackrock = new InstitutionalInvestor("BlackRock", 180.00, 3.0);
        
        TradingBot momentumBot = new TradingBot("MOM-001", "Momentum", 2.0);
        TradingBot arbitrageBot = new TradingBot("ARB-002", "Arbitrage", 1.5);

        // Register observers for Apple stock
        System.out.println("--- Registering observers for AAPL ---");
        appleStock.attach(alice);
        appleStock.attach(vanguard);
        appleStock.attach(momentumBot);

        // Register observers for Tesla stock
        System.out.println("\n--- Registering observers for TSLA ---");
        teslaStock.attach(bob);
        teslaStock.attach(blackrock);
        teslaStock.attach(arbitrageBot);

        // Simulate price changes for Apple
        System.out.println("\n=== AAPL Price Updates ===");
        appleStock.setPrice(148.50);  // Slight decrease
        appleStock.setPrice(145.00);  // Larger decrease
        appleStock.setPrice(152.00);  // Increase above some thresholds

        // Add more observers to Apple
        System.out.println("\n--- Adding more observers to AAPL ---");
        appleStock.attach(blackrock);
        appleStock.attach(arbitrageBot);

        appleStock.setPrice(158.00);  // Another increase

        // Simulate price changes for Tesla
        System.out.println("\n=== TSLA Price Updates ===");
        teslaStock.setPrice(245.00);  // Small decrease
        teslaStock.setPrice(260.00);  // Increase
        teslaStock.setPrice(240.00);  // Larger decrease

        // Remove some observers
        System.out.println("\n--- Removing some observers ---");
        appleStock.detach(alice);
        teslaStock.detach(bob);

        // Final price updates
        System.out.println("\n=== Final Price Updates ===");
        appleStock.setPrice(165.00);
        teslaStock.setPrice(270.00);

        System.out.println("\n=== Demo Complete ===");
    }
}