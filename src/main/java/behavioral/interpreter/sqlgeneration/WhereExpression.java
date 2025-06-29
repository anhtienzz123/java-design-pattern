package behavioral.interpreter.sqlgeneration;

/**
 * Terminal Expression for WHERE clause
 * Represents a SQL WHERE condition
 */
public class WhereExpression implements SQLExpression {
    private String column;
    private String operator;
    private String value;
    private boolean isParameterized;
    
    /**
     * Constructor for WHERE clause with direct value
     */
    public WhereExpression(String column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.isParameterized = false;
    }
    
    /**
     * Constructor for WHERE clause with parameterized value
     */
    public WhereExpression(String column, String operator, String parameterName, boolean isParameterized) {
        this.column = column;
        this.operator = operator;
        this.value = parameterName;
        this.isParameterized = isParameterized;
    }
    
    @Override
    public String interpret(SQLContext context) {
        StringBuilder sql = new StringBuilder("WHERE ");
        sql.append(column).append(" ").append(operator).append(" ");
        
        if (isParameterized) {
            // Use parameter from context
            String paramValue = context.getParameter(value);
            if (!paramValue.isEmpty()) {
                // For string values, add quotes
                if (paramValue.matches(".*[a-zA-Z].*")) {
                    sql.append("'").append(paramValue).append("'");
                } else {
                    sql.append(paramValue);
                }
            } else {
                // Use placeholder if parameter not found
                sql.append("?");
            }
        } else {
            // Direct value
            if (value.matches(".*[a-zA-Z].*") && !value.startsWith("'")) {
                sql.append("'").append(value).append("'");
            } else {
                sql.append(value);
            }
        }
        
        return sql.toString();
    }
    
    /**
     * Gets the column name
     */
    public String getColumn() {
        return column;
    }
    
    /**
     * Gets the operator
     */
    public String getOperator() {
        return operator;
    }
    
    /**
     * Gets the value
     */
    public String getValue() {
        return value;
    }
} 