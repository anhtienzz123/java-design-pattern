package structural.bridge.database;

// Concrete Implementors
public class MySQLImplementor implements DatabaseImplementor {
	@Override
	public void connect() {
		System.out.println("Connecting to MySQL database...");
	}

	@Override
	public void executeQuery(String query) {
		System.out.println("Executing MySQL query: " + query);
	}

	@Override
	public void disconnect() {
		System.out.println("Disconnecting from MySQL database");
	}
}
