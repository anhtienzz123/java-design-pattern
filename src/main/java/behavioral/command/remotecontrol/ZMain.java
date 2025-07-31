package behavioral.command.remotecontrol;

// Client code - Remote Control System Demo
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Smart Home Remote Control System ===");
        
        // Create receivers (smart devices)
        Light livingRoomLight = new Light("Living Room");
        Light bedroomLight = new Light("Bedroom");
        Stereo kitchenStereo = new Stereo("Kitchen");
        
        // Create invoker (remote control)
        RemoteControl remote = new RemoteControl();
        
        // Create commands for individual devices
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        
        LightOnCommand bedroomLightOn = new LightOnCommand(bedroomLight);
        LightOffCommand bedroomLightOff = new LightOffCommand(bedroomLight);
        
        StereoOnCommand kitchenStereoOn = new StereoOnCommand(kitchenStereo);
        StereoOffCommand kitchenStereoOff = new StereoOffCommand(kitchenStereo);
        
        // Set up remote control slots
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, bedroomLightOn, bedroomLightOff);
        remote.setCommand(2, kitchenStereoOn, kitchenStereoOff);
        
        // Create macro commands for "Party Mode" and "Sleep Mode"
        Command[] partyCommands = {
            livingRoomLightOn,
            bedroomLightOn,
            kitchenStereoOn
        };
        Command[] sleepCommands = {
            livingRoomLightOff,
            bedroomLightOff,
            kitchenStereoOff
        };
        
        MacroCommand partyMode = new MacroCommand(partyCommands, "Party Mode");
        MacroCommand sleepMode = new MacroCommand(sleepCommands, "Sleep Mode");
        
        remote.setCommand(6, partyMode, sleepMode);
        
        // Display initial remote status
        remote.displayStatus();
        
        // Test individual commands
        System.out.println("=== Testing Individual Commands ===");
        remote.onButtonPressed(0);  // Living room light on
        remote.onButtonPressed(2);  // Kitchen stereo on
        remote.offButtonPressed(0); // Living room light off
        
        System.out.println("\n=== Testing Undo Functionality ===");
        remote.undoButtonPressed(); // Undo living room light off
        remote.undoButtonPressed(); // Undo kitchen stereo on
        remote.undoButtonPressed(); // Undo living room light on
        remote.undoButtonPressed(); // Nothing to undo
        
        System.out.println("\n=== Testing Macro Commands ===");
        remote.onButtonPressed(6);  // Party mode
        
        System.out.println("\n--- Current device states ---");
        System.out.println("Living Room Light: " + (livingRoomLight.isOn() ? "ON" : "OFF"));
        System.out.println("Bedroom Light: " + (bedroomLight.isOn() ? "ON" : "OFF"));
        System.out.println("Kitchen Stereo: " + (kitchenStereo.isOn() ? "ON" : "OFF"));
        
        System.out.println("\n=== Undoing Party Mode ===");
        remote.undoButtonPressed(); // Undo party mode
        
        System.out.println("\n--- Final device states ---");
        System.out.println("Living Room Light: " + (livingRoomLight.isOn() ? "ON" : "OFF"));
        System.out.println("Bedroom Light: " + (bedroomLight.isOn() ? "ON" : "OFF"));
        System.out.println("Kitchen Stereo: " + (kitchenStereo.isOn() ? "ON" : "OFF"));
        
        System.out.println("\n=== Testing Sleep Mode ===");
        // First turn some devices on
        remote.onButtonPressed(0); // Living room light on
        remote.onButtonPressed(1); // Bedroom light on
        remote.onButtonPressed(2); // Kitchen stereo on
        
        // Now use sleep mode to turn everything off
        remote.offButtonPressed(6); // Sleep mode
        
        // Display final remote status
        remote.displayStatus();
    }
}