package creational.abstractfactory.databaseconnectivity;

public class ZMain {

	public static void main(String[] args) {
		String sql = "SELECT * FROM user";

		System.out.println("-- MySQL");
		DatabaseFactory mysqlFactory = new MySQLDatabaseFactory();
		DatabaseClient mysqlClient = new DatabaseClient(mysqlFactory);
		mysqlClient.executeQuery(sql);

		System.out.println("-- PostgreSQL");
		DatabaseFactory postgresqlFactory = new PostgreSQLDatabaseFactory();
		DatabaseClient postgresqlClient = new DatabaseClient(postgresqlFactory);
		postgresqlClient.executeQuery(sql);
	}
}
