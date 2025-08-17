# Observer Pattern - Stock Market Monitoring System

## Overview

This implementation demonstrates the Observer pattern through a **Stock Market Monitoring System**. Different types of investors and trading systems observe stock price changes and react according to their specific strategies and thresholds.

## Pattern Components

### Subject Interface
- **`StockSubject`**: Defines the contract for subjects that can be observed
  - Methods: `attach()`, `detach()`, `notifyObservers()`

### Concrete Subject
- **`Stock`**: Represents a stock with price tracking capabilities
  - Maintains a list of observers
  - Notifies observers when price changes occur
  - Tracks both current and previous prices

### Observer Interface
- **`StockObserver`**: Defines the contract for observers
  - Method: `update(String stockSymbol, double currentPrice, double previousPrice)`

### Concrete Observers

#### 1. RetailInvestor
- **Purpose**: Represents individual retail investors
- **Strategy**: Uses buy/sell thresholds to make investment decisions
- **Behavior**: 
  - Considers buying when price drops below buy threshold
  - Considers selling when price rises above sell threshold
  - Holds when price is within acceptable range

#### 2. InstitutionalInvestor
- **Purpose**: Represents large financial institutions (mutual funds, pension funds)
- **Strategy**: Focuses on volatility management and large volume trades
- **Behavior**:
  - Monitors volatility against risk tolerance
  - Executes large volume trades above certain price thresholds
  - Issues alerts for high volatility scenarios

#### 3. TradingBot
- **Purpose**: Represents automated algorithmic trading systems
- **Strategy**: Uses configurable algorithms (Momentum, Arbitrage, etc.)
- **Behavior**:
  - Executes trades based on percentage change thresholds
  - Different strategies react to different market conditions
  - Provides automated, emotionless trading decisions

## Key Features

### Real-World Scenarios
- **Multiple Observer Types**: Different investor categories with unique strategies
- **Configurable Thresholds**: Each observer can set custom trigger points
- **Dynamic Registration**: Observers can be added/removed during runtime
- **Percentage Calculations**: Real market-like percentage change calculations

### Design Benefits
- **Loose Coupling**: Stocks don't need to know specific observer types
- **Extensibility**: Easy to add new investor types or trading strategies
- **Scalability**: Can handle multiple stocks and unlimited observers
- **Realistic Modeling**: Mirrors actual financial market observer relationships

## Usage Example

```java
// Create stock
Stock appleStock = new Stock("AAPL", 150.00);

// Create different types of observers
RetailInvestor alice = new RetailInvestor("Alice", 140.00, 160.00);
InstitutionalInvestor vanguard = new InstitutionalInvestor("Vanguard", 200.00, 5.0);
TradingBot momentumBot = new TradingBot("MOM-001", "Momentum", 2.0);

// Register observers
appleStock.attach(alice);
appleStock.attach(vanguard);
appleStock.attach(momentumBot);

// Trigger notifications
appleStock.setPrice(145.00);  // All observers notified and react accordingly
```

## Output Example

```
AAPL price changed: $150.00 → $145.00

[Retail Investor Alice] AAPL: $145.00 (-3.33% change)
  → Alice considering BUYING AAPL at $145.00 (below buy threshold $140.00)

[Institution Vanguard] AAPL: $145.00 (3.33% volatility)
  → Vanguard: Monitoring AAPL within acceptable parameters

[Trading Bot MOM-001] AAPL: $145.00 (Strategy: Momentum)
  → Bot MOM-001: EXECUTING SELL ORDER for AAPL (3.33% decrease detected)
```

## Real-World Applications

This pattern is extensively used in:
- **Financial Trading Platforms**: Real-time price monitoring systems
- **Investment Management**: Portfolio tracking and rebalancing
- **Risk Management**: Automated alerts and compliance monitoring
- **Market Data Feeds**: Distributing price updates to multiple subscribers
- **Algorithmic Trading**: High-frequency trading systems

## Pattern Benefits in Finance

1. **Real-time Updates**: Immediate notification of price changes
2. **Multiple Strategies**: Different observers can implement various trading strategies
3. **Scalability**: Easily add new observer types without modifying existing code
4. **Decoupling**: Stock price sources are independent of observer implementations
5. **Flexibility**: Observers can be added/removed dynamically based on market conditions

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.observer.stockmarket.ZMain"

# Or using java directly
java -cp target/classes behavioral.observer.stockmarket.ZMain
```

This implementation showcases how the Observer pattern enables efficient, scalable, and maintainable financial monitoring systems that mirror real-world market dynamics.