package structural.adapter.database;

// Concrete implementations
public class OracleDBImpl implements OracleDB {
	@Override
	public void executeOracleQuery(String query) {
		System.out.println("Executing Oracle query: " + query);
	}
}
