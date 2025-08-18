package behavioral.state.trafficlight;

/**
 * Context: Traffic Light Controller
 * Manages the current state and delegates behavior to state objects.
 * Handles automatic state transitions and emergency overrides.
 */
public class TrafficLightController {
    private TrafficLightState currentState;
    private boolean emergencyMode;
    private final String intersectionId;
    
    // State instances
    private final TrafficLightState redLightState = new RedLightState();
    private final TrafficLightState greenLightState = new GreenLightState();
    private final TrafficLightState yellowLightState = new YellowLightState();
    
    public TrafficLightController(String intersectionId) {
        this.intersectionId = intersectionId;
        this.emergencyMode = false;
        // Initialize with red state for safety
        this.currentState = redLightState;
        System.out.println("Traffic Light Controller initialized for intersection: " + intersectionId);
        System.out.println("Initial state: " + getCurrentLight());
    }
    
    /**
     * Advance to the next state in the traffic light cycle.
     */
    public void advanceToNextState() {
        if (emergencyMode) {
            System.out.println("System in emergency mode - manual reset required");
            return;
        }
        
        System.out.println("\n[" + intersectionId + "] State transition:");
        currentState.nextState(this);
        displayCurrentStatus();
    }
    
    /**
     * Trigger emergency override - immediately switch to red light.
     */
    public void triggerEmergency() {
        System.out.println("\n[" + intersectionId + "] EMERGENCY TRIGGERED!");
        currentState.emergencyOverride(this);
        displayCurrentStatus();
    }
    
    /**
     * Reset emergency mode and return to normal operation.
     */
    public void resetEmergencyMode() {
        if (emergencyMode) {
            emergencyMode = false;
            System.out.println("\n[" + intersectionId + "] Emergency mode reset - resuming normal operation");
            displayCurrentStatus();
        }
    }
    
    /**
     * Simulate automatic cycling through states with timing.
     */
    public void simulateAutomaticCycle(int cycles) {
        System.out.println("\n[" + intersectionId + "] Starting automatic cycle simulation for " + cycles + " cycles");
        
        for (int i = 1; i <= cycles && !emergencyMode; i++) {
            System.out.println("\n--- Cycle " + i + " ---");
            
            // Simulate time passing
            try {
                System.out.println("Current: " + getCurrentLight() + " (will last " + currentState.getDuration() + " seconds)");
                Thread.sleep(1000); // Simulate 1 second for demo purposes
                advanceToNextState();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    // State management methods
    public void setState(TrafficLightState state) {
        this.currentState = state;
    }
    
    public void setEmergencyMode(boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }
    
    public String getCurrentLight() {
        return currentState.getCurrentLight();
    }
    
    public boolean isEmergencyMode() {
        return emergencyMode;
    }
    
    public String getIntersectionId() {
        return intersectionId;
    }
    
    // State accessors for transitions
    public TrafficLightState getRedLightState() {
        return redLightState;
    }
    
    public TrafficLightState getGreenLightState() {
        return greenLightState;
    }
    
    public TrafficLightState getYellowLightState() {
        return yellowLightState;
    }
    
    /**
     * Display current status of the traffic light system.
     */
    public void displayCurrentStatus() {
        System.out.println("Current Status:");
        System.out.println("  Light: " + getCurrentLight());
        System.out.println("  Duration: " + currentState.getDuration() + " seconds");
        System.out.println("  Emergency Mode: " + (emergencyMode ? "ACTIVE" : "INACTIVE"));
        System.out.println("  Intersection: " + intersectionId);
    }
}