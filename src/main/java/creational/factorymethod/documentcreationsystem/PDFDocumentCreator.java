package creational.factorymethod.documentcreationsystem;

// ConcreateCreator
public class PDFDocumentCreator extends DocumentCreator {

	@Override
	Document createDocument() {
		return new PDFDocument();
	}
}
