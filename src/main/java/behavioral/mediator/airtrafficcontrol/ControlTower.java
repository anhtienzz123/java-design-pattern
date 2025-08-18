package behavioral.mediator.airtrafficcontrol;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Concrete mediator that implements the air traffic control system.
 * Coordinates all communications and operations between aircraft,
 * manages runway assignments, altitude separations, and emergency situations.
 */
public class ControlTower implements AirTrafficControlMediator {
    private final Map<String, Aircraft> activeAircraft;
    private final Map<String, Boolean> runwayStatus; // runway -> available
    private final Set<Integer> occupiedAltitudes;
    private final Queue<Aircraft> landingQueue;
    private final Queue<Aircraft> takeoffQueue;
    private String currentWeather;
    
    public ControlTower() {
        this.activeAircraft = new ConcurrentHashMap<>();
        this.runwayStatus = new HashMap<>();
        this.occupiedAltitudes = new HashSet<>();
        this.landingQueue = new LinkedList<>();
        this.takeoffQueue = new LinkedList<>();
        this.currentWeather = "Clear skies, visibility 10+ miles";
        
        // Initialize runways
        initializeRunways();
    }
    
    private void initializeRunways() {
        runwayStatus.put("09L", true);
        runwayStatus.put("09R", true);
        runwayStatus.put("27L", true);
        runwayStatus.put("27R", true);
    }
    
    @Override
    public void registerAircraft(Aircraft aircraft) {
        activeAircraft.put(aircraft.getCallSign(), aircraft);
        System.out.printf("🏗️ [CONTROL] Aircraft %s registered with control tower%n", aircraft.getCallSign());
        
        // Send current weather to newly registered aircraft
        aircraft.receiveWeatherUpdate(currentWeather);
    }
    
    @Override
    public void unregisterAircraft(Aircraft aircraft) {
        activeAircraft.remove(aircraft.getCallSign());
        occupiedAltitudes.remove(aircraft.getCurrentAltitude());
        System.out.printf("📤 [CONTROL] Aircraft %s departed controlled airspace%n", aircraft.getCallSign());
    }
    
    @Override
    public void requestLanding(Aircraft aircraft, String preferredRunway) {
        System.out.printf("🎯 [CONTROL] Processing landing request from %s%n", aircraft.getCallSign());
        
        if (runwayStatus.getOrDefault(preferredRunway, false)) {
            // Runway available
            runwayStatus.put(preferredRunway, false);
            aircraft.setAssignedRunway(preferredRunway);
            aircraft.setCurrentStatus("Cleared for Landing");
            
            String instruction = String.format("Cleared to land runway %s, winds calm, temperature 22°C", 
                                             preferredRunway);
            aircraft.receiveInstruction(instruction);
            
            // Notify other aircraft about runway usage
            notifyAircraftAboutRunwayStatus(aircraft, preferredRunway, "LANDING");
            
        } else {
            // Runway occupied - add to queue
            landingQueue.offer(aircraft);
            aircraft.setCurrentStatus("Holding for Landing");
            
            String instruction = String.format("Hold short, runway %s occupied. Number %d in landing queue", 
                                             preferredRunway, landingQueue.size());
            aircraft.receiveInstruction(instruction);
        }
    }
    
    @Override
    public void requestTakeoff(Aircraft aircraft, String runway) {
        System.out.printf("🎯 [CONTROL] Processing takeoff request from %s%n", aircraft.getCallSign());
        
        if (runwayStatus.getOrDefault(runway, false)) {
            // Runway available
            runwayStatus.put(runway, false);
            aircraft.setAssignedRunway(runway);
            aircraft.setCurrentStatus("Cleared for Takeoff");
            
            String instruction = String.format("Cleared for takeoff runway %s, wind 270 at 8 knots", runway);
            aircraft.receiveInstruction(instruction);
            
            // Process takeoff completion after a brief moment
            processTakeoffCompletion(aircraft, runway);
            
        } else {
            // Runway occupied - add to queue
            takeoffQueue.offer(aircraft);
            aircraft.setCurrentStatus("Holding for Takeoff");
            
            String instruction = String.format("Hold short runway %s. Number %d for takeoff", 
                                             runway, takeoffQueue.size());
            aircraft.receiveInstruction(instruction);
        }
    }
    
    @Override
    public void requestAltitudeChange(Aircraft aircraft, int newAltitude) {
        System.out.printf("🎯 [CONTROL] Processing altitude change request from %s%n", aircraft.getCallSign());
        
        // Check for altitude conflicts
        if (isAltitudeSafe(newAltitude, aircraft.getCurrentAltitude())) {
            occupiedAltitudes.remove(aircraft.getCurrentAltitude());
            occupiedAltitudes.add(newAltitude);
            aircraft.setCurrentAltitude(newAltitude);
            
            String instruction = String.format("Cleared to %d feet, maintain heading", newAltitude);
            aircraft.receiveInstruction(instruction);
            
        } else {
            String instruction = String.format("Unable altitude %d feet due to traffic. Maintain current altitude", 
                                             newAltitude);
            aircraft.receiveInstruction(instruction);
        }
    }
    
    @Override
    public void requestFlightPathChange(Aircraft aircraft, String newFlightPath) {
        System.out.printf("🎯 [CONTROL] Processing flight path change from %s%n", aircraft.getCallSign());
        
        // Check for conflicts with other aircraft paths
        boolean pathClear = checkFlightPathConflicts(newFlightPath);
        
        if (pathClear) {
            String instruction = String.format("Cleared to proceed direct %s, maintain current altitude", 
                                             newFlightPath);
            aircraft.receiveInstruction(instruction);
        } else {
            String instruction = String.format("Unable direct %s due to traffic. Maintain current routing", 
                                             newFlightPath);
            aircraft.receiveInstruction(instruction);
        }
    }
    
