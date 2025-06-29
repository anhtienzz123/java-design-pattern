package creational.abstractfactory.databaseconnectivity;

// ConcreteStatement
public class MySQLStatement implements Statement {

	@Override
	public void prepare(String sql) {
		System.out.println("Preparing MySQL statement: " + sql);
	}

	@Override
	public void execute() {
		System.out.println("Executing MySQL statement");
	}
}
