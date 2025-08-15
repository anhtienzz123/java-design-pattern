# Abstract Factory Pattern - Notification System

## Overview

This implementation demonstrates the Abstract Factory pattern through a comprehensive notification system that supports multiple platforms (Mobile and Web) with different notification types (Email, SMS, Push).

## Pattern Structure

### Abstract Products
- **Notification**: Interface for all notification types
- **NotificationChannel**: Interface for platform-specific notification channels

### Abstract Factory
- **NotificationFactory**: Abstract factory that defines methods to create notification products

### Concrete Products

#### Mobile Platform
- `MobileEmailNotification`: Mobile-optimized email notifications
- `MobileSMSNotification`: SMS notifications via mobile carrier networks
- `MobilePushNotification`: Push notifications via FCM/APNs
- `MobileNotificationChannel`: Mobile platform notification channel

#### Web Platform
- `WebEmailNotification`: Web-optimized email with rich HTML templates
- `WebSMSNotification`: SMS notifications via web gateways
- `WebPushNotification`: Browser push notifications via Service Workers
- `WebNotificationChannel`: Web platform notification channel

### Concrete Factories
- **MobileNotificationFactory**: Creates mobile-specific notification products
- **WebNotificationFactory**: Creates web-specific notification products

## Key Benefits

1. **Platform Independence**: Client code works with any platform without knowing implementation details
2. **Consistency**: All products from the same factory are guaranteed to be compatible
3. **Extensibility**: Easy to add new platforms or notification types
4. **Separation of Concerns**: Platform-specific logic is encapsulated in respective factories

## Usage Example

```java
// Configure mobile notifications
NotificationFactory mobileFactory = new MobileNotificationFactory();
NotificationChannel mobileChannel = mobileFactory.createNotificationChannel();
Notification mobilePush = mobileFactory.createPushNotification();

// Configure web notifications  
NotificationFactory webFactory = new WebNotificationFactory();
NotificationChannel webChannel = webFactory.createNotificationChannel();
Notification webPush = webFactory.createPushNotification();
```

## Real-World Applications

- **Multi-platform notification services** (mobile apps + web dashboard)
- **Cross-platform messaging systems**
- **Marketing automation platforms** with different delivery channels
- **Alert systems** that need to work across various platforms and devices

## Running the Example

```bash
mvn exec:java -Dexec.mainClass="creational.abstractfactory.notificationsystem.ZMain"
```

This will demonstrate both mobile and web notification systems, showing how the same client code can work with different platform implementations seamlessly.