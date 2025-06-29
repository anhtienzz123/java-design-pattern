package creational.abstractfactory.databaseconnectivity;

//AbstractProduct
public interface ResultSet {

	boolean hasNext();

	String getString(String columnName);

	int getInt(String columnName);
}
