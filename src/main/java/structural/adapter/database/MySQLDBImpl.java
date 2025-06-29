package structural.adapter.database;

public class MySQLDBImpl implements MySQLDB {
	@Override
	public void executeMySQLQuery(String query) {
		System.out.println("Executing MySQL query: " + query);
	}
}
