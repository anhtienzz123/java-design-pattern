package behavioral.iterator.musicplaylist;

public class Song {
    private String title;
    private String artist;
    private String genre;
    private int durationSeconds;

    public Song(String title, String artist, String genre, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public String getFormattedDuration() {
        int minutes = durationSeconds / 60;
        int seconds = durationSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s [%s] (%s)", title, artist, genre, getFormattedDuration());
    }
}