package creational.abstractfactory.databaseconnectivity;

// ConcreteProduct
public class MySQLResultSet implements ResultSet {

	@Override
	public boolean hasNext() {
		System.out.println("Moving to next record in MySQL result set");
		return true;
	}

	@Override
	public String getString(String columnName) {
		System.out.println("MySQL getString");
		return columnName;
	}

	@Override
	public int getInt(String columnName) {
		System.out.println("MySQL getInt");
		return 1;
	}
}
