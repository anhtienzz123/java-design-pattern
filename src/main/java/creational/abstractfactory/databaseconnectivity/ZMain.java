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

//		== Output: 
//		-- MySQL
//		Opening MySQL connection
//		Preparing MySQL statement: SELECT * FROM user
//		Executing MySQL statement
//		Moving to next record in MySQL result set
//		MySQL getInt
//		id: 1
//		MySQL getString
//		name: name
//		Closing MySQL connection
//		-- PostgreSQL
//		Opening PostgreSQL connection
//		Preparing PostgreSQL statement: SELECT * FROM user
//		Executing PostgreSQL statement
//		Moving to next record in PostgreSQL result set
//		PostgreSQL getInt
//		id: 1
//		PostgreSQL getString
//		name: name
//		Closing PostgreSQL connection
	}
}
