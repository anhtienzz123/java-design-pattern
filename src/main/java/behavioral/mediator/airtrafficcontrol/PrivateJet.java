package behavioral.mediator.airtrafficcontrol;

/**
 * Concrete colleague representing a private jet aircraft.
 * Handles VIP operations and flexible scheduling requirements.
 */
public class PrivateJet extends Aircraft {
    private boolean vipStatus;
    private String owner;
    private boolean flexibleSchedule;
    
    public PrivateJet(AirTrafficControlMediator mediator, String callSign, 
                     String aircraftType, String owner, boolean vipStatus) {
        super(mediator, callSign, aircraftType);
        this.owner = owner;
        this.vipStatus = vipStatus;
        this.flexibleSchedule = true; // Private jets typically have flexible schedules
    }
    
    @Override
    public void receiveInstruction(String instruction) {
        System.out.printf("🛩️ [%s] Private jet instruction: %s%n", callSign, instruction);
        
        // Private jet specific handling
        if (vipStatus && instruction.contains("hold")) {
            System.out.printf("   [%s] VIP passenger - requesting priority handling%n", callSign);
        } else if (instruction.contains("cleared")) {
            System.out.printf("   [%s] Proceeding with clearance for %s%n", callSign, owner);
        }
        
        if (flexibleSchedule && instruction.contains("delay")) {
            System.out.printf("   [%s] Flexible schedule - can accommodate delays%n", callSign);
        }
    }
    
    @Override
    public void receiveWeatherUpdate(String weatherInfo) {
        System.out.printf("🌤️ [%s] Private jet weather update: %s%n", callSign, weatherInfo);
        
        // Private jets often have more flexibility with weather
        if (weatherInfo.contains("marginal") || weatherInfo.contains("poor")) {
            if (flexibleSchedule) {
                System.out.printf("   [%s] Flexible schedule allows weather delay if needed%n", callSign);
            } else if (vipStatus) {
                System.out.printf("   [%s] VIP status - requesting alternative arrangements%n", callSign);
            }
        }
    }
    
    @Override
    public void receiveEmergencyAlert(String emergencyInfo) {
        System.out.printf("🚨 [%s] Private jet emergency alert: %s%n", callSign, emergencyInfo);
        
        if (vipStatus) {
            System.out.printf("   [%s] VIP passenger protocols activated%n", callSign);
        }
        
        System.out.printf("   [%s] Owner/Charter: %s%n", callSign, owner);
    }
    
    public void requestVipServices(String serviceType) {
        System.out.printf("👑 [%s] Requesting VIP services: %s for %s%n", callSign, serviceType, owner);
        if (vipStatus) {
            mediator.declareEmergency(this, "VIP Service Request: " + serviceType);
        }
    }
    
    public void requestFlexibleTiming(String reason) {
        System.out.printf("🕒 [%s] Requesting schedule flexibility: %s%n", callSign, reason);
        if (flexibleSchedule) {
            System.out.printf("   [%s] Private schedule allows adjustment%n", callSign);
        }
    }
    
    // Getters and setters
    public boolean isVipStatus() { return vipStatus; }
    public String getOwner() { return owner; }
    public boolean hasFlexibleSchedule() { return flexibleSchedule; }
    
    public void setVipStatus(boolean vipStatus) { this.vipStatus = vipStatus; }
    public void setFlexibleSchedule(boolean flexibleSchedule) { this.flexibleSchedule = flexibleSchedule; }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - Alt: %dft, Status: %s, Owner: %s%s%s", 
                           callSign, aircraftType, currentAltitude, currentStatus, owner,
                           vipStatus ? " [VIP]" : "",
                           flexibleSchedule ? " [FLEX]" : "");
    }
}