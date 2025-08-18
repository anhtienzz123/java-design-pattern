package behavioral.state.trafficlight;

/**
 * Concrete State: Red Light State
 * Vehicles must stop, pedestrians can cross.
 */
public class RedLightState implements TrafficLightState {
    
    @Override
    public void nextState(TrafficLightController context) {
        System.out.println("Red light -> Green light");
        context.setState(context.getGreenLightState());
    }
    
    @Override
    public void emergencyOverride(TrafficLightController context) {
        System.out.println("Emergency override: Already in Red state - maintaining red light");
        // Already in safe state, no transition needed
    }
    
    @Override
    public String getCurrentLight() {
        return "RED";
    }
    
    @Override
    public int getDuration() {
        return 30; // Red light lasts 30 seconds
    }
}