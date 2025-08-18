package behavioral.mediator.airtrafficcontrol;

/**
 * Concrete colleague representing a cargo aircraft.
 * Handles cargo-specific operations and communications.
 */
public class CargoAircraft extends Aircraft {
    private double cargoWeight;
    private String cargoType;
    private boolean hazardousMaterials;
    
    public CargoAircraft(AirTrafficControlMediator mediator, String callSign, 
                        String aircraftType, double cargoWeight, String cargoType) {
        super(mediator, callSign, aircraftType);
        this.cargoWeight = cargoWeight;
        this.cargoType = cargoType;
        this.hazardousMaterials = cargoType.toLowerCase().contains("hazmat") || 
                                 cargoType.toLowerCase().contains("chemicals");
    }
    
    @Override
    public void receiveInstruction(String instruction) {
        System.out.printf("📦 [%s] Cargo flight received instruction: %s%n", callSign, instruction);
        
        // Cargo aircraft specific handling
        if (instruction.contains("weight") || instruction.contains("performance")) {
            System.out.printf("   [%s] Cargo weight: %.1f tons, Type: %s%n", 
                             callSign, cargoWeight, cargoType);
        }
        
        if (hazardousMaterials && instruction.contains("emergency")) {
            System.out.printf("   [%s] ⚠️ HAZMAT cargo aboard - special handling required%n", callSign);
        }
    }
    
    @Override
    public void receiveWeatherUpdate(String weatherInfo) {
        System.out.printf("🌤️ [%s] Weather update for cargo flight: %s%n", callSign, weatherInfo);
        
        // Cargo-specific weather considerations
        if (weatherInfo.contains("wind") && cargoWeight > 50.0) {
            System.out.printf("   [%s] Heavy cargo (%.1f tons) - checking wind limits%n", 
                             callSign, cargoWeight);
        }
        
        if (hazardousMaterials && (weatherInfo.contains("lightning") || weatherInfo.contains("storm"))) {
            System.out.printf("   [%s] ⚠️ HAZMAT cargo - avoiding electrical activity%n", callSign);
        }
    }
    
    @Override
    public void receiveEmergencyAlert(String emergencyInfo) {
        System.out.printf("🚨 [%s] Cargo flight emergency alert: %s%n", callSign, emergencyInfo);
        
        if (hazardousMaterials) {
            System.out.printf("   [%s] ⚠️ HAZMAT cargo notification sent to emergency services%n", callSign);
        }
        
        System.out.printf("   [%s] Cargo manifest: %.1f tons of %s%n", 
                         callSign, cargoWeight, cargoType);
    }
    
    public void reportCargoShift() {
        System.out.printf("⚠️ [%s] Cargo shift detected - requesting priority handling%n", callSign);
        mediator.declareEmergency(this, "Cargo Shift - " + cargoWeight + " tons " + cargoType);
    }
    
    public void requestHazmatHandling() {
        if (hazardousMaterials) {
            System.out.printf("☢️ [%s] Requesting hazmat handling procedures%n", callSign);
            mediator.declareEmergency(this, "Hazardous Materials Handling Required");
        }
    }
    
    // Getters and setters
    public double getCargoWeight() { return cargoWeight; }
    public String getCargoType() { return cargoType; }
    public boolean hasHazardousMaterials() { return hazardousMaterials; }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - Alt: %dft, Status: %s, Cargo: %.1f tons %s%s", 
                           callSign, aircraftType, currentAltitude, currentStatus, 
                           cargoWeight, cargoType, hazardousMaterials ? " [HAZMAT]" : "");
    }
}