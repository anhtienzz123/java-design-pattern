package behavioral.observer.weatherstationsystem;

import java.util.ArrayList;
import java.util.List;

// Concrete Subject: Tracks temperature and notifies observers
public class WeatherStation implements WeatherSubject {
	private float temperature;
	private List<WeatherObserver> observers = new ArrayList<>();

	@Override
	public void attach(WeatherObserver observer) {
		observers.add(observer);
		System.out.println("Attached observer: " + observer);
	}

	@Override
	public void detach(WeatherObserver observer) {
		observers.remove(observer);
		System.out.println("Detached observer: " + observer);
	}

	@Override
	public void notifyObservers() {
		for (WeatherObserver observer : observers) {
			observer.update(temperature); // Push model
			observer.update(this); // Pull model
		}
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
		System.out.println("Temperature updated to: " + temperature + "°C");
		notifyObservers();
	}

	public float getTemperature() {
		return temperature;
	}
}
