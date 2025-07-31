package behavioral.command.remotecontrol;

// Concrete Command: Turn light off
public class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOff();
    }
    
    @Override
    public void undo() {
        light.turnOn();
    }
    
    @Override
    public String getDescription() {
        return "Turn " + light.getLocation() + " light OFF";
    }
}