package behavioral.iterator.bookcollection;

// Concrete Aggregate: Stores books and provides an iterator
public class Library implements BookCollection {
	private Book[] books;
	private int size;

	public Library(int capacity) {
		books = new Book[capacity];
		size = 0;
	}

	public void addBook(Book book) {
		if (size < books.length) {
			books[size++] = book;
		}
	}

	@Override
	public BookIterator createIterator() {
		return new LibraryIterator(this);
	}

	// Package-private method to access books (used by iterator)
	Book getBook(int index) {
		return books[index];
	}

	int getSize() {
		return size;
	}
}
