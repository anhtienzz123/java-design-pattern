package creational.factorymethod.documentcreationsystem;

// ConcreateCreator
public class WordDocumentCreator extends DocumentCreator {

	@Override
	Document createDocument() {
		return new WordDocument();
	}
}
