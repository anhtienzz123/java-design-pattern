package creational.factorymethod.documentcreationsystem;

// Creator
public abstract class DocumentCreator {

	// Factory method
	abstract Document createDocument();

	// Common document handling operations
	public void editDocument() {
		Document document = createDocument();
		document.open();
		System.out.println("Editing document...");
		document.save();
		document.close();
	}
}
