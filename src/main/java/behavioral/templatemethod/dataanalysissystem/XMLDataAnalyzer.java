package behavioral.templatemethod.dataanalysissystem;

import java.util.*;

public class XMLDataAnalyzer extends DataAnalysisTemplate {
	
	@Override
	protected String loadData(String filePath) {
		System.out.println("Loading XML data from: " + filePath);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			   "<customers>\n" +
			   "  <customer id=\"C001\">\n" +
			   "    <name>Alice Johnson</name>\n" +
			   "    <email>alice@example.com</email>\n" +
			   "    <orders>5</orders>\n" +
			   "    <totalSpent>1250.50</totalSpent>\n" +
			   "    <region>North</region>\n" +
			   "  </customer>\n" +
			   "  <customer id=\"C002\">\n" +
			   "    <name>Bob Smith</name>\n" +
			   "    <email>bob@example.com</email>\n" +
			   "    <orders>3</orders>\n" +
			   "    <totalSpent>780.25</totalSpent>\n" +
			   "    <region>South</region>\n" +
			   "  </customer>\n" +
			   "  <customer id=\"C003\">\n" +
			   "    <name>Carol Davis</name>\n" +
			   "    <email>carol@example.com</email>\n" +
			   "    <orders>8</orders>\n" +
			   "    <totalSpent>2100.75</totalSpent>\n" +
			   "    <region>East</region>\n" +
			   "  </customer>\n" +
			   "  <customer id=\"C004\">\n" +
			   "    <name>David Wilson</name>\n" +
			   "    <email>david@example.com</email>\n" +
			   "    <orders>2</orders>\n" +
			   "    <totalSpent>450.00</totalSpent>\n" +
			   "    <region>West</region>\n" +
			   "  </customer>\n" +
			   "</customers>";
	}
	
	@Override
	protected List<Map<String, Object>> parseData(String rawData) {
		System.out.println("Parsing XML format...");
		List<Map<String, Object>> records = new ArrayList<>();
		
		String[] customerBlocks = rawData.split("<customer");
		
		for (int i = 1; i < customerBlocks.length; i++) {
			String block = customerBlocks[i];
			Map<String, Object> record = new HashMap<>();
			
			String id = extractValue(block, "id=\"", "\"");
			record.put("customerId", id);
			
			record.put("name", extractXmlTag(block, "name"));
			record.put("email", extractXmlTag(block, "email"));
			record.put("orders", Integer.parseInt(extractXmlTag(block, "orders")));
			record.put("totalSpent", Double.parseDouble(extractXmlTag(block, "totalSpent")));
			record.put("region", extractXmlTag(block, "region"));
			
			records.add(record);
		}
		
		return records;
	}
	
	private String extractValue(String text, String startDelim, String endDelim) {
		int start = text.indexOf(startDelim);
		if (start == -1) return "";
		start += startDelim.length();
		int end = text.indexOf(endDelim, start);
		if (end == -1) return "";
		return text.substring(start, end);
	}
	
	private String extractXmlTag(String text, String tagName) {
		String openTag = "<" + tagName + ">";
		String closeTag = "</" + tagName + ">";
		int start = text.indexOf(openTag);
		if (start == -1) return "";
		start += openTag.length();
		int end = text.indexOf(closeTag, start);
		if (end == -1) return "";
		return text.substring(start, end).trim();
	}
	
	@Override
	protected Map<String, Object> cleanData(List<Map<String, Object>> data) {
		System.out.println("Performing XML-specific data cleaning and validation...");
		
		data.removeIf(record -> {
			String email = (String) record.get("email");
			Integer orders = (Integer) record.get("orders");
			Double totalSpent = (Double) record.get("totalSpent");
			
			return email == null || !email.contains("@") || 
				   orders == null || orders < 0 || 
				   totalSpent == null || totalSpent < 0;
		});
		
		return Map.of(
			"records", data,
			"totalRecords", data.size(),
			"cleanedAt", System.currentTimeMillis(),
			"xmlValidation", "Email format and positive values validated"
		);
	}
	
	@Override
	protected Map<String, Object> performAnalysis(Map<String, Object> cleanedData) {
		System.out.println("Performing XML-specific customer analysis...");
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> records = (List<Map<String, Object>>) cleanedData.get("records");
		
		double totalRevenue = 0;
		int totalOrders = 0;
		Map<String, Integer> regionCount = new HashMap<>();
		Map<String, Double> regionRevenue = new HashMap<>();
		
		String topCustomer = "";
		double maxSpent = 0;
		
		for (Map<String, Object> record : records) {
			double spent = (Double) record.get("totalSpent");
			int orders = (Integer) record.get("orders");
			String region = (String) record.get("region");
			String name = (String) record.get("name");
			
			totalRevenue += spent;
			totalOrders += orders;
			
			regionCount.put(region, regionCount.getOrDefault(region, 0) + 1);
			regionRevenue.put(region, regionRevenue.getOrDefault(region, 0.0) + spent);
			
			if (spent > maxSpent) {
				maxSpent = spent;
				topCustomer = name;
			}
		}
		
		return Map.of(
			"totalRevenue", totalRevenue,
			"totalOrders", totalOrders,
			"averageOrderValue", totalRevenue / totalOrders,
			"averageCustomerValue", totalRevenue / records.size(),
			"regionDistribution", regionCount,
			"regionRevenue", regionRevenue,
			"topCustomer", topCustomer,
			"topCustomerSpent", maxSpent,
			"dataFormat", "XML"
		);
	}
	
	@Override
	protected void generateReport(Map<String, Object> results) {
		System.out.println("Generating XML-specific customer analytics report...");
		super.generateReport(results);
		System.out.println("XML customer analysis completed with regional breakdown.");
	}
	
	@Override
	protected boolean shouldExportResults() {
		return true;
	}
	
	@Override
	protected void exportResults(Map<String, Object> results) {
		System.out.println("Exporting XML customer analysis to CRM system...");
		System.out.println("Customer analytics exported successfully!");
	}
}