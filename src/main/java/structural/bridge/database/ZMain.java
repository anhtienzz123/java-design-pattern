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
		
//		== Output: 
//		Logging operation for Sales department: Starting sales operations
//		Logging operation for Sales department: Generating report
//		Connecting to MySQL database...
//		Executing MySQL query: SELECT * FROM Sales_data
//		Disconnecting from MySQL database
//		Report generated for Sales department
//
//		------------------------
//
//		Logging customer operation for Premium: Starting customer operations
//		Logging customer operation for Premium: Adding customer
//		Connecting to Oracle database...
//		Executing Oracle query: INSERT INTO Premium_customers VALUES ('John Doe', 'john@example.com')
//		Disconnecting from Oracle database
//		Customer added to Premium database
//
//		------------------------
//
//		Switching database implementation for business database:
//		Logging operation for Sales department: Generating report
//		Connecting to Oracle database...
//		Executing Oracle query: SELECT * FROM Sales_data
//		Disconnecting from Oracle database
//		Report generated for Sales department
	}
}
