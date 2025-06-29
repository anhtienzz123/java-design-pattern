package behavioral.iterator.bookcollection;

// Aggregate: Defines the interface for creating an iterator
public interface BookCollection {
	BookIterator createIterator(); // Return an iterator for the collection
}
