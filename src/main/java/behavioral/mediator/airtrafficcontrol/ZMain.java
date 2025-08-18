package behavioral.mediator.airtrafficcontrol;

public class ZMain {
    
    private static String repeat(String str, int count) {
        return str.repeat(count);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Mediator Pattern - Air Traffic Control System Demo ===\n");
        
        // Create the mediator (Control Tower)
        ControlTower controlTower = new ControlTower();
        
        // Create different types of aircraft (colleagues)
        CommercialAircraft delta123 = new CommercialAircraft(
            controlTower, "DAL123", "Boeing 737", "Delta Airlines", 180
        );
        
        CargoAircraft fedex456 = new CargoAircraft(
            controlTower, "FDX456", "Boeing 767", 45.5, "Electronics"
        );
        
        PrivateJet gulfstream789 = new PrivateJet(
            controlTower, "N789GS", "Gulfstream G650", "Tech CEO", true
        );
        
        CommercialAircraft united321 = new CommercialAircraft(
            controlTower, "UAL321", "Airbus A320", "United Airlines", 150
        );
        
        CargoAircraft hazmat999 = new CargoAircraft(
            controlTower, "HAZ999", "Boeing 747", 80.0, "Hazmat Chemicals"
        );
        
        System.out.println("1. Aircraft Registration:");
        System.out.println(repeat("=", 40));
        // Register aircraft with control tower
        controlTower.registerAircraft(delta123);
        controlTower.registerAircraft(fedex456);
        controlTower.registerAircraft(gulfstream789);
        controlTower.registerAircraft(united321);
        controlTower.registerAircraft(hazmat999);
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("2. Normal Operations - Landing Requests:");
        System.out.println(repeat("=", 45));
        // Simulate landing requests
        delta123.requestLanding("09L");
        fedex456.requestLanding("09R");
        gulfstream789.requestLanding("09L"); // Should be queued - runway occupied
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("3. Takeoff Operations:");
        System.out.println(repeat("=", 25));
        united321.requestTakeoff("27L");
        hazmat999.requestTakeoff("27R");
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("4. Altitude and Flight Path Changes:");
        System.out.println(repeat("=", 40));
        delta123.requestAltitudeChange(35000);
        fedex456.requestAltitudeChange(37000);
        gulfstream789.requestFlightPathChange("Direct KBOS");
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("5. Weather Update Broadcast:");
        System.out.println(repeat("=", 32));
        controlTower.broadcastWeatherUpdate("Scattered thunderstorms, visibility 3 miles, moderate turbulence expected");
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("6. Emergency Situations:");
        System.out.println(repeat("=", 27));
        
        // Commercial aircraft medical emergency
        delta123.requestPriorityLanding("Medical Emergency - passenger needs immediate attention");
        
        System.out.println();
        
        // Cargo aircraft emergency
        hazmat999.reportCargoShift();
        hazmat999.requestHazmatHandling();
        
        System.out.println();
        
        // Private jet VIP request
        gulfstream789.requestVipServices("Diplomatic immunity protocols");
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("7. System Status Display:");
        System.out.println(repeat("=", 30));
        controlTower.displaySystemStatus();
        
        System.out.println("\n8. Aircraft Status Summary:");
        System.out.println(repeat("=", 32));
        for (Aircraft aircraft : controlTower.getActiveAircraft()) {
            System.out.printf("  • %s%n", aircraft.toString());
        }
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        // Demonstrate aircraft-specific behavior
        System.out.println("9. Aircraft-Specific Demonstrations:");
        System.out.println(repeat("=", 40));
        
        // Show different responses to same weather update
        System.out.println("\n🌩️ Severe Weather Alert:");
        controlTower.broadcastWeatherUpdate("SEVERE: Tornado warning, all aircraft avoid area Delta-7");
        
        System.out.println("\n🚨 Multiple Emergency Scenario:");
        fedex456.declareEmergency("Engine Fire");
        gulfstream789.declareEmergency("Cabin Pressurization Failure");
        
        System.out.println("\n" + repeat("=", 70) + "\n");
        
        System.out.println("=== Key Benefits of Mediator Pattern Demonstrated ===");
        System.out.println("✅ LOOSE COUPLING: Aircraft don't communicate directly with each other");
        System.out.println("✅ CENTRALIZED CONTROL: All coordination logic is in the Control Tower");
        System.out.println("✅ COMPLEX COORDINATION: Handles runway conflicts, altitude separation, emergencies");
        System.out.println("✅ EXTENSIBLE: Easy to add new aircraft types without changing existing code");
        System.out.println("✅ CONSISTENT BEHAVIOR: All communications follow the same protocol");
        System.out.println("✅ EMERGENCY HANDLING: Centralized emergency response coordination");
        
        System.out.println("\n=== Pattern Structure ===");
        System.out.println("AirTrafficControlMediator (Interface)");
        System.out.println("└── ControlTower (Concrete Mediator)");
        System.out.println("");
        System.out.println("Aircraft (Abstract Colleague)");
        System.out.println("├── CommercialAircraft (Concrete Colleague)");
        System.out.println("├── CargoAircraft (Concrete Colleague)");
        System.out.println("└── PrivateJet (Concrete Colleague)");
        System.out.println("");
        System.out.println("All aircraft communicate through the Control Tower mediator,");
        System.out.println("which coordinates operations, manages conflicts, and handles emergencies.");
        
        System.out.println("\n=== Real-World Benefits ===");
        System.out.println("• Prevents aircraft from needing to know about all other aircraft");
        System.out.println("• Centralizes complex air traffic control logic");
        System.out.println("• Handles runway conflicts and altitude separation automatically");
        System.out.println("• Provides consistent emergency response protocols");
        System.out.println("• Makes system easier to maintain and extend");
        System.out.println("• Follows aviation safety principles of centralized control");
    }
}