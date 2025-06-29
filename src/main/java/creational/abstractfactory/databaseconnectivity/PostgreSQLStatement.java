package creational.abstractfactory.databaseconnectivity;

// ConcreteStatement
public class PostgreSQLStatement implements Statement {

	@Override
	public void prepare(String sql) {
		System.out.println("Preparing PostgreSQL statement: " + sql);
	}

	@Override
	public void execute() {
		System.out.println("Executing PostgreSQL statement");
	}
}
