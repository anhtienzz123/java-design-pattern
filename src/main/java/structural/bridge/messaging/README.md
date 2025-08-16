# Bridge Pattern: Messaging/Notification System

This example demonstrates the Bridge pattern through a comprehensive messaging and notification system that separates notification types (abstraction) from message delivery mechanisms (implementation).

## Pattern Overview

The Bridge pattern decouples an abstraction from its implementation, allowing both to vary independently. In this messaging system:

- **Abstraction**: `Notification` and its subclasses (`AlertNotification`, `MarketingNotification`)
- **Implementation**: `MessageSender` interface and its implementations (`EmailSender`, `SMSSender`, `SlackSender`)

## Key Components

### Abstraction Layer
- **`Notification`**: Abstract base class that defines the notification interface and holds a reference to a `MessageSender`
- **`AlertNotification`**: Handles urgent system alerts with escalation capabilities
- **`MarketingNotification`**: Manages promotional and marketing communications with engagement tracking

### Implementation Layer
- **`MessageSender`**: Interface defining message delivery operations
- **`EmailSender`**: Implements email delivery via SMTP
- **`SMSSender`**: Implements SMS delivery via API gateway
- **`SlackSender`**: Implements Slack workspace messaging

## Benefits Demonstrated

### 1. **Runtime Implementation Switching**
```java
AlertNotification alert = new AlertNotification(emailSender, "CRITICAL");
alert.send("admin@company.com", "Database error");

// Switch to SMS delivery at runtime
alert.setMessageSender(smsSender);
alert.send("+1234567890", "Now sent via SMS");
```

### 2. **Independent Evolution**
- New notification types can be added without modifying message senders
- New message delivery mechanisms can be added without changing notification logic
- Each abstraction and implementation can evolve independently

### 3. **Multiple Implementations per Abstraction**
The same notification type can use different delivery mechanisms:
- Critical alerts via email for detailed information
- Urgent alerts via SMS for immediate attention
- Team notifications via Slack for collaboration

### 4. **Hiding Implementation Details**
Notification classes don't need to know:
- SMTP server configurations
- SMS API authentication details
- Slack workspace setup

## Real-World Applications

This pattern is commonly used in:

1. **Multi-channel Communication Systems**
   - Customer notifications across email, SMS, push notifications
   - Internal alerts through various team communication tools

2. **Platform-Agnostic Services**
   - Cross-platform UI frameworks
   - Database abstraction layers
   - Cloud service adapters

3. **Plugin Architectures**
   - Payment processing with multiple providers
   - Authentication systems with various identity providers
   - Content delivery networks with different backends

## Usage Example

```java
// Create notification with email delivery
AlertNotification alert = new AlertNotification(emailSender, "WARNING");
alert.send("admin@company.com", "Server CPU usage high");

// Create marketing campaign with Slack delivery
MarketingNotification campaign = new MarketingNotification(slackSender, "PROMO2024");
campaign.send("general", "New product launch announcement");

// Switch delivery mechanism at runtime
alert.setMessageSender(smsSender);
alert.send("+1234567890", "Alert now sent via SMS");
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="structural.bridge.messaging.ZMain"

# Or with Java directly
java -cp target/classes structural.bridge.messaging.ZMain
```

## Design Principles Applied

- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension (new senders/notifications), closed for modification
- **Bridge Pattern**: Abstraction and implementation can vary independently
- **Strategy Pattern Elements**: MessageSender implementations are interchangeable strategies