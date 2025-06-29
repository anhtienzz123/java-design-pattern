package behavioral.iterator.bookcollection;

// Iterator: Defines the interface for traversing books
public interface BookIterator {
	boolean hasNext(); // Check if more books exist

	Book next(); // Get the next book
}
