package creational.simplefactory.documentcreationsystem;

public class ZMain {

	public static void main(String[] args) {
		Document pdfDocument = DocumentFactory.createDocument(DocumentType.PDF);
		pdfDocument.open();
		pdfDocument.save();
		pdfDocument.close();

		Document wordDocument = DocumentFactory.createDocument(DocumentType.WORD);
		wordDocument.open();
		wordDocument.save();
		wordDocument.close();

		Document textDocument = DocumentFactory.createDocument(DocumentType.TEXT);
		textDocument.open();
		textDocument.save();
		textDocument.close();

//		== Output: 
//		Opening PDF document
//		Saving PDF document
//		Closing PDF document
//		Opening Word document
//		Saving Word document
//		Closing Word document
//		Opening Text document
//		Saving Text document
//		Closing Text document
	}
}
