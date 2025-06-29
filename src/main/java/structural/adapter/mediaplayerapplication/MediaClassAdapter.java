package structural.adapter.mediaplayerapplication;

// Class Adapter: Inherits from Adaptee and implements Target
public class MediaClassAdapter extends LegacyMediaPlayer implements MediaPlayer {
	@Override
	public void play(String audioType, String fileName) {
		if (audioType.equalsIgnoreCase("mp3")) {
			playMp3(fileName); // Call inherited method
		} else {
			System.out.println("Unsupported audio type: " + audioType);
		}
	}
}
