package behavioral.templatemethod.dataanalysissystem;

public class ZMain {
	public static void main(String[] args) {
		System.out.println("=== Template Method Pattern: Data Analysis System ===\n");
		
		System.out.println("1. CSV Employee Data Analysis:");
		DataAnalysisTemplate csvAnalyzer = new CSVDataAnalyzer();
		csvAnalyzer.analyzeData("employees.csv");
		
		System.out.println("2. JSON Product Inventory Analysis:");
		DataAnalysisTemplate jsonAnalyzer = new JSONDataAnalyzer();
		jsonAnalyzer.analyzeData("products.json");
		
		System.out.println("3. XML Customer Data Analysis:");
		DataAnalysisTemplate xmlAnalyzer = new XMLDataAnalyzer();
		xmlAnalyzer.analyzeData("customers.xml");
		
		System.out.println("4. Demonstrating Template Method Invariance:");
		demonstrateTemplateMethodStructure();
		
		System.out.println("5. Error Handling Demo:");
		demonstrateErrorHandling();
	}
	
	private static void demonstrateTemplateMethodStructure() {
		System.out.println("\n=== Template Method Structure Demo ===");
		System.out.println("All analyzers follow the same algorithm structure:");
		System.out.println("1. Validate Input");
		System.out.println("2. Load Data");
		System.out.println("3. Parse Data (format-specific)");
		System.out.println("4. Clean Data (with format-specific rules)");
		System.out.println("5. Perform Analysis (format-specific)");
		System.out.println("6. Generate Report");
		System.out.println("7. Export Results (if needed)");
		System.out.println("This structure is enforced by the template method and cannot be changed.\n");
	}
	
	private static void demonstrateErrorHandling() {
		System.out.println("=== Error Handling Demo ===");
		DataAnalysisTemplate analyzer = new CSVDataAnalyzer();
		
		try {
			analyzer.analyzeData(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Caught expected error: " + e.getMessage());
		}
		
		try {
			analyzer.analyzeData("");
		} catch (IllegalArgumentException e) {
			System.out.println("Caught expected error: " + e.getMessage());
		}
		
		System.out.println("Error handling completed successfully.\n");
	}
}