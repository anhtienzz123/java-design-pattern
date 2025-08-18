package behavioral.state.trafficlight;

/**
 * Concrete State: Yellow Light State
 * Vehicles should prepare to stop, pedestrians should not start crossing.
 */
public class YellowLightState implements TrafficLightState {
    
    @Override
    public void nextState(TrafficLightController context) {
        System.out.println("Yellow light -> Red light");
        context.setState(context.getRedLightState());
    }
    
    @Override
    public void emergencyOverride(TrafficLightController context) {
        System.out.println("Emergency override: Yellow -> Red (immediate stop)");
        context.setState(context.getRedLightState());
        context.setEmergencyMode(true);
    }
    
    @Override
    public String getCurrentLight() {
        return "YELLOW";
    }
    
    @Override
    public int getDuration() {
        return 5; // Yellow light lasts 5 seconds
    }
}