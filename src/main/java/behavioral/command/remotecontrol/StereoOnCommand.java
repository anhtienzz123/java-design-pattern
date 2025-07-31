package behavioral.command.remotecontrol;

// Concrete Command: Turn stereo on with default settings
public class StereoOnCommand implements Command {
    private Stereo stereo;
    private int previousVolume;
    private String previousSource;
    
    public StereoOnCommand(Stereo stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public void execute() {
        // Store previous state for undo
        previousVolume = stereo.getVolume();
        previousSource = stereo.getSource();
        
        stereo.turnOn();
        stereo.setVolume(7);
        stereo.setSource("Bluetooth");
    }
    
    @Override
    public void undo() {
        stereo.setVolume(previousVolume);
        stereo.setSource(previousSource);
        stereo.turnOff();
    }
    
    @Override
    public String getDescription() {
        return "Turn " + stereo.getLocation() + " stereo ON (Bluetooth, vol 7)";
    }
}