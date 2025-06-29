package behavioral.observer.weatherstationsystem;

// Observer: Defines the interface for update notifications
public interface WeatherObserver {
	void update(float temperature); // Push model: Receive temperature directly

	void update(WeatherStation station); // Pull model: Query the subject
}