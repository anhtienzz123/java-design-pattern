package behavioral.iterator.musicplaylist;

public class SequentialSongIterator implements SongIterator {
    private Playlist playlist;
    private int currentIndex;

    public SequentialSongIterator(Playlist playlist) {
        this.playlist = playlist;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < playlist.getSize();
    }

    @Override
    public Song next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more songs in playlist");
        }
        return playlist.getSong(currentIndex++);
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
}