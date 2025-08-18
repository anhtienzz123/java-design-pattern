package behavioral.interpreter.searchfilters;

import java.util.List;

/**
 * Abstract base class for composite search expressions.
 * Provides common functionality for combining multiple search expressions.
 */
public abstract class CompositeSearchExpression implements SearchExpression {
    protected final List<SearchExpression> expressions;
    protected final String operatorName;
    
    protected CompositeSearchExpression(List<SearchExpression> expressions, String operatorName) {
        this.expressions = List.copyOf(expressions);
        this.operatorName = operatorName;
    }
    
    @Override
    public String getQueryString() {
        if (expressions.isEmpty()) {
            return operatorName + "()";
        }
        
        if (expressions.size() == 1) {
            return expressions.get(0).getQueryString();
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < expressions.size(); i++) {
            if (i > 0) {
                sb.append(" ").append(operatorName).append(" ");
            }
            sb.append(expressions.get(i).getQueryString());
        }
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public double getSelectivity() {
        if (expressions.isEmpty()) {
            return 1.0;
        }
        
        // Default implementation - subclasses should override for specific logic
        return expressions.stream()
                         .mapToDouble(SearchExpression::getSelectivity)
                         .average()
                         .orElse(0.5);
    }
    
    @Override
    public int getPriority() {
        if (expressions.isEmpty()) {
            return 0;
        }
        
        // Return the maximum priority of child expressions
        return expressions.stream()
                         .mapToInt(SearchExpression::getPriority)
                         .max()
                         .orElse(0);
    }
    
    /**
     * Gets the number of child expressions.
     */
    public int getExpressionCount() {
        return expressions.size();
    }
    
    /**
     * Gets a child expression by index.
     */
    public SearchExpression getExpression(int index) {
        return expressions.get(index);
    }
    
    /**
     * Gets all child expressions.
     */
    public List<SearchExpression> getExpressions() {
        return expressions;
    }
}