# Iterator Pattern - Music Playlist System

This implementation demonstrates the Iterator pattern using a music playlist system that supports multiple iteration strategies.

## Overview

The Iterator pattern provides a way to access elements of a collection sequentially without exposing its underlying representation. This music playlist example showcases different types of iterators that can traverse the same collection in various ways.

## Components

### Core Elements
- **Song**: Represents a music track with title, artist, genre, and duration
- **SongIterator**: Interface defining the contract for iterating over songs
- **PlaylistCollection**: Interface for creating different types of iterators
- **Playlist**: Concrete collection that stores songs and creates iterators

### Iterator Implementations

1. **SequentialSongIterator**: Standard sequential iteration through all songs
2. **GenreFilterIterator**: Filters songs by genre during iteration
3. **ShuffleSongIterator**: Randomizes the order of songs for each iteration

## Key Features

### Multiple Iteration Strategies
- **Sequential**: Play songs in order they were added
- **Genre Filtering**: Only iterate through songs of a specific genre
- **Shuffle**: Random order playback with reset capability

### Advanced Functionality
- **Reset Capability**: All iterators support resetting to start position
- **Concurrent Iterators**: Multiple iterators can operate independently
- **Type Safety**: Strong typing ensures only valid songs are returned
- **Exception Handling**: Proper bounds checking with meaningful error messages

## Design Benefits

1. **Separation of Concerns**: Iteration logic is separated from the collection structure
2. **Multiple Algorithms**: Different traversal strategies without changing the collection
3. **Uniform Interface**: All iterators share the same interface for consistency
4. **Independence**: Multiple iterators can traverse the same collection simultaneously
5. **Extensibility**: Easy to add new iteration strategies without modifying existing code

## Usage Example

```java
Playlist playlist = new Playlist("My Mix");
playlist.addSong(new Song("Title", "Artist", "Rock", 180));

// Sequential iteration
SongIterator sequential = playlist.createIterator();
while (sequential.hasNext()) {
    System.out.println(sequential.next());
}

// Genre-filtered iteration
SongIterator rockOnly = playlist.createGenreIterator("Rock");
while (rockOnly.hasNext()) {
    System.out.println(rockOnly.next());
}

// Shuffled iteration
SongIterator shuffled = playlist.createShuffleIterator();
while (shuffled.hasNext()) {
    System.out.println(shuffled.next());
}
```

## Real-World Applications

- **Music Players**: Spotify, Apple Music playlist management
- **Media Libraries**: Photo galleries with different viewing modes
- **E-commerce**: Product catalogs with filtering capabilities
- **Social Media**: News feeds with various sorting algorithms
- **File Systems**: Directory traversal with different criteria

## Pattern Advantages in This Context

1. **Flexibility**: Switch between different playback modes seamlessly
2. **Maintainability**: Adding new iteration types doesn't affect existing code
3. **Reusability**: Iterator implementations can be used across different playlist types
4. **Performance**: Lazy evaluation - only processes songs as needed
5. **Memory Efficiency**: No need to duplicate the collection for different traversals

This implementation demonstrates how the Iterator pattern enables sophisticated collection traversal while maintaining clean, extensible code architecture.