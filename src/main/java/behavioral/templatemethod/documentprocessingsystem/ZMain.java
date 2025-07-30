package behavioral.templatemethod.documentprocessingsystem;

public class ZMain {
	public static void main(String[] args) {
		// Process a PDF document
		System.out.println("Processing PDF Document:");
		DocumentProcessor pdfProcessor = new PdfDocumentProcessor();
		pdfProcessor.processDocument();

		// Process a Word document
		System.out.println("\nProcessing Word Document:");
		DocumentProcessor wordProcessor = new WordDocumentProcessor();
		wordProcessor.processDocument();

//		== Output:
//		Processing PDF Document:
//		Opening PDF document
//		Processing PDF content (e.g., extracting text)
//		Saving PDF document
//		Logging: PDF document processed successfully
//
//		Processing Word Document:
//		Opening Word document
//		Processing Word content (e.g., formatting text)
//		Saving Word document
	}
}
