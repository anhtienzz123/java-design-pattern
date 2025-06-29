package structural.adapter.database;

//Adapters for each database type
public class OracleAdapter implements DatabaseExecutor {
	private OracleDB oracleDB;

	public OracleAdapter(OracleDB oracleDB) {
		this.oracleDB = oracleDB;
	}

	@Override
	public void executeQuery(String query) {
		// Translate and adapt the query if needed
		String oracleQuery = translateToOracleQuery(query);
		oracleDB.executeOracleQuery(oracleQuery);
	}

	private String translateToOracleQuery(String query) {
		// In a real scenario, this would translate generic SQL to Oracle-specific SQL
		return query + " [Translated for Oracle]";
	}
}
