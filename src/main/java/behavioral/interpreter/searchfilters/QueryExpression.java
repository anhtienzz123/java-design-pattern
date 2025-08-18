package behavioral.interpreter.searchfilters;

import java.util.Arrays;
import java.util.List;

/**
 * Composite expression that combines multiple search criteria with a default AND logic.
 * Provides a high-level interface for building complex search queries.
 */
public class QueryExpression implements SearchExpression {
    private final SearchExpression rootExpression;
    private final String queryName;
    private final boolean optimized;
    
    public QueryExpression(SearchExpression rootExpression) {
        this(rootExpression, "Query", false);
    }
    
    public QueryExpression(SearchExpression rootExpression, String queryName, boolean optimized) {
        this.rootExpression = rootExpression;
        this.queryName = queryName;
        this.optimized = optimized;
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        return rootExpression.matches(item);
    }
    
    @Override
    public String getQueryString() {
        return queryName + ": " + rootExpression.getQueryString();
    }
    
    @Override
    public double getSelectivity() {
        return rootExpression.getSelectivity();
    }
    
    @Override
    public int getPriority() {
        return rootExpression.getPriority();
    }
    
    /**
     * Gets the root expression.
     */
    public SearchExpression getRootExpression() {
        return rootExpression;
    }
    
    /**
     * Gets the query name.
     */
    public String getQueryName() {
        return queryName;
    }
    
    /**
     * Checks if this query has been optimized.
     */
    public boolean isOptimized() {
        return optimized;
    }
    
    /**
     * Creates an optimized version of this query.
     */
    public QueryExpression optimize() {
        if (optimized) {
            return this;
        }
        
        SearchExpression optimizedRoot = optimizeExpression(rootExpression);
        return new QueryExpression(optimizedRoot, queryName, true);
    }
    
    private SearchExpression optimizeExpression(SearchExpression expr) {
        if (expr instanceof AndExpression andExpr) {
            return andExpr.flatten().optimize();
        } else if (expr instanceof OrExpression orExpr) {
            return orExpr.flatten().optimize();
        } else if (expr instanceof NotExpression notExpr) {
            return notExpr.optimize();
        } else {
            return expr;
        }
    }
    
    /**
     * Builder class for constructing complex queries.
     */
    public static class Builder {
        private SearchExpression expression;
        private String queryName = "Query";
        
        public Builder() {
            this.expression = null;
        }
        
        public Builder name(String name) {
            this.queryName = name;
            return this;
        }
        
        public Builder field(String fieldName, FieldExpression.ComparisonOperator operator, Object value) {
            return add(new FieldExpression(fieldName, operator, value));
        }
        
        public Builder text(String searchText, TextSearchExpression.TextSearchMode mode) {
            return add(new TextSearchExpression(searchText, mode));
        }
        
        public Builder range(String fieldName, Object min, Object max, RangeExpression.RangeType type) {
            return add(new RangeExpression(fieldName, min, max, true, true, type));
        }
        
        public Builder tags(TagExpression.TagMatchMode mode, String... tags) {
            return add(new TagExpression(java.util.Set.of(tags), mode));
        }
        
        public Builder and(SearchExpression... expressions) {
            return add(AndExpression.of(expressions));
        }
        
        public Builder or(SearchExpression... expressions) {
            return add(OrExpression.of(expressions));
        }
        
        public Builder not(SearchExpression expression) {
            return add(new NotExpression(expression));
        }
        
        public Builder add(SearchExpression expr) {
            if (expression == null) {
                expression = expr;
            } else {
                expression = AndExpression.of(expression, expr);
            }
            return this;
        }
        
        public Builder addOr(SearchExpression expr) {
            if (expression == null) {
                expression = expr;
            } else {
                expression = OrExpression.of(expression, expr);
            }
            return this;
        }
        
        public QueryExpression build() {
            if (expression == null) {
                throw new IllegalStateException("Query must have at least one expression");
            }
            return new QueryExpression(expression, queryName, false);
        }
        
        public QueryExpression buildOptimized() {
            return build().optimize();
        }
    }
    
    /**
     * Creates a new query builder.
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Creates a simple field equality query.
     */
    public static QueryExpression equals(String fieldName, Object value) {
        return new QueryExpression(
            new FieldExpression(fieldName, FieldExpression.ComparisonOperator.EQUALS, value),
            "FieldEquals",
            false
        );
    }
    
    /**
     * Creates a simple text search query.
     */
    public static QueryExpression textSearch(String searchText) {
        return new QueryExpression(
            new TextSearchExpression(searchText, TextSearchExpression.TextSearchMode.ANY_WORD),
            "TextSearch",
            false
        );
    }
    
    /**
     * Creates a simple tag query.
     */
    public static QueryExpression hasTag(String... tags) {
        return new QueryExpression(
            TagExpression.hasAny(tags),
            "TagQuery",
            false
        );
    }
}