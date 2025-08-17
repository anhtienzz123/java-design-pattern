package behavioral.iterator.musicplaylist;

public interface PlaylistCollection {
    SongIterator createIterator();
    SongIterator createGenreIterator(String genre);
    SongIterator createShuffleIterator();
}