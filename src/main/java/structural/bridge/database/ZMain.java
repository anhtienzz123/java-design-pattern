package structural.bridge.database;

//Client code
public class ZMain {
	public static void main(String[] args) {
		// Using MySQL for business database
		DatabaseImplementor mySqlImplementor = new MySQLImplementor();
		Database businessDb = new BusinessDatabase("Sales", mySqlImplementor);

		businessDb.logOperation("Starting sales operations");
		((BusinessDatabase) businessDb).generateReport();

		System.out.println("\n------------------------\n");

		// Using Oracle for customer database
		DatabaseImplementor oracleImplementor = new OracleImplementor();
		Database customerDb = new CustomerDatabase("Premium", oracleImplementor);

		customerDb.logOperation("Starting customer operations");
		((CustomerDatabase) customerDb).addCustomer("'John Doe', 'john@example.com'");

		System.out.println("\n------------------------\n");

		// Switching implementations
		System.out.println("Switching database implementation for business database:");
		businessDb.setImplementor(oracleImplementor);
		((BusinessDatabase) businessDb).generateReport();
	}
}
