package creational.abstractfactory.databaseconnectivity;

// AbstractProduct
public interface Statement {

	void prepare(String sql);

	void execute();
}
