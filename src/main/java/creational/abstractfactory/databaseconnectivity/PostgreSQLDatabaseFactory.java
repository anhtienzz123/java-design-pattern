package creational.abstractfactory.databaseconnectivity;

// ConcreteFactory
public class PostgreSQLDatabaseFactory implements DatabaseFactory {

	@Override
	public Connection createConnection() {
		return new PostgreSQLConnection();
	}

	@Override
	public Statement createStatement() {
		return new PostgreSQLStatement();
	}

	@Override
	public ResultSet createResultSet() {
		return new PostgreSQLResultSet();
	}
}
