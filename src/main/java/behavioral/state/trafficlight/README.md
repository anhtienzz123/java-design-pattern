# State Pattern - Traffic Light Control System

## Overview

This example demonstrates the **State Pattern** using a Traffic Light Control System. The State pattern allows an object to alter its behavior when its internal state changes, appearing as if the object changed its class.

## Use Case: Traffic Light Management

A traffic light system needs to:
- Cycle through different light states (Red, Yellow, Green)
- Handle state-specific behaviors and transitions
- Support emergency overrides
- Maintain timing information for each state
- Provide automatic cycling capabilities

## Implementation Structure

### Components

1. **TrafficLightState (State Interface)**
   - Defines common interface for all concrete states
   - Declares methods for state transitions and behaviors

2. **Concrete States**
   - `RedLightState`: Vehicles stop, pedestrians can cross
   - `GreenLightState`: Vehicles proceed, pedestrians wait  
   - `YellowLightState`: Vehicles prepare to stop

3. **TrafficLightController (Context)**
   - Maintains reference to current state
   - Delegates behavior to current state object
   - Manages state transitions and emergency modes

## Key Features

### State-Specific Behaviors
Each state encapsulates its own behavior:
- **Red State**: 30-second duration, transitions to Green
- **Green State**: 45-second duration, transitions to Yellow
- **Yellow State**: 5-second duration, transitions to Red

### Emergency Override
All states can handle emergency situations by immediately transitioning to Red state and entering emergency mode.

### Automatic Cycling
The system supports automatic state transitions with configurable timing, simulating real-world traffic light operation.

## Benefits of Using State Pattern

1. **Encapsulation**: State-specific logic is contained within individual state classes
2. **Maintainability**: Easy to add new states without modifying existing code
3. **Clarity**: State transitions are explicit and well-defined
4. **Flexibility**: Different behaviors can be easily implemented for each state
5. **Testability**: Each state can be tested independently

## Usage Example

```java
// Create traffic light controller
TrafficLightController trafficLight = new TrafficLightController("Main St & Oak Ave");

// Manual state transitions
trafficLight.advanceToNextState(); // Red -> Green
trafficLight.advanceToNextState(); // Green -> Yellow
trafficLight.advanceToNextState(); // Yellow -> Red

// Emergency override
trafficLight.triggerEmergency(); // Any state -> Red (emergency mode)
trafficLight.resetEmergencyMode(); // Resume normal operation

// Automatic cycling
trafficLight.simulateAutomaticCycle(3); // Run 3 complete cycles
```

## Running the Example

Execute the main class to see the State pattern in action:

```bash
mvn exec:java -Dexec.mainClass="behavioral.state.trafficlight.ZMain"
```

Or compile and run directly:

```bash
javac src/main/java/behavioral/state/trafficlight/*.java
java -cp target/classes behavioral.state.trafficlight.ZMain
```

## Real-World Applications

The State pattern is commonly used in:
- **UI Components**: Button states (enabled, disabled, pressed)
- **Game Development**: Character states (idle, running, jumping, attacking)
- **Protocol Implementations**: Connection states (connecting, connected, disconnected)
- **Workflow Systems**: Document states (draft, review, approved, published)
- **Media Players**: Playback states (stopped, playing, paused, buffering)

## Design Pattern Benefits

This implementation showcases how the State pattern provides:
- **Clean separation of concerns** between different states
- **Easy extension** for new states without modifying existing code
- **Elimination of complex conditional statements** in the context class
- **Better organization** of state-specific behavior and transitions