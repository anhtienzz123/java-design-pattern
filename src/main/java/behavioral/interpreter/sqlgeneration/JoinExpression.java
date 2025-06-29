package behavioral.interpreter.sqlgeneration;

/**
 * Terminal Expression for JOIN clause
 * Represents different types of SQL JOINs
 */
public class JoinExpression implements SQLExpression {
    private String joinType;
    private String tableName;
    private String alias;
    private String onCondition;
    
    /**
     * Constructor for JOIN with table and condition
     */
    public JoinExpression(String joinType, String tableName, String onCondition) {
        this.joinType = joinType.toUpperCase();
        this.tableName = tableName;
        this.onCondition = onCondition;
        this.alias = "";
    }
    
    /**
     * Constructor for JOIN with table, alias, and condition
     */
    public JoinExpression(String joinType, String tableName, String alias, String onCondition) {
        this.joinType = joinType.toUpperCase();
        this.tableName = tableName;
        this.alias = alias;
        this.onCondition = onCondition;
    }
    
    @Override
    public String interpret(SQLContext context) {
        StringBuilder sql = new StringBuilder();
        
        // Validate join type
        String validJoinType = joinType;
        if (!isValidJoinType(joinType)) {
            validJoinType = "INNER JOIN"; // Default to INNER JOIN
        }
        
        sql.append(validJoinType).append(" ").append(tableName);
        
        if (!alias.isEmpty()) {
            sql.append(" AS ").append(alias);
        }
        
        if (!onCondition.isEmpty()) {
            sql.append(" ON ").append(onCondition);
        }
        
        return sql.toString();
    }
    
    /**
     * Validates if the join type is valid
     */
    private boolean isValidJoinType(String joinType) {
        switch (joinType) {
            case "INNER JOIN":
            case "LEFT JOIN":
            case "RIGHT JOIN":
            case "FULL OUTER JOIN":
            case "CROSS JOIN":
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Static factory methods for common join types
     */
    public static JoinExpression innerJoin(String table, String onCondition) {
        return new JoinExpression("INNER JOIN", table, onCondition);
    }
    
    public static JoinExpression leftJoin(String table, String onCondition) {
        return new JoinExpression("LEFT JOIN", table, onCondition);
    }
    
    public static JoinExpression rightJoin(String table, String onCondition) {
        return new JoinExpression("RIGHT JOIN", table, onCondition);
    }
    
    public static JoinExpression fullOuterJoin(String table, String onCondition) {
        return new JoinExpression("FULL OUTER JOIN", table, onCondition);
    }
    
    public static JoinExpression crossJoin(String table) {
        return new JoinExpression("CROSS JOIN", table, "");
    }
    
    /**
     * Getters
     */
    public String getJoinType() {
        return joinType;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public String getOnCondition() {
        return onCondition;
    }
} 