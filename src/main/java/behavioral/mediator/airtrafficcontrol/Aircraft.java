package behavioral.mediator.airtrafficcontrol;

/**
 * Abstract aircraft class that represents the colleague interface.
 * All aircraft types must implement this interface to participate
 * in the air traffic control communication system.
 */
public abstract class Aircraft {
    protected AirTrafficControlMediator mediator;
    protected String callSign;
    protected String aircraftType;
    protected int currentAltitude;
    protected String currentStatus;
    protected String assignedRunway;
    
    public Aircraft(AirTrafficControlMediator mediator, String callSign, String aircraftType) {
        this.mediator = mediator;
        this.callSign = callSign;
        this.aircraftType = aircraftType;
        this.currentAltitude = 0;
        this.currentStatus = "Ground";
        this.assignedRunway = null;
    }
    
    // Abstract methods that concrete aircraft must implement
    public abstract void receiveInstruction(String instruction);
    public abstract void receiveWeatherUpdate(String weatherInfo);
    public abstract void receiveEmergencyAlert(String emergencyInfo);
    
    // Common methods for all aircraft
    public void requestLanding(String preferredRunway) {
        System.out.printf("🛬 [%s] Requesting landing clearance on runway %s%n", callSign, preferredRunway);
        mediator.requestLanding(this, preferredRunway);
    }
    
    public void requestTakeoff(String runway) {
        System.out.printf("🛫 [%s] Requesting takeoff clearance from runway %s%n", callSign, runway);
        mediator.requestTakeoff(this, runway);
    }
    
    public void requestAltitudeChange(int newAltitude) {
        System.out.printf("📈 [%s] Requesting altitude change to %d feet%n", callSign, newAltitude);
        mediator.requestAltitudeChange(this, newAltitude);
    }
    
    public void requestFlightPathChange(String newFlightPath) {
        System.out.printf("🗺️ [%s] Requesting flight path change to %s%n", callSign, newFlightPath);
        mediator.requestFlightPathChange(this, newFlightPath);
    }
    
    public void declareEmergency(String emergencyType) {
        System.out.printf("🚨 [%s] EMERGENCY: %s%n", callSign, emergencyType);
        mediator.declareEmergency(this, emergencyType);
    }
    
    // Getters and setters
    public String getCallSign() { return callSign; }
    public String getAircraftType() { return aircraftType; }
    public int getCurrentAltitude() { return currentAltitude; }
    public String getCurrentStatus() { return currentStatus; }
    public String getAssignedRunway() { return assignedRunway; }
    
    public void setCurrentAltitude(int altitude) { this.currentAltitude = altitude; }
    public void setCurrentStatus(String status) { this.currentStatus = status; }
    public void setAssignedRunway(String runway) { this.assignedRunway = runway; }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - Alt: %dft, Status: %s", 
                           callSign, aircraftType, currentAltitude, currentStatus);
    }
}