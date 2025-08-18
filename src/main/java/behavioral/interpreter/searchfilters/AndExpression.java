package behavioral.interpreter.searchfilters;

import java.util.Arrays;
import java.util.List;

/**
 * Composite expression that performs logical AND operation.
 * All child expressions must match for the overall expression to match.
 */
public class AndExpression extends CompositeSearchExpression {
    
    public AndExpression(List<SearchExpression> expressions) {
        super(expressions, "AND");
    }
    
    public static AndExpression of(SearchExpression... expressions) {
        return new AndExpression(Arrays.asList(expressions));
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        // Short-circuit evaluation: return false as soon as any expression doesn't match
        for (SearchExpression expression : expressions) {
            if (!expression.matches(item)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public double getSelectivity() {
        if (expressions.isEmpty()) {
            return 1.0;
        }
        
        // For AND expressions, selectivity is the product of all child selectivities
        // (more restrictive than any individual expression)
        return expressions.stream()
                         .mapToDouble(SearchExpression::getSelectivity)
                         .reduce(1.0, (a, b) -> a * b);
    }
    
    @Override
    public int getPriority() {
        if (expressions.isEmpty()) {
            return 0;
        }
        
        // For AND expressions, use the highest priority among children
        // since we want to evaluate the most selective expressions first
        return expressions.stream()
                         .mapToInt(SearchExpression::getPriority)
                         .max()
                         .orElse(0);
    }
    
    /**
     * Creates an optimized version of this AND expression.
     * Sorts child expressions by priority (highest first) for better performance.
     */
    public AndExpression optimize() {
        if (expressions.size() <= 1) {
            return this;
        }
        
        // Sort expressions by priority (descending) and selectivity (ascending)
        List<SearchExpression> optimizedExpressions = expressions.stream()
            .sorted((e1, e2) -> {
                int priorityCompare = Integer.compare(e2.getPriority(), e1.getPriority());
                if (priorityCompare != 0) {
                    return priorityCompare;
                }
                // If priorities are equal, prefer more selective (lower selectivity value)
                return Double.compare(e1.getSelectivity(), e2.getSelectivity());
            })
            .toList();
        
        return new AndExpression(optimizedExpressions);
    }
    
    /**
     * Flattens nested AND expressions into a single AND expression.
     * (A AND (B AND C)) becomes (A AND B AND C)
     */
    public AndExpression flatten() {
        List<SearchExpression> flattenedExpressions = expressions.stream()
            .flatMap(expr -> {
                if (expr instanceof AndExpression andExpr) {
                    return andExpr.flatten().getExpressions().stream();
                } else {
                    return java.util.stream.Stream.of(expr);
                }
            })
            .toList();
        
        return new AndExpression(flattenedExpressions);
    }
}