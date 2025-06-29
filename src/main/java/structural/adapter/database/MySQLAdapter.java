package structural.adapter.database;

public class MySQLAdapter implements DatabaseExecutor {
	private MySQLDB mySqlDB;

	public MySQLAdapter(MySQLDB mySqlDB) {
		this.mySqlDB = mySqlDB;
	}

	@Override
	public void executeQuery(String query) {
		// Translate and adapt the query if needed
		String mySqlQuery = translateToMySQLQuery(query);
		mySqlDB.executeMySQLQuery(mySqlQuery);
	}

	private String translateToMySQLQuery(String query) {
		// In a real scenario, this would translate generic SQL to MySQL-specific SQL
		return query + " [Translated for MySQL]";
	}
}