    @Override
    public void declareEmergency(Aircraft aircraft, String emergencyType) {
        System.out.printf("🚨 [CONTROL] EMERGENCY DECLARED by %s: %s%n", 
                         aircraft.getCallSign(), emergencyType);
        
        aircraft.setCurrentStatus("EMERGENCY");
        
        // Clear priority handling for emergency aircraft
        if (emergencyType.toLowerCase().contains("landing") || 
            emergencyType.toLowerCase().contains("medical")) {
            clearRunwayForEmergency(aircraft);
        }
        
        // Notify all other aircraft about the emergency
        String emergencyAlert = String.format("Emergency traffic: %s - %s", 
                                             aircraft.getCallSign(), emergencyType);
        
        for (Aircraft otherAircraft : activeAircraft.values()) {
            if (!otherAircraft.getCallSign().equals(aircraft.getCallSign())) {
                otherAircraft.receiveEmergencyAlert(emergencyAlert);
            }
        }
        
        // Provide immediate assistance to emergency aircraft
        String instruction = String.format("Emergency services notified. Cleared for immediate priority handling. " +
                                          "State your intentions");
        aircraft.receiveInstruction(instruction);
    }
    
    @Override
    public void broadcastWeatherUpdate(String weatherInfo) {
        this.currentWeather = weatherInfo;
        System.out.printf("🌤️ [CONTROL] Broadcasting weather update to all aircraft%n");
        
        for (Aircraft aircraft : activeAircraft.values()) {
            aircraft.receiveWeatherUpdate(weatherInfo);
        }
        
        // Check if weather affects operations
        if (weatherInfo.contains("severe") || weatherInfo.contains("storm")) {
            System.out.printf("⚠️ [CONTROL] Severe weather - implementing weather protocols%n");
        }
    }
    
    @Override
    public List<Aircraft> getActiveAircraft() {
        return new ArrayList<>(activeAircraft.values());
    }
    
    // Helper methods
    
    private boolean isAltitudeSafe(int requestedAltitude, int currentAltitude) {
        // Check for 1000 feet separation
        for (int occupiedAlt : occupiedAltitudes) {
            if (occupiedAlt != currentAltitude && Math.abs(occupiedAlt - requestedAltitude) < 1000) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkFlightPathConflicts(String newFlightPath) {
        // Simplified conflict detection
        return !newFlightPath.toLowerCase().contains("restricted");
    }
    
    private void notifyAircraftAboutRunwayStatus(Aircraft landingAircraft, String runway, String operation) {
        String notification = String.format("Runway %s in use by %s for %s", 
                                           runway, landingAircraft.getCallSign(), operation);
        
        for (Aircraft aircraft : activeAircraft.values()) {
            if (!aircraft.getCallSign().equals(landingAircraft.getCallSign())) {
                aircraft.receiveInstruction("ADVISORY: " + notification);
            }
        }
    }
    
    private void processTakeoffCompletion(Aircraft aircraft, String runway) {
        // Simulate takeoff completion
        System.out.printf("✈️ [CONTROL] %s airborne from runway %s%n", aircraft.getCallSign(), runway);
        aircraft.setCurrentStatus("Airborne");
        aircraft.setCurrentAltitude(1000); // Initial climb altitude
        occupiedAltitudes.add(1000);
        
        // Free up runway
        runwayStatus.put(runway, true);
        
        // Process next aircraft in takeoff queue
        processNextInQueue();
    }
    
    private void clearRunwayForEmergency(Aircraft emergencyAircraft) {
        // Find best available runway for emergency
        for (Map.Entry<String, Boolean> runway : runwayStatus.entrySet()) {
            if (runway.getValue()) {
                runwayStatus.put(runway.getKey(), false);
                emergencyAircraft.setAssignedRunway(runway.getKey());
                
                String instruction = String.format("EMERGENCY CLEARED runway %s, emergency vehicles dispatched", 
                                                 runway.getKey());
                emergencyAircraft.receiveInstruction(instruction);
                break;
            }
        }
    }
    
    private void processNextInQueue() {
        // Process next landing if runway available
        if (!landingQueue.isEmpty()) {
            Aircraft nextLanding = landingQueue.poll();
            if (nextLanding != null) {
                requestLanding(nextLanding, nextLanding.getAssignedRunway());
            }
        }
        
        // Process next takeoff if runway available
        if (!takeoffQueue.isEmpty()) {
            Aircraft nextTakeoff = takeoffQueue.poll();
            if (nextTakeoff != null) {
                requestTakeoff(nextTakeoff, nextTakeoff.getAssignedRunway());
            }
        }
    }
    
    public void displaySystemStatus() {
        System.out.println("\n📊 [CONTROL TOWER STATUS]");
        System.out.println("=".repeat(50));
        System.out.printf("Active Aircraft: %d%n", activeAircraft.size());
        System.out.printf("Current Weather: %s%n", currentWeather);
        System.out.println("\nRunway Status:");
        for (Map.Entry<String, Boolean> runway : runwayStatus.entrySet()) {
            System.out.printf("  %s: %s%n", runway.getKey(), 
                             runway.getValue() ? "AVAILABLE" : "OCCUPIED");
        }
        System.out.printf("Landing Queue: %d aircraft%n", landingQueue.size());
        System.out.printf("Takeoff Queue: %d aircraft%n", takeoffQueue.size());
        System.out.println("=".repeat(50));
    }
}