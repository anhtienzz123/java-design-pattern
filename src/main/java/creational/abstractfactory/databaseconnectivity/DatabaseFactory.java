package creational.abstractfactory.databaseconnectivity;

// AbstractFactory
public interface DatabaseFactory {

	Connection createConnection();

	Statement createStatement();

	ResultSet createResultSet();
}
