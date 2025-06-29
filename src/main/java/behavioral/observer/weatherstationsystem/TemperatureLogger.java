package behavioral.observer.weatherstationsystem;

// Concrete Observer: Logs temperature using pull model
public class TemperatureLogger implements WeatherObserver {
	private String loggerName;

	public TemperatureLogger(String loggerName) {
		this.loggerName = loggerName;
	}

	@Override
	public void update(float temperature) {
		// Optionally ignore push model
	}

	@Override
	public void update(WeatherStation station) {
		System.out.println(loggerName + " (Pull): Logged temperature: " + station.getTemperature() + "°C");
	}

	@Override
	public String toString() {
		return loggerName;
	}
}
