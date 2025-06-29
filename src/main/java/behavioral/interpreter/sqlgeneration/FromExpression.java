package behavioral.interpreter.sqlgeneration;

/**
 * Terminal Expression for FROM clause
 * Represents a SQL FROM clause
 */
public class FromExpression implements SQLExpression {
    private String tableName;
    private String alias;
    
    /**
     * Constructor for FROM with table name only
     */
    public FromExpression(String tableName) {
        this.tableName = tableName;
        this.alias = "";
    }
    
    /**
     * Constructor for FROM with table name and alias
     */
    public FromExpression(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }
    
    @Override
    public String interpret(SQLContext context) {
        // Set the current table in context for column validation
        context.setCurrentTable(tableName);
        
        StringBuilder sql = new StringBuilder("FROM ");
        sql.append(tableName);
        
        if (!alias.isEmpty()) {
            sql.append(" AS ").append(alias);
        }
        
        return sql.toString();
    }
    
    /**
     * Gets the table name
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * Gets the alias
     */
    public String getAlias() {
        return alias;
    }
} 