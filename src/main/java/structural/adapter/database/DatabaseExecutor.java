package structural.adapter.database;

// Unified interface that client will use
public interface DatabaseExecutor {
	void executeQuery(String query);
}
