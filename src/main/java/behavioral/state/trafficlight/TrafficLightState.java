package behavioral.state.trafficlight;

/**
 * State interface for traffic light states.
 * Each state defines behavior for different traffic light phases.
 */
public interface TrafficLightState {
    
    /**
     * Handle the next transition in the traffic light cycle.
     * @param context The traffic light controller context
     */
    void nextState(TrafficLightController context);
    
    /**
     * Handle emergency override (immediately go to red state).
     * @param context The traffic light controller context
     */
    void emergencyOverride(TrafficLightController context);
    
    /**
     * Get the current light color.
     * @return The current light color as a string
     */
    String getCurrentLight();
    
    /**
     * Get the duration this state should remain active (in seconds).
     * @return Duration in seconds
     */
    int getDuration();
}