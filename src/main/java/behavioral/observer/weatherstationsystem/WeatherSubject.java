package behavioral.observer.weatherstationsystem;

// Subject: Defines methods to manage observers
public interface WeatherSubject {
	void attach(WeatherObserver observer);

	void detach(WeatherObserver observer);

	void notifyObservers();
}
