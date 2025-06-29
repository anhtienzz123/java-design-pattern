package behavioral.interpreter.sqlgeneration;

/**
 * Abstract Expression interface for SQL generation
 * Defines the interpret method that returns a SQL string
 */
public interface SQLExpression {
    /**
     * Interprets the expression and returns the SQL string
     * @param context The context containing database information
     * @return Generated SQL string
     */
    String interpret(SQLContext context);
} 