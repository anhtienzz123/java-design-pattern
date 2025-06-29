package creational.abstractfactory.databaseconnectivity;

// ConcreteFactory
public class MySQLDatabaseFactory implements DatabaseFactory {

	@Override
	public Connection createConnection() {
		return new MySQLConnection();
	}

	@Override
	public Statement createStatement() {
		return new MySQLStatement();
	}

	@Override
	public ResultSet createResultSet() {
		return new MySQLResultSet();
	}
}
