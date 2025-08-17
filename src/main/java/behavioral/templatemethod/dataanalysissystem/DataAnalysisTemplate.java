package behavioral.templatemethod.dataanalysissystem;

import java.util.List;
import java.util.Map;

public abstract class DataAnalysisTemplate {
	
	public final void analyzeData(String filePath) {
		System.out.println("=== Starting Data Analysis ===");
		
		validateInput(filePath);
		String rawData = loadData(filePath);
		List<Map<String, Object>> parsedData = parseData(rawData);
		Map<String, Object> cleanedData = cleanData(parsedData);
		Map<String, Object> results = performAnalysis(cleanedData);
		generateReport(results);
		
		if (shouldExportResults()) {
			exportResults(results);
		}
		
		System.out.println("=== Analysis Complete ===\n");
	}
	
	protected void validateInput(String filePath) {
		if (filePath == null || filePath.trim().isEmpty()) {
			throw new IllegalArgumentException("File path cannot be null or empty");
		}
		System.out.println("Input validated: " + filePath);
	}
	
	protected abstract String loadData(String filePath);
	
	protected abstract List<Map<String, Object>> parseData(String rawData);
	
	protected Map<String, Object> cleanData(List<Map<String, Object>> data) {
		System.out.println("Performing basic data cleaning...");
		return Map.of(
			"records", data,
			"totalRecords", data.size(),
			"cleanedAt", System.currentTimeMillis()
		);
	}
	
	protected abstract Map<String, Object> performAnalysis(Map<String, Object> cleanedData);
	
	protected void generateReport(Map<String, Object> results) {
		System.out.println("Generating standard report...");
		System.out.println("Analysis Results: " + results);
	}
	
	protected boolean shouldExportResults() {
		return false;
	}
	
	protected void exportResults(Map<String, Object> results) {
		System.out.println("Exporting results to default format...");
	}
}