package behavioral.iterator.bookcollection;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create a book collection
		Library library = new Library(5);
		library.addBook(new Book("Design Patterns"));
		library.addBook(new Book("Clean Code"));
		library.addBook(new Book("Refactoring"));

		// Get an iterator
		BookIterator iterator = library.createIterator();

		// Traverse the collection
		System.out.println("Iterating over books:");
		while (iterator.hasNext()) {
			Book book = iterator.next();
			System.out.println(book);
		}

		// Demonstrate multiple iterators
		System.out.println("\nUsing a second iterator:");
		BookIterator iterator2 = library.createIterator();
		while (iterator2.hasNext()) {
			System.out.println(iterator2.next());
		}

//		== Output: 
//		Iterating over books:
//		Book: Design Patterns
//		Book: Clean Code
//		Book: Refactoring
//
//		Using a second iterator:
//		Book: Design Patterns
//		Book: Clean Code
//		Book: Refactoring
	}
}
