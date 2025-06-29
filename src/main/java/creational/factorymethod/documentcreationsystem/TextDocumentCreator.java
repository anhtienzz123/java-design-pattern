package creational.factorymethod.documentcreationsystem;

// ConcreateCreator
public class TextDocumentCreator extends DocumentCreator {

	@Override
	Document createDocument() {
		return new TextDocument();
	}
}
