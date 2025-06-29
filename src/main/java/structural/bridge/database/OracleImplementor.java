package structural.bridge.database;

public class OracleImplementor implements DatabaseImplementor {
	@Override
	public void connect() {
		System.out.println("Connecting to Oracle database...");
	}

	@Override
	public void executeQuery(String query) {
		System.out.println("Executing Oracle query: " + query);
	}

	@Override
	public void disconnect() {
		System.out.println("Disconnecting from Oracle database");
	}
}
