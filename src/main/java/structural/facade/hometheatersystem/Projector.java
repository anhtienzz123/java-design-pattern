package structural.facade.hometheatersystem;

// Subsystem Class: Projector
public class Projector {
	public void turnOn() {
		System.out.println("Projector: Turning on");
	}

	public void setInput(String input) {
		System.out.println("Projector: Setting input to " + input);
	}

	public void turnOff() {
		System.out.println("Projector: Turning off");
	}
}
