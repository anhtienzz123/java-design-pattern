# Mediator Pattern - Air Traffic Control System

## Overview

This example demonstrates the **Mediator Pattern** through a comprehensive air traffic control system. The pattern centralizes complex communication and coordination logic between multiple aircraft types, eliminating the need for direct aircraft-to-aircraft communication while providing sophisticated traffic management capabilities.

## Problem Statement

In an air traffic control environment, multiple aircraft need to coordinate their operations:

- **Landing requests** need runway conflict resolution
- **Takeoff clearances** require runway availability checks  
- **Altitude changes** need separation conflict detection
- **Flight path changes** require traffic deconfliction
- **Emergency situations** need immediate priority handling
- **Weather updates** must be broadcast to all aircraft

Without the Mediator pattern, each aircraft would need to:
- Know about all other aircraft in the airspace
- Implement complex conflict detection logic
- Handle emergency prioritization
- Manage runway availability tracking
- Coordinate weather-related decisions

This would result in:
```java
// Without Mediator - tight coupling and complex interactions
class Aircraft {
    private List<Aircraft> allOtherAircraft; // Tight coupling!
    
    public void requestLanding(String runway) {
        // Each aircraft needs to check conflicts with ALL others
        for (Aircraft other : allOtherAircraft) {
            if (other.isUsingRunway(runway)) {
                // Complex conflict resolution logic in each aircraft
                handleRunwayConflict(other, runway);
            }
        }
    }
}
```

## Solution: Mediator Pattern

The Mediator pattern centralizes all coordination logic in a Control Tower mediator:

```java
// With Mediator - loose coupling and centralized coordination
class Aircraft {
    public void requestLanding(String runway) {
        mediator.requestLanding(this, runway); // Simple delegation
    }
}
```

## Implementation Structure

### Core Components

1. **AirTrafficControlMediator (Mediator Interface)**
   - Defines the contract for aircraft coordination
   - Methods: `requestLanding()`, `requestTakeoff()`, `requestAltitudeChange()`, `declareEmergency()`, etc.

2. **ControlTower (Concrete Mediator)**
   - Implements all coordination logic
   - Manages runway availability, altitude conflicts, emergency prioritization
   - Maintains queues for landing/takeoff operations
   - Broadcasts weather updates and emergency alerts

3. **Aircraft (Abstract Colleague)**
   - Base class for all aircraft types
   - Defines common communication interface
   - Delegates all coordination to the mediator

4. **Concrete Aircraft Types (Concrete Colleagues)**
   - **CommercialAircraft**: Handles passenger operations, priority requests
   - **CargoAircraft**: Manages cargo-specific operations, hazmat handling  
   - **PrivateJet**: Supports VIP operations, flexible scheduling

### Class Diagram

```
AirTrafficControlMediator (Interface)
└── ControlTower (Concrete Mediator)
    ├── manages runway status
    ├── coordinates altitude conflicts
    ├── handles emergency prioritization
    └── broadcasts weather/alerts

Aircraft (Abstract Colleague)
├── CommercialAircraft (passengers, airlines)
├── CargoAircraft (cargo weight, hazmat)
└── PrivateJet (VIP status, flexibility)
```

## Key Features Demonstrated

### 1. Runway Conflict Management
```java
// Control Tower automatically manages runway conflicts
delta123.requestLanding("09L");     // Gets clearance
gulfstream789.requestLanding("09L"); // Automatically queued
```

### 2. Altitude Separation
```java
// Control Tower enforces 1000-foot separation rules
aircraft1.requestAltitudeChange(35000); // Approved
aircraft2.requestAltitudeChange(35500); // Denied - too close
```

### 3. Emergency Priority Handling
```java
// Emergency aircraft get immediate priority
normalAircraft.requestLanding("09L");      // Normal queue
emergencyAircraft.declareEmergency("Medical"); // Immediate priority
```

### 4. Aircraft-Specific Behavior
```java
// Each aircraft type responds differently to the same situations
commercialAircraft.receiveWeatherUpdate("turbulence");
// → "Preparing cabin crew for turbulence"

cargoAircraft.receiveWeatherUpdate("turbulence"); 
// → "Heavy cargo - checking weight limits"

privateJet.receiveWeatherUpdate("turbulence");
// → "Flexible schedule allows delay if needed"
```

