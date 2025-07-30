package behavioral.templatemethod.documentprocessingsystem;

// Concrete Class: Processes PDF documents
public class PdfDocumentProcessor extends DocumentProcessor {
	@Override
	protected void openDocument() {
		System.out.println("Opening PDF document");
	}

	@Override
	protected void processContent() {
		System.out.println("Processing PDF content (e.g., extracting text)");
	}

	@Override
	protected void saveDocument() {
		System.out.println("Saving PDF document");
	}

	@Override
	protected void logProcessing() {
		System.out.println("Logging: PDF document processed successfully");
	}
}