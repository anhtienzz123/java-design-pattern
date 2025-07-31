package behavioral.command.remotecontrol;

// Concrete Command: Turn stereo off
public class StereoOffCommand implements Command {
    private Stereo stereo;
    private boolean wasOn;
    private int previousVolume;
    private String previousSource;
    
    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public void execute() {
        // Store previous state for undo
        wasOn = stereo.isOn();
        previousVolume = stereo.getVolume();
        previousSource = stereo.getSource();
        
        stereo.turnOff();
    }
    
    @Override
    public void undo() {
        if (wasOn) {
            stereo.turnOn();
            stereo.setVolume(previousVolume);
            stereo.setSource(previousSource);
        }
    }
    
    @Override
    public String getDescription() {
        return "Turn " + stereo.getLocation() + " stereo OFF";
    }
}