package behavioral.iterator.musicplaylist;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements PlaylistCollection {
    private String name;
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return songs.size();
    }

    public Song getSong(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    @Override
    public SongIterator createIterator() {
        return new SequentialSongIterator(this);
    }

    @Override
    public SongIterator createGenreIterator(String genre) {
        return new GenreFilterIterator(this, genre);
    }

    @Override
    public SongIterator createShuffleIterator() {
        return new ShuffleSongIterator(this);
    }

    @Override
    public String toString() {
        return String.format("Playlist: %s (%d songs)", name, songs.size());
    }
}