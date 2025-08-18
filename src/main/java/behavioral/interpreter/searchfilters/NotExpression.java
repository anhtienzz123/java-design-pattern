package behavioral.interpreter.searchfilters;

/**
 * Composite expression that performs logical NOT operation.
 * Inverts the result of the wrapped expression.
 */
public class NotExpression implements SearchExpression {
    private final SearchExpression expression;
    
    public NotExpression(SearchExpression expression) {
        this.expression = expression;
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        return !expression.matches(item);
    }
    
    @Override
    public String getQueryString() {
        return "NOT(" + expression.getQueryString() + ")";
    }
    
    @Override
    public double getSelectivity() {
        // NOT inverts selectivity
        return 1.0 - expression.getSelectivity();
    }
    
    @Override
    public int getPriority() {
        // NOT expressions generally have lower priority since they're less efficient
        return Math.max(0, expression.getPriority() - 1);
    }
    
    /**
     * Gets the wrapped expression.
     */
    public SearchExpression getExpression() {
        return expression;
    }
    
    /**
     * Optimizes double negation: NOT(NOT(expr)) becomes expr
     */
    public SearchExpression optimize() {
        if (expression instanceof NotExpression notExpr) {
            return notExpr.getExpression();
        }
        return this;
    }
}