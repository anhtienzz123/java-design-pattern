package behavioral.visitor.documentprocessing;

import java.util.List;

public class ZMain {
	public static void main(String[] args) {
		System.out.println("=== Visitor Pattern: Document Processing System ===\n");
		
		Document document = createSampleDocument();
		
		System.out.println("Processing document: \"" + document.getTitle() + "\" by " + document.getAuthor());
		System.out.println("Document contains " + document.getElementCount() + " elements\n");
		
		System.out.println("1. Word Count Analysis:");
		WordCountVisitor wordCounter = new WordCountVisitor();
		document.accept(wordCounter);
		wordCounter.printSummary();
		System.out.println();
		
		System.out.println("2. Document Validation:");
		ValidationVisitor validator = new ValidationVisitor();
		document.accept(validator);
		validator.printValidationResults();
		System.out.println();
		
		System.out.println("3. Document Statistics:");
		StatisticsVisitor statistics = new StatisticsVisitor();
		document.accept(statistics);
		statistics.printStatistics();
		System.out.println();
		
		System.out.println("4. HTML Export:");
		HTMLExportVisitor htmlExporter = new HTMLExportVisitor();
		document.accept(htmlExporter);
		htmlExporter.printHTML();
		System.out.println();
		
		System.out.println("5. Demonstrating Visitor Pattern Benefits:");
		demonstratePatternBenefits(document);
	}
	
	private static Document createSampleDocument() {
		Document doc = new Document("Design Patterns Guide", "Software Engineer");
		
		doc.addElement(new Heading("Introduction", 1, true));
		doc.addElement(new Paragraph("Design patterns are reusable solutions to commonly occurring problems in software design. They represent best practices and provide a shared vocabulary for developers.", "left"));
		doc.addElement(new Paragraph("This guide covers the most important design patterns used in modern software development.", "left"));
		
		doc.addElement(new Heading("Behavioral Patterns", 2, true));
		doc.addElement(new Paragraph("Behavioral patterns focus on communication between objects and the assignment of responsibilities.", "left"));
		
		doc.addElement(new Heading("Visitor Pattern", 3, true));
		doc.addElement(new Paragraph("The Visitor pattern allows you to add new operations to existing object structures without modifying them.", "justify"));
		
		doc.addElement(new Table(
			List.of("Pattern", "Type", "Complexity", "Usage"),
			List.of(
				List.of("Visitor", "Behavioral", "High", "Operations on object structures"),
				List.of("Strategy", "Behavioral", "Medium", "Interchangeable algorithms"),
				List.of("Observer", "Behavioral", "Medium", "Event handling"),
				List.of("Command", "Behavioral", "Medium", "Encapsulate requests")
			),
			true
		));
		
		doc.addElement(new Image("visitor-pattern-diagram.png", "Visitor Pattern UML Diagram", 800, 600, "PNG"));
		
		doc.addElement(new Heading("Benefits", 3, true));
		doc.addElement(new Paragraph("Easy to add new operations without modifying existing classes.", "left"));
		doc.addElement(new Paragraph("Separates operations from the objects they operate on.", "left"));
		doc.addElement(new Paragraph("", "left"));
		
		doc.addElement(new Heading("Conclusion", 2, true));
		doc.addElement(new Paragraph("The Visitor pattern is particularly useful when you have a stable object structure but need to add new operations frequently. It provides excellent separation of concerns and follows the Open/Closed Principle.", "justify"));
		
		return doc;
	}
	
	private static void demonstratePatternBenefits(Document document) {
		System.out.println("=== Visitor Pattern Benefits Demo ===");
		System.out.println("The Visitor pattern allows us to:");
		System.out.println("1. Add new operations without modifying existing classes");
		System.out.println("2. Separate operations from the data structure");
		System.out.println("3. Collect related operations in one place");
		System.out.println();
		
		System.out.println("We created 4 different visitors that operate on the same document:");
		System.out.println("- WordCountVisitor: Counts words and elements");
		System.out.println("- ValidationVisitor: Validates document structure and content");
		System.out.println("- StatisticsVisitor: Gathers comprehensive statistics");
		System.out.println("- HTMLExportVisitor: Exports document to HTML format");
		System.out.println();
		
		System.out.println("Each visitor implements different operations without changing:");
		System.out.println("- Document class");
		System.out.println("- DocumentElement implementations (Paragraph, Heading, Table, Image)");
		System.out.println("- DocumentVisitor interface");
		System.out.println();
		
		System.out.println("This demonstrates the Open/Closed Principle:");
		System.out.println("✓ Open for extension (new visitors)");
		System.out.println("✓ Closed for modification (existing classes)");
		
		System.out.println("\nTo add a new operation (e.g., PDF export), we would:");
		System.out.println("1. Create a new PDFExportVisitor class");
		System.out.println("2. Implement the DocumentVisitor interface");
		System.out.println("3. No changes needed to existing classes!");
	}
}