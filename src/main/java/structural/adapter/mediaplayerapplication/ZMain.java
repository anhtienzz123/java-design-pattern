package structural.adapter.mediaplayerapplication;

//Usage
public class ZMain {
	public static void main(String[] args) {
		AudioPlayer player = new AudioPlayer();
		player.play("mp3", "song.mp3");
		player.play("wav", "audio.wav");
		player.play("mp4", "video.mp4");
		player.play("avi", "movie.avi");
		
//		== Ouput: 
//		Playing MP3 file: song.mp3
//		Playing wav file: audio.wav
//		Playing mp4 file: video.mp4
//		Invalid media type: avi
	}
}
