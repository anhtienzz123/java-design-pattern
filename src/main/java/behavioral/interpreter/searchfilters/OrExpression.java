package behavioral.interpreter.searchfilters;

import java.util.Arrays;
import java.util.List;

/**
 * Composite expression that performs logical OR operation.
 * At least one child expression must match for the overall expression to match.
 */
public class OrExpression extends CompositeSearchExpression {
    
    public OrExpression(List<SearchExpression> expressions) {
        super(expressions, "OR");
    }
    
    public static OrExpression of(SearchExpression... expressions) {
        return new OrExpression(Arrays.asList(expressions));
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        // Short-circuit evaluation: return true as soon as any expression matches
        for (SearchExpression expression : expressions) {
            if (expression.matches(item)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public double getSelectivity() {
        if (expressions.isEmpty()) {
            return 0.0;
        }
        
        // For OR expressions, selectivity is higher than individual expressions
        // Formula: 1 - product of (1 - individual_selectivity)
        double product = expressions.stream()
                                   .mapToDouble(expr -> 1.0 - expr.getSelectivity())
                                   .reduce(1.0, (a, b) -> a * b);
        
        return 1.0 - product;
    }
    
    @Override
    public int getPriority() {
        if (expressions.isEmpty()) {
            return 0;
        }
        
        // For OR expressions, use the lowest priority among children
        // since any one expression can satisfy the condition
        return expressions.stream()
                         .mapToInt(SearchExpression::getPriority)
                         .min()
                         .orElse(0);
    }
    
    /**
     * Creates an optimized version of this OR expression.
     * Sorts child expressions by selectivity (highest first) for better performance.
     */
    public OrExpression optimize() {
        if (expressions.size() <= 1) {
            return this;
        }
        
        // Sort expressions by selectivity (descending) and priority (descending)
        List<SearchExpression> optimizedExpressions = expressions.stream()
            .sorted((e1, e2) -> {
                int selectivityCompare = Double.compare(e2.getSelectivity(), e1.getSelectivity());
                if (selectivityCompare != 0) {
                    return selectivityCompare;
                }
                // If selectivities are equal, prefer higher priority
                return Integer.compare(e2.getPriority(), e1.getPriority());
            })
            .toList();
        
        return new OrExpression(optimizedExpressions);
    }
    
    /**
     * Flattens nested OR expressions into a single OR expression.
     * (A OR (B OR C)) becomes (A OR B OR C)
     */
    public OrExpression flatten() {
        List<SearchExpression> flattenedExpressions = expressions.stream()
            .flatMap(expr -> {
                if (expr instanceof OrExpression orExpr) {
                    return orExpr.flatten().getExpressions().stream();
                } else {
                    return java.util.stream.Stream.of(expr);
                }
            })
            .toList();
        
        return new OrExpression(flattenedExpressions);
    }
}