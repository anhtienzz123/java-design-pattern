package structural.facade.hometheatersystem;

// Subsystem Class: DVD Player
public class DVDPlayer {
	public void turnOn() {
		System.out.println("DVD Player: Turning on");
	}

	public void play(String movie) {
		System.out.println("DVD Player: Playing movie '" + movie + "'");
	}

	public void stop() {
		System.out.println("DVD Player: Stopping movie");
	}

	public void turnOff() {
		System.out.println("DVD Player: Turning off");
	}
}