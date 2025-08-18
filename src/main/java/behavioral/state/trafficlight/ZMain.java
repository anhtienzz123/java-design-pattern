package behavioral.state.trafficlight;

/**
 * Demonstration of the State Pattern using a Traffic Light Control System.
 * 
 * This example shows how the State pattern can be used to manage complex state
 * transitions in a traffic light system with automatic cycling and emergency overrides.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Traffic Light Control System - State Pattern Demo ===\n");
        
        // Create traffic light controller for an intersection
        TrafficLightController trafficLight = new TrafficLightController("Main St & Oak Ave");
        
        // Display initial status
        trafficLight.displayCurrentStatus();
        
        // Test 1: Manual state transitions
        System.out.println("\n=== Test 1: Manual State Transitions ===");
        trafficLight.advanceToNextState(); // Red -> Green
        trafficLight.advanceToNextState(); // Green -> Yellow
        trafficLight.advanceToNextState(); // Yellow -> Red
        
        // Test 2: Emergency override from different states
        System.out.println("\n=== Test 2: Emergency Override ===");
        trafficLight.advanceToNextState(); // Red -> Green
        System.out.println("Simulating emergency vehicle approach...");
        trafficLight.triggerEmergency(); // Green -> Red (emergency)
        
        // Try to advance while in emergency mode
        trafficLight.advanceToNextState(); // Should fail
        
        // Reset emergency and continue
        trafficLight.resetEmergencyMode();
        trafficLight.advanceToNextState(); // Red -> Green
        
        // Test 3: Emergency from Yellow state
        System.out.println("\n=== Test 3: Emergency from Yellow State ===");
        trafficLight.advanceToNextState(); // Green -> Yellow
        trafficLight.triggerEmergency(); // Yellow -> Red (emergency)
        trafficLight.resetEmergencyMode();
        
        // Test 4: Automatic cycling simulation
        System.out.println("\n=== Test 4: Automatic Cycling Simulation ===");
        TrafficLightController autoLight = new TrafficLightController("Broadway & 1st St");
        autoLight.simulateAutomaticCycle(3);
        
        // Test 5: Emergency during automatic cycling
        System.out.println("\n=== Test 5: Emergency During Automatic Operation ===");
        TrafficLightController emergencyTestLight = new TrafficLightController("Highway 101 & Exit 5");
        
        // Start in different state
        emergencyTestLight.advanceToNextState(); // Red -> Green
        
        // Simulate emergency during green light
        new Thread(() -> {
            try {
                Thread.sleep(500); // Wait a bit
                System.out.println("\n[EMERGENCY THREAD] Ambulance approaching!");
                emergencyTestLight.triggerEmergency();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        // Continue automatic operation (will be interrupted by emergency)
        emergencyTestLight.simulateAutomaticCycle(2);
        
        System.out.println("\n=== Demo Complete ===");
        System.out.println("The State pattern allows the traffic light to:");
        System.out.println("1. Encapsulate state-specific behavior in separate classes");
        System.out.println("2. Make state transitions explicit and manageable");
        System.out.println("3. Add new states without modifying existing code");
        System.out.println("4. Handle complex state logic (like emergency overrides)");
    }
}