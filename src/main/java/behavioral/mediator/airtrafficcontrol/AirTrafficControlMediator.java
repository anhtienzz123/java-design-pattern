package behavioral.mediator.airtrafficcontrol;

import java.util.List;

/**
 * Mediator interface that defines the contract for coordinating
 * communications and operations between aircraft.
 * 
 * This interface encapsulates the complex logic of aircraft coordination,
 * runway management, and flight path coordination.
 */
public interface AirTrafficControlMediator {
    
    /**
     * Register an aircraft with the control tower
     */
    void registerAircraft(Aircraft aircraft);
    
    /**
     * Remove an aircraft from the control system
     */
    void unregisterAircraft(Aircraft aircraft);
    
    /**
     * Handle landing request from an aircraft
     */
    void requestLanding(Aircraft aircraft, String runway);
    
    /**
     * Handle takeoff request from an aircraft
     */
    void requestTakeoff(Aircraft aircraft, String runway);
    
    /**
     * Handle altitude change request
     */
    void requestAltitudeChange(Aircraft aircraft, int newAltitude);
    
    /**
     * Handle flight path change request
     */
    void requestFlightPathChange(Aircraft aircraft, String newFlightPath);
    
    /**
     * Handle emergency situations
     */
    void declareEmergency(Aircraft aircraft, String emergencyType);
    
    /**
     * Send weather updates to all aircraft
     */
    void broadcastWeatherUpdate(String weatherInfo);
    
    /**
     * Get list of all active aircraft in the airspace
     */
    List<Aircraft> getActiveAircraft();
}