### 5. Centralized Broadcasting
```java
// Control Tower broadcasts to all aircraft simultaneously
controlTower.broadcastWeatherUpdate("Severe storm approaching");
// → All aircraft receive update with aircraft-specific handling
```

## Benefits Demonstrated

### ✅ Loose Coupling
- Aircraft don't need to know about each other
- New aircraft types can be added without changing existing aircraft
- Control Tower can be enhanced without modifying aircraft classes

### ✅ Centralized Complex Logic
- All coordination logic is in one place (Control Tower)
- Runway management, altitude conflicts, emergency handling centralized
- Easier to maintain and debug coordination issues

### ✅ Polymorphic Behavior
- Different aircraft types handle the same events differently
- Control Tower treats all aircraft uniformly through the Aircraft interface
- Easy to extend with new aircraft types

### ✅ Emergency Response Coordination
- Centralized emergency protocols
- Automatic priority handling
- Coordinated response across all aircraft

### ✅ Scalable Architecture
- Easy to add new communication types
- New coordination rules can be added to Control Tower
- Aircraft remain simple and focused on their specific behavior

## Real-World Scenarios Demonstrated

### Normal Operations
```java
// Multiple aircraft coordinate through Control Tower
controlTower.registerAircraft(delta123);
controlTower.registerAircraft(cargoFlight);
controlTower.registerAircraft(privateJet);

delta123.requestLanding("09L");      // Commercial landing
cargoFlight.requestTakeoff("27R");   // Cargo takeoff  
privateJet.requestAltitudeChange(41000); // VIP altitude change
```

### Emergency Situations
```java
// Medical emergency gets immediate priority
commercialFlight.requestPriorityLanding("Medical Emergency");
// → Control Tower clears runway, notifies all aircraft, dispatches emergency services

// Hazmat cargo emergency
cargoFlight.reportCargoShift();
// → Special hazmat protocols activated, emergency services notified
```

### Weather Coordination
```java
// Weather affects all aircraft differently
controlTower.broadcastWeatherUpdate("Severe thunderstorms");
// → Commercial: passenger safety protocols
// → Cargo: weight/wind limit checks  
// → Private: flexible scheduling options
```

## When to Use This Pattern

✅ **Use when:**
- Multiple objects need to communicate in complex ways
- Communication logic is complex and centralized coordination is beneficial
- You want to avoid tight coupling between interacting objects
- The interaction rules may change or new participants may be added
- You need to coordinate behavior across different types of objects

❌ **Avoid when:**
- Simple one-to-one or one-to-many communications are sufficient
- The mediator would become overly complex or have too many responsibilities
- Performance is critical and the mediation overhead is unacceptable
- The interaction patterns are very stable and unlikely to change

## Running the Example

Execute the main class to see the complete air traffic control simulation:

```bash
mvn exec:java -Dexec.mainClass="behavioral.mediator.airtrafficcontrol.ZMain"
```

The demo demonstrates:
1. **Aircraft Registration**: Multiple aircraft types registering with control tower
2. **Normal Operations**: Landing/takeoff requests with conflict resolution
3. **Altitude Management**: Altitude changes with separation enforcement
4. **Weather Coordination**: Broadcast updates with aircraft-specific responses
5. **Emergency Handling**: Priority emergency response across different aircraft types
6. **System Status**: Real-time status monitoring and queue management

## Pattern Benefits Summary

- **Decoupling**: Aircraft don't need references to each other
- **Centralization**: Complex coordination logic in one maintainable location  
- **Extensibility**: New aircraft types integrate seamlessly
- **Reusability**: Control Tower logic can coordinate any aircraft types
- **Testability**: Centralized logic is easier to unit test
- **Real-world Modeling**: Accurately reflects actual air traffic control operations

## Advanced Features

### Queue Management
- Automatic landing/takeoff queue processing
- Priority handling for emergency situations
- Efficient runway utilization optimization

### Conflict Detection
- Runway availability tracking
- Altitude separation enforcement (1000+ feet)
- Flight path conflict resolution

### Emergency Protocols
- Immediate priority for medical emergencies
- Hazmat cargo special handling
- VIP service coordination
- Emergency services notification

### Weather Integration
- Real-time weather broadcast
- Aircraft-specific weather response
- Operational impact assessment

This implementation showcases how the Mediator pattern enables sophisticated coordination in complex systems while maintaining clean, maintainable, and extensible code architecture.