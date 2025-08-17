package behavioral.iterator.musicplaylist;

public class ZMain {
    public static void main(String[] args) {
        Playlist myPlaylist = new Playlist("My Awesome Mix");
        
        myPlaylist.addSong(new Song("Bohemian Rhapsody", "Queen", "Rock", 355));
        myPlaylist.addSong(new Song("Billie Jean", "Michael Jackson", "Pop", 294));
        myPlaylist.addSong(new Song("Stairway to Heaven", "Led Zeppelin", "Rock", 482));
        myPlaylist.addSong(new Song("Shape of You", "Ed Sheeran", "Pop", 263));
        myPlaylist.addSong(new Song("Hotel California", "Eagles", "Rock", 391));
        myPlaylist.addSong(new Song("Blinding Lights", "The Weeknd", "Pop", 200));
        myPlaylist.addSong(new Song("Sweet Child O' Mine", "Guns N' Roses", "Rock", 356));
        
        System.out.println(myPlaylist);
        System.out.println("=" + "=".repeat(60));
        
        System.out.println("\n1. Sequential playback:");
        SongIterator sequentialIterator = myPlaylist.createIterator();
        while (sequentialIterator.hasNext()) {
            System.out.println("  ♪ " + sequentialIterator.next());
        }
        
        System.out.println("\n2. Rock songs only:");
        SongIterator rockIterator = myPlaylist.createGenreIterator("Rock");
        while (rockIterator.hasNext()) {
            System.out.println("  🎸 " + rockIterator.next());
        }
        
        System.out.println("\n3. Pop songs only:");
        SongIterator popIterator = myPlaylist.createGenreIterator("Pop");
        while (popIterator.hasNext()) {
            System.out.println("  🎤 " + popIterator.next());
        }
        
        System.out.println("\n4. Shuffled playback:");
        SongIterator shuffleIterator = myPlaylist.createShuffleIterator();
        while (shuffleIterator.hasNext()) {
            System.out.println("  🔀 " + shuffleIterator.next());
        }
        
        System.out.println("\n5. Demonstrating iterator reset (first 3 rock songs):");
        rockIterator.reset();
        int count = 0;
        while (rockIterator.hasNext() && count < 3) {
            System.out.println("  🎸 " + rockIterator.next());
            count++;
        }
        
        System.out.println("\n6. Multiple concurrent iterators:");
        SongIterator iter1 = myPlaylist.createIterator();
        SongIterator iter2 = myPlaylist.createGenreIterator("Pop");
        
        System.out.println("  Next sequential song: " + (iter1.hasNext() ? iter1.next() : "None"));
        System.out.println("  Next pop song: " + (iter2.hasNext() ? iter2.next() : "None"));
        System.out.println("  Next sequential song: " + (iter1.hasNext() ? iter1.next() : "None"));
        System.out.println("  Next pop song: " + (iter2.hasNext() ? iter2.next() : "None"));
    }
}