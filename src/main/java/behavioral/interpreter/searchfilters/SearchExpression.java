package behavioral.interpreter.searchfilters;

/**
 * Abstract expression interface for search filter evaluation.
 * Defines the contract for interpreting search queries and filtering data.
 */
public interface SearchExpression {
    /**
     * Evaluates the search expression against a data item.
     * 
     * @param item The data item to evaluate against
     * @return true if the item matches the search criteria, false otherwise
     */
    boolean matches(SearchableItem item);
    
    /**
     * Returns a string representation of the search expression.
     * Used for debugging and query display purposes.
     */
    String getQueryString();
    
    /**
     * Returns the estimated selectivity of this expression (0.0 to 1.0).
     * Used for query optimization - lower values indicate more selective filters.
     */
    default double getSelectivity() {
        return 0.5; // Default neutral selectivity
    }
    
    /**
     * Returns the priority of this expression for optimization.
     * Higher values indicate expressions that should be evaluated first.
     */
    default int getPriority() {
        return 0; // Default neutral priority
    }
}