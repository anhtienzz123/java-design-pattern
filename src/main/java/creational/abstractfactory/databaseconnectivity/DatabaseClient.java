package creational.abstractfactory.databaseconnectivity;

// Client
public class DatabaseClient {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public DatabaseClient(DatabaseFactory databaseFactory) {
		this.connection = databaseFactory.createConnection();
		this.statement = databaseFactory.createStatement();
		this.resultSet = databaseFactory.createResultSet();
	}

	public void executeQuery(String sql) {
		this.connection.open();
		statement.prepare(sql);
		statement.execute();

		if (resultSet.hasNext()) {
			System.out.println("id: " + resultSet.getInt("id"));
			System.out.println("name: " + resultSet.getString("name"));
		}

		this.connection.close();
	}
}
