package creational.factorymethod.documentcreationsystem;

public class ZMain {

	public static void main(String[] args) {
		DocumentCreator pdfDocumentCreator = new PDFDocumentCreator();
		pdfDocumentCreator.editDocument();

		DocumentCreator wordDocumentCreator = new WordDocumentCreator();
		wordDocumentCreator.editDocument();

		DocumentCreator textDocumentCreator = new TextDocumentCreator();
		textDocumentCreator.editDocument();
	}
}
