package structural.adapter.database;

// Client code using the adapter
public class ZMain {
	public static void main(String[] args) {
		String query = "SELECT * FROM users";

		// Using Oracle
		DatabaseExecutor oracleExecutor = new OracleAdapter(new OracleDBImpl());
		oracleExecutor.executeQuery(query);

		// Using MySQL
		DatabaseExecutor mySqlExecutor = new MySQLAdapter(new MySQLDBImpl());
		mySqlExecutor.executeQuery(query);

//		== Output: 
//		Executing Oracle query: SELECT * FROM users [Translated for Oracle]
//		Executing MySQL query: SELECT * FROM users [Translated for MySQL]			
	}
}
