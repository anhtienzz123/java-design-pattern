package behavioral.iterator.musicplaylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleSongIterator implements SongIterator {
    private List<Song> shuffledSongs;
    private int currentIndex;

    public ShuffleSongIterator(Playlist playlist) {
        this.shuffledSongs = new ArrayList<>(playlist.getSongs());
        Collections.shuffle(this.shuffledSongs);
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < shuffledSongs.size();
    }

    @Override
    public Song next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more songs in shuffled playlist");
        }
        return shuffledSongs.get(currentIndex++);
    }

    @Override
    public void reset() {
        Collections.shuffle(shuffledSongs);
        currentIndex = 0;
    }
}