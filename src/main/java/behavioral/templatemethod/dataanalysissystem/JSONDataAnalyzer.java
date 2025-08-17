package behavioral.templatemethod.dataanalysissystem;

import java.util.*;

public class JSONDataAnalyzer extends DataAnalysisTemplate {
	
	@Override
	protected String loadData(String filePath) {
		System.out.println("Loading JSON data from: " + filePath);
		return "[\n" +
			   "  {\"productId\": \"P001\", \"name\": \"Laptop\", \"price\": 999.99, \"category\": \"Electronics\", \"stock\": 50},\n" +
			   "  {\"productId\": \"P002\", \"name\": \"Mouse\", \"price\": 25.99, \"category\": \"Electronics\", \"stock\": 200},\n" +
			   "  {\"productId\": \"P003\", \"name\": \"Desk\", \"price\": 299.99, \"category\": \"Furniture\", \"stock\": 15},\n" +
			   "  {\"productId\": \"P004\", \"name\": \"Chair\", \"price\": 149.99, \"category\": \"Furniture\", \"stock\": 30},\n" +
			   "  {\"productId\": \"P005\", \"name\": \"Monitor\", \"price\": 399.99, \"category\": \"Electronics\", \"stock\": 75}\n" +
			   "]";
	}
	
	@Override
	protected List<Map<String, Object>> parseData(String rawData) {
		System.out.println("Parsing JSON format...");
		List<Map<String, Object>> records = new ArrayList<>();
		
		String cleanJson = rawData.replaceAll("[\\[\\]\\n\\s]", "");
		String[] objects = cleanJson.split("\\},\\{");
		
		for (String obj : objects) {
			obj = obj.replaceAll("[{}]", "");
			String[] pairs = obj.split(",");
			Map<String, Object> record = new HashMap<>();
			
			for (String pair : pairs) {
				String[] keyValue = pair.split(":");
				if (keyValue.length == 2) {
					String key = keyValue[0].replaceAll("\"", "").trim();
					String value = keyValue[1].replaceAll("\"", "").trim();
					
					if (key.equals("price")) {
						record.put(key, Double.parseDouble(value));
					} else if (key.equals("stock")) {
						record.put(key, Integer.parseInt(value));
					} else {
						record.put(key, value);
					}
				}
			}
			if (!record.isEmpty()) {
				records.add(record);
			}
		}
		
		return records;
	}
	
	@Override
	protected Map<String, Object> cleanData(List<Map<String, Object>> data) {
		System.out.println("Performing JSON-specific data cleaning...");
		
		data.removeIf(record -> {
			Double price = (Double) record.get("price");
			Integer stock = (Integer) record.get("stock");
			return price == null || price <= 0 || stock == null || stock < 0;
		});
		
		return Map.of(
			"records", data,
			"totalRecords", data.size(),
			"cleanedAt", System.currentTimeMillis(),
			"validationRules", "Removed items with invalid price or negative stock"
		);
	}
	
	@Override
	protected Map<String, Object> performAnalysis(Map<String, Object> cleanedData) {
		System.out.println("Performing JSON-specific analysis...");
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> records = (List<Map<String, Object>>) cleanedData.get("records");
		
		double totalValue = 0;
		int totalStock = 0;
		Map<String, Integer> categoryCount = new HashMap<>();
		Map<String, Double> categoryValue = new HashMap<>();
		
		for (Map<String, Object> record : records) {
			double price = (Double) record.get("price");
			int stock = (Integer) record.get("stock");
			String category = (String) record.get("category");
			
			double itemValue = price * stock;
			totalValue += itemValue;
			totalStock += stock;
			
			categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
			categoryValue.put(category, categoryValue.getOrDefault(category, 0.0) + itemValue);
		}
		
		return Map.of(
			"totalInventoryValue", totalValue,
			"totalStockItems", totalStock,
			"categoryDistribution", categoryCount,
			"categoryValues", categoryValue,
			"averageItemValue", totalValue / records.size(),
			"dataFormat", "JSON"
		);
	}
	
	@Override
	protected void generateReport(Map<String, Object> results) {
		System.out.println("Generating JSON-specific inventory report...");
		super.generateReport(results);
		System.out.println("JSON inventory analysis completed with product categorization.");
	}
}