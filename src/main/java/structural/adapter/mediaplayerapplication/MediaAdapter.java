package structural.adapter.mediaplayerapplication;

// Object Adapter: Implements Target and holds Adaptee
public class MediaAdapter implements MediaPlayer {
	private LegacyMediaPlayer legacyPlayer;

	public MediaAdapter() {
		this.legacyPlayer = new LegacyMediaPlayer();
	}

	@Override
	public void play(String audioType, String fileName) {
		if (audioType.equalsIgnoreCase("mp3")) {
			legacyPlayer.playMp3(fileName); // Delegate to Adaptee
		} else {
			System.out.println("Unsupported audio type: " + audioType);
		}
	}
}
