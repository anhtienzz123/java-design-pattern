package creational.abstractfactory.databaseconnectivity;

// ConcreteProduct
public class MySQLConnection implements Connection {

	@Override
	public void open() {
		System.out.println("Opening MySQL connection");
	}

	@Override
	public void close() {
		System.out.println("Closing MySQL connection");
	}
}
