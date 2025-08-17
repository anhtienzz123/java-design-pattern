package behavioral.observer.weatherstationsystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create subject
		WeatherStation station = new WeatherStation();

		// Create observers
		WeatherObserver display1 = new TemperatureDisplay("Display 1");
		WeatherObserver display2 = new TemperatureDisplay("Display 2");
		WeatherObserver logger = new TemperatureLogger("Logger");

		// Register observers
		station.attach(display1);
		station.attach(display2);
		station.attach(logger);

		// Update temperature
		station.setTemperature(25.5f);

		// Remove an observer
		station.detach(display2);

		// Update temperature again
		station.setTemperature(26.0f);

		// Add a new observer
		WeatherObserver newDisplay = new TemperatureDisplay("Display 3");
		station.attach(newDisplay);

		// Update temperature
		station.setTemperature(27.0f);
	}
}