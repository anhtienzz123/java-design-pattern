package structural.facade.hometheatersystem;

// Facade: Simplifies interaction with the home theater subsystem
public class HomeTheaterFacade {
	private DVDPlayer dvdPlayer;
	private Projector projector;
	private SoundSystem soundSystem;

	public HomeTheaterFacade(DVDPlayer dvdPlayer, Projector projector, SoundSystem soundSystem) {
		this.dvdPlayer = dvdPlayer;
		this.projector = projector;
		this.soundSystem = soundSystem;
	}

	// Simplified method to watch a movie
	public void watchMovie(String movie) {
		System.out.println("Home Theater: Setting up to watch a movie...");
		projector.turnOn();
		projector.setInput("DVD");
		soundSystem.turnOn();
		soundSystem.setVolume(5);
		dvdPlayer.turnOn();
		dvdPlayer.play(movie);
		System.out.println("Home Theater: Movie setup complete!");
	}

	// Simplified method to end the movie
	public void endMovie() {
		System.out.println("Home Theater: Shutting down...");
		dvdPlayer.stop();
		dvdPlayer.turnOff();
		soundSystem.turnOff();
		projector.turnOff();
		System.out.println("Home Theater: Shutdown complete!");
	}
}
