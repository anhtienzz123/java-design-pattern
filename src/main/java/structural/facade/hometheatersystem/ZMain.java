package structural.facade.hometheatersystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create subsystem components
		DVDPlayer dvdPlayer = new DVDPlayer();
		Projector projector = new Projector();
		SoundSystem soundSystem = new SoundSystem();

		// Create facade
		HomeTheaterFacade homeTheater = new HomeTheaterFacade(dvdPlayer, projector, soundSystem);

		// Use facade to watch a movie
		homeTheater.watchMovie("Inception");

		// Use facade to end the movie
		homeTheater.endMovie();
	}
}
