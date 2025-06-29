package behavioral.documentprocessingsystem;

// Abstract Class: Defines the template method and algorithm steps
public abstract class DocumentProcessor {
	// Template method: Defines the algorithm structure
	public final void processDocument() {
		openDocument();
		processContent();
		saveDocument();
		logProcessing(); // Hook method
	}

	// Abstract methods: Must be implemented by subclasses
	protected abstract void openDocument();

	protected abstract void processContent();

	protected abstract void saveDocument();

	// Hook method: Optional, with default implementation
	protected void logProcessing() {
		// Default: Do nothing
	}
}
