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

//		== Ouput:
//		Home Theater: Setting up to watch a movie...
//		Projector: Turning on
//		Projector: Setting input to DVD
//		Sound System: Turning on
//		Sound System: Setting volume to 5
//		DVD Player: Turning on
//		DVD Player: Playing movie 'Inception'
//		Home Theater: Movie setup complete!
//		Home Theater: Shutting down...
//		DVD Player: Stopping movie
//		DVD Player: Turning off
//		Sound System: Turning off
//		Projector: Turning off
//		Home Theater: Shutdown complete!
	}
}
