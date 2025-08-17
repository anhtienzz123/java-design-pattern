package behavioral.templatemethod.dataanalysissystem;

import java.util.*;

public class CSVDataAnalyzer extends DataAnalysisTemplate {
	
	@Override
	protected String loadData(String filePath) {
		System.out.println("Loading CSV data from: " + filePath);
		return "name,age,salary,department\n" +
			   "John Doe,30,50000,Engineering\n" +
			   "Jane Smith,25,45000,Marketing\n" +
			   "Bob Johnson,35,60000,Engineering\n" +
			   "Alice Brown,28,48000,HR\n" +
			   "Charlie Wilson,32,55000,Engineering";
	}
	
	@Override
	protected List<Map<String, Object>> parseData(String rawData) {
		System.out.println("Parsing CSV format...");
		List<Map<String, Object>> records = new ArrayList<>();
		String[] lines = rawData.split("\n");
		
		if (lines.length < 2) return records;
		
		String[] headers = lines[0].split(",");
		
		for (int i = 1; i < lines.length; i++) {
			String[] values = lines[i].split(",");
			Map<String, Object> record = new HashMap<>();
			
			for (int j = 0; j < headers.length && j < values.length; j++) {
				String value = values[j].trim();
				if (headers[j].equals("age")) {
					record.put(headers[j], Integer.parseInt(value));
				} else if (headers[j].equals("salary")) {
					record.put(headers[j], Double.parseDouble(value));
				} else {
					record.put(headers[j], value);
				}
			}
			records.add(record);
		}
		
		return records;
	}
	
	@Override
	protected Map<String, Object> performAnalysis(Map<String, Object> cleanedData) {
		System.out.println("Performing CSV-specific analysis...");
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> records = (List<Map<String, Object>>) cleanedData.get("records");
		
		double totalSalary = 0;
		int totalAge = 0;
		Map<String, Integer> departmentCount = new HashMap<>();
		
		for (Map<String, Object> record : records) {
			totalSalary += (Double) record.get("salary");
			totalAge += (Integer) record.get("age");
			
			String department = (String) record.get("department");
			departmentCount.put(department, departmentCount.getOrDefault(department, 0) + 1);
		}
		
		return Map.of(
			"averageSalary", totalSalary / records.size(),
			"averageAge", (double) totalAge / records.size(),
			"departmentDistribution", departmentCount,
			"totalEmployees", records.size(),
			"dataFormat", "CSV"
		);
	}
	
	@Override
	protected boolean shouldExportResults() {
		return true;
	}
	
	@Override
	protected void exportResults(Map<String, Object> results) {
		System.out.println("Exporting CSV analysis to spreadsheet format...");
		System.out.println("CSV Report exported successfully!");
	}
}