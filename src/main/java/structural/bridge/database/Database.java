package structural.bridge.database;

// Abstraction
public abstract class Database {
	protected DatabaseImplementor implementor;

	public Database(DatabaseImplementor implementor) {
		this.implementor = implementor;
	}

	public void setImplementor(DatabaseImplementor implementor) {
		this.implementor = implementor;
	}

	// Common operations for all databases
	public void performOperation(String query) {
		implementor.connect();
		implementor.executeQuery(query);
		implementor.disconnect();
	}

	// Abstract operations to be defined by refined abstractions
	public abstract void logOperation(String operation);
}
