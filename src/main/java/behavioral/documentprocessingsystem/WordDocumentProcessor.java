package behavioral.documentprocessingsystem;

// Concrete Class: Processes Word documents
public class WordDocumentProcessor extends DocumentProcessor {
	@Override
	protected void openDocument() {
		System.out.println("Opening Word document");
	}

	@Override
	protected void processContent() {
		System.out.println("Processing Word content (e.g., formatting text)");
	}

	@Override
	protected void saveDocument() {
		System.out.println("Saving Word document");
	}
	// No override for logProcessing; uses default (no logging)
}
