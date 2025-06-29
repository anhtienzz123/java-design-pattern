package behavioral.iterator.bookcollection;

// Concrete Iterator: Traverses the book collection
public class LibraryIterator implements BookIterator {
	private Library library;
	private int currentIndex;

	public LibraryIterator(Library library) {
		this.library = library;
		this.currentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return currentIndex < library.getSize();
	}

	@Override
	public Book next() {
		if (!hasNext()) {
			throw new IndexOutOfBoundsException("No more books to iterate");
		}
		return library.getBook(currentIndex++);
	}
}