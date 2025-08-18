package behavioral.state.trafficlight;

/**
 * Concrete State: Green Light State
 * Vehicles can proceed, pedestrians should not cross.
 */
public class GreenLightState implements TrafficLightState {
    
    @Override
    public void nextState(TrafficLightController context) {
        System.out.println("Green light -> Yellow light");
        context.setState(context.getYellowLightState());
    }
    
    @Override
    public void emergencyOverride(TrafficLightController context) {
        System.out.println("Emergency override: Green -> Red (immediate stop)");
        context.setState(context.getRedLightState());
        context.setEmergencyMode(true);
    }
    
    @Override
    public String getCurrentLight() {
        return "GREEN";
    }
    
    @Override
    public int getDuration() {
        return 45; // Green light lasts 45 seconds
    }
}