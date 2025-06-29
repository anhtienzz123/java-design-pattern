package structural.facade.hometheatersystem;

// Subsystem Class: Sound System
public class SoundSystem {
	public void turnOn() {
		System.out.println("Sound System: Turning on");
	}

	public void setVolume(int level) {
		System.out.println("Sound System: Setting volume to " + level);
	}

	public void turnOff() {
		System.out.println("Sound System: Turning off");
	}
}