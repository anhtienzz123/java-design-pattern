package behavioral.command.remotecontrol;

// Concrete Command: Turn light on
public class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOn();
    }
    
    @Override
    public void undo() {
        light.turnOff();
    }
    
    @Override
    public String getDescription() {
        return "Turn " + light.getLocation() + " light ON";
    }
}