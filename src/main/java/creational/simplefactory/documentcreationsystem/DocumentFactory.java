package creational.simplefactory.documentcreationsystem;

// SimpleFactory
public class DocumentFactory {

	public static Document createDocument(DocumentType documentType) {
		return switch (documentType) {
		case DocumentType.PDF -> new PDFDocument();
		case DocumentType.WORD -> new WordDocument();
		case DocumentType.TEXT -> new TextDocument();
		};
	}
}
