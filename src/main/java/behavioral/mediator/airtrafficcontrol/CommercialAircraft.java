package behavioral.mediator.airtrafficcontrol;

/**
 * Concrete colleague representing a commercial passenger aircraft.
 * Handles instructions and communications specific to commercial operations.
 */
public class CommercialAircraft extends Aircraft {
    private int passengerCount;
    private String airline;
    private boolean priorityHandling;
    
    public CommercialAircraft(AirTrafficControlMediator mediator, String callSign, 
                             String aircraftType, String airline, int passengerCount) {
        super(mediator, callSign, aircraftType);
        this.airline = airline;
        this.passengerCount = passengerCount;
        this.priorityHandling = false;
    }
    
    @Override
    public void receiveInstruction(String instruction) {
        System.out.printf("✈️ [%s - %s] Received instruction: %s%n", callSign, airline, instruction);
        
        // Commercial aircraft specific handling
        if (instruction.contains("hold")) {
            System.out.printf("   [%s] Informing %d passengers about delay%n", callSign, passengerCount);
        } else if (instruction.contains("cleared")) {
            System.out.printf("   [%s] Proceeding with clearance - %d passengers aboard%n", callSign, passengerCount);
        }
    }
    
    @Override
    public void receiveWeatherUpdate(String weatherInfo) {
        System.out.printf("🌤️ [%s] Weather update received: %s%n", callSign, weatherInfo);
        
        // Assess weather impact on commercial operations
        if (weatherInfo.contains("turbulence")) {
            System.out.printf("   [%s] Preparing cabin crew for potential turbulence%n", callSign);
        } else if (weatherInfo.contains("storm") || weatherInfo.contains("severe")) {
            System.out.printf("   [%s] Considering passenger safety protocols%n", callSign);
            setPriorityHandling(true);
        }
    }
    
    @Override
    public void receiveEmergencyAlert(String emergencyInfo) {
        System.out.printf("🚨 [%s] Emergency alert: %s%n", callSign, emergencyInfo);
        System.out.printf("   [%s] Implementing emergency passenger protocols%n", callSign);
        
        if (emergencyInfo.contains("runway") || emergencyInfo.contains("airport")) {
            System.out.printf("   [%s] Preparing for possible diversion with %d passengers%n", 
                             callSign, passengerCount);
        }
    }
    
    public void requestPriorityLanding(String reason) {
        System.out.printf("🔴 [%s] Requesting priority landing: %s (%d passengers)%n", 
                         callSign, reason, passengerCount);
        setPriorityHandling(true);
        mediator.declareEmergency(this, "Priority Landing: " + reason);
    }
    
    // Getters and setters
    public int getPassengerCount() { return passengerCount; }
    public String getAirline() { return airline; }
    public boolean isPriorityHandling() { return priorityHandling; }
    public void setPriorityHandling(boolean priorityHandling) { this.priorityHandling = priorityHandling; }
    
    @Override
    public String toString() {
        return String.format("%s %s (%s) - Alt: %dft, Status: %s, Passengers: %d%s", 
                           airline, callSign, aircraftType, currentAltitude, currentStatus, 
                           passengerCount, priorityHandling ? " [PRIORITY]" : "");
    }
}