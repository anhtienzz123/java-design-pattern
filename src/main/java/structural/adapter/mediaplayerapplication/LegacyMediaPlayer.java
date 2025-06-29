package structural.adapter.mediaplayerapplication;

// Adaptee with incompatible interface
public class LegacyMediaPlayer {
	public void playMp3(String fileName) {
		System.out.println("Playing MP3 file: " + fileName);
	}
}
