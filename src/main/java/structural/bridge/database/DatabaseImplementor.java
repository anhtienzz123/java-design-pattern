package structural.bridge.database;

// Implementor interface
public interface DatabaseImplementor {
	void connect();

	void executeQuery(String query);

	void disconnect();
}
