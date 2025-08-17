package behavioral.iterator.musicplaylist;

public interface SongIterator {
    boolean hasNext();
    Song next();
    void reset();
}