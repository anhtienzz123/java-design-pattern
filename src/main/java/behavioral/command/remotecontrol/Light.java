package behavioral.command.remotecontrol;

// Receiver: Smart Light device
public class Light {
    private String location;
    private boolean isOn;
    private int brightness = 50; // 0-100
    
    public Light(String location) {
        this.location = location;
        this.isOn = false;
    }
    
    public void turnOn() {
        isOn = true;
        System.out.println(location + " light is ON (brightness: " + brightness + "%)");
    }
    
    public void turnOff() {
        isOn = false;
        System.out.println(location + " light is OFF");
    }
    
    public void setBrightness(int level) {
        if (level >= 0 && level <= 100) {
            this.brightness = level;
            if (isOn) {
                System.out.println(location + " light brightness set to " + brightness + "%");
            }
        }
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public int getBrightness() {
        return brightness;
    }
    
    public String getLocation() {
        return location;
    }
}