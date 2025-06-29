package structural.bridge.database;

public class CustomerDatabase extends Database {
	private String customerType;

	public CustomerDatabase(String customerType, DatabaseImplementor implementor) {
		super(implementor);
		this.customerType = customerType;
	}

	@Override
	public void logOperation(String operation) {
		System.out.println("Logging customer operation for " + customerType + ": " + operation);
	}

	public void addCustomer(String customerInfo) {
		String query = "INSERT INTO " + customerType + "_customers VALUES (" + customerInfo + ")";
		logOperation("Adding customer");
		performOperation(query);
		System.out.println("Customer added to " + customerType + " database");
	}
}
