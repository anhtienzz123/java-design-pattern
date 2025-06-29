package creational.abstractfactory.databaseconnectivity;

// ConcreteProduct
public class PostgreSQLResultSet implements ResultSet {

	@Override
	public boolean hasNext() {
		System.out.println("Moving to next record in PostgreSQL result set");
		return true;
	}

	@Override
	public String getString(String columnName) {
		System.out.println("PostgreSQL getString");
		return columnName;
	}

	@Override
	public int getInt(String columnName) {
		System.out.println("PostgreSQL getInt");
		return 1;
	}
}
