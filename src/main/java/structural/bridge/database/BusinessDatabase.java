package structural.bridge.database;

// Refined Abstractions
public class BusinessDatabase extends Database {
	private String department;

	public BusinessDatabase(String department, DatabaseImplementor implementor) {
		super(implementor);
		this.department = department;
	}

	@Override
	public void logOperation(String operation) {
		System.out.println("Logging operation for " + department + " department: " + operation);
	}

	public void generateReport() {
		String query = "SELECT * FROM " + department + "_data";
		logOperation("Generating report");
		performOperation(query);
		System.out.println("Report generated for " + department + " department");
	}
}
