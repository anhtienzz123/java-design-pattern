package structural.adapter.mediaplayerapplication;

// Client: AudioPlayer
public class AudioPlayer implements MediaPlayer {
	private MediaPlayer adapter;

	@Override
	public void play(String audioType, String fileName) {
		if (audioType.equalsIgnoreCase("mp3")) {
			// Use Object Adapter
			adapter = new MediaAdapter();
			adapter.play(audioType, fileName);
			// Alternatively, use Class Adapter
			// adapter = new MediaClassAdapter();
			// adapter.play(audioType, fileName);
		} else if (audioType.equalsIgnoreCase("wav") || audioType.equalsIgnoreCase("mp4")) {
			System.out.println("Playing " + audioType + " file: " + fileName);
		} else {
			System.out.println("Invalid media type: " + audioType);
		}
	}
}
