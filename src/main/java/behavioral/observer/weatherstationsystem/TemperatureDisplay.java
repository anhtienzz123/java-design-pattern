package behavioral.observer.weatherstationsystem;

// Concrete Observer: Displays temperature using push model
public class TemperatureDisplay implements WeatherObserver {
	private String displayName;

	public TemperatureDisplay(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void update(float temperature) {
		System.out.println(displayName + " (Push): Temperature is " + temperature + "°C");
	}

	@Override
	public void update(WeatherStation station) {
		// Optionally ignore pull model
	}

	@Override
	public String toString() {
		return displayName;
	}
}
