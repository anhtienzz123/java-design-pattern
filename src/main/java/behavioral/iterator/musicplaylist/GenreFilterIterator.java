package behavioral.iterator.musicplaylist;

public class GenreFilterIterator implements SongIterator {
    private Playlist playlist;
    private String targetGenre;
    private int currentIndex;

    public GenreFilterIterator(Playlist playlist, String targetGenre) {
        this.playlist = playlist;
        this.targetGenre = targetGenre;
        this.currentIndex = 0;
        findNextMatchingSong();
    }

    @Override
    public boolean hasNext() {
        return currentIndex < playlist.getSize();
    }

    @Override
    public Song next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more songs matching genre: " + targetGenre);
        }
        
        Song song = playlist.getSong(currentIndex);
        currentIndex++;
        findNextMatchingSong();
        return song;
    }

    @Override
    public void reset() {
        currentIndex = 0;
        findNextMatchingSong();
    }

    private void findNextMatchingSong() {
        while (currentIndex < playlist.getSize()) {
            Song song = playlist.getSong(currentIndex);
            if (song != null && song.getGenre().equalsIgnoreCase(targetGenre)) {
                break;
            }
            currentIndex++;
        }
    }
}