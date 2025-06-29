package creational.factorymethod.documentcreationsystem;

public class ZMain {

	public static void main(String[] args) {
		DocumentCreator pdfDocumentCreator = new PDFDocumentCreator();
		pdfDocumentCreator.editDocument();

		DocumentCreator wordDocumentCreator = new WordDocumentCreator();
		wordDocumentCreator.editDocument();

		DocumentCreator textDocumentCreator = new TextDocumentCreator();
		textDocumentCreator.editDocument();

//		== Output: 
//		Opening PDF document
//		Editing document...
//		Saving PDF document
//		Closing PDF document
//		Opening Word document
//		Editing document...
//		Saving Word document
//		Closing Word document
//		Opening Text document
//		Editing document...
//		Saving Text document
//		Closing Text document
	}
}
