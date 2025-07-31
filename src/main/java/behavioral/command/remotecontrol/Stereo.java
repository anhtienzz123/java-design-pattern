package behavioral.command.remotecontrol;

// Receiver: Stereo System device
public class Stereo {
    private String location;
    private boolean isOn;
    private int volume = 5; // 0-11
    private String source = "CD";
    
    public Stereo(String location) {
        this.location = location;
        this.isOn = false;
    }
    
    public void turnOn() {
        isOn = true;
        System.out.println(location + " stereo is ON");
    }
    
    public void turnOff() {
        isOn = false;
        System.out.println(location + " stereo is OFF");
    }
    
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 11) {
            this.volume = volume;
            if (isOn) {
                System.out.println(location + " stereo volume set to " + volume);
            }
        }
    }
    
    public void setSource(String source) {
        this.source = source;
        if (isOn) {
            System.out.println(location + " stereo source set to " + source);
        }
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getLocation() {
        return location;
    }
}