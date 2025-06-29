package behavioral.interpreter.sqlgeneration;

import java.util.ArrayList;
import java.util.List;

/**
 * Non-terminal Expression for complete SQL Query
 * Combines multiple SQL expressions (SELECT, FROM, WHERE, ORDER BY, etc.)
 */
public class SQLQueryExpression implements SQLExpression {
    private SelectExpression selectExpression;
    private FromExpression fromExpression;
    private List<JoinExpression> joinExpressions;
    private List<WhereExpression> whereExpressions;
    private OrderByExpression orderByExpression;
    private Integer limitValue;
    
    /**
     * Constructor
     */
    public SQLQueryExpression() {
        this.joinExpressions = new ArrayList<>();
        this.whereExpressions = new ArrayList<>();
    }
    
    /**
     * Sets the SELECT clause
     */
    public SQLQueryExpression select(SelectExpression selectExpression) {
        this.selectExpression = selectExpression;
        return this;
    }
    
    /**
     * Sets the FROM clause
     */
    public SQLQueryExpression from(FromExpression fromExpression) {
        this.fromExpression = fromExpression;
        return this;
    }
    
    /**
     * Adds a JOIN clause
     */
    public SQLQueryExpression join(JoinExpression joinExpression) {
        this.joinExpressions.add(joinExpression);
        return this;
    }
    
    /**
     * Adds a WHERE clause
     */
    public SQLQueryExpression where(WhereExpression whereExpression) {
        this.whereExpressions.add(whereExpression);
        return this;
    }
    
    /**
     * Sets the ORDER BY clause
     */
    public SQLQueryExpression orderBy(OrderByExpression orderByExpression) {
        this.orderByExpression = orderByExpression;
        return this;
    }
    
    /**
     * Sets the LIMIT clause
     */
    public SQLQueryExpression limit(int limit) {
        this.limitValue = limit;
        return this;
    }
    
    @Override
    public String interpret(SQLContext context) {
        List<String> clauses = new ArrayList<>();
        
        // SELECT clause (required)
        if (selectExpression != null) {
            clauses.add(selectExpression.interpret(context));
        } else {
            clauses.add("SELECT *");
        }
        
        // FROM clause (required)
        if (fromExpression != null) {
            clauses.add(fromExpression.interpret(context));
        } else {
            throw new IllegalStateException("FROM clause is required for SQL query");
        }
        
        // JOIN clauses (optional, can be multiple)
        for (JoinExpression join : joinExpressions) {
            clauses.add(join.interpret(context));
        }
        
        // WHERE clauses (optional, can be multiple)
        if (!whereExpressions.isEmpty()) {
            List<String> whereClausesList = new ArrayList<>();
            for (WhereExpression where : whereExpressions) {
                String whereClause = where.interpret(context);
                // Remove "WHERE " prefix since we'll add it once
                if (whereClause.startsWith("WHERE ")) {
                    whereClause = whereClause.substring(6);
                }
                whereClausesList.add(whereClause);
            }
            clauses.add("WHERE " + String.join(" AND ", whereClausesList));
        }
        
        // ORDER BY clause (optional)
        if (orderByExpression != null) {
            String orderByClause = orderByExpression.interpret(context);
            if (!orderByClause.isEmpty()) {
                clauses.add(orderByClause);
            }
        }
        
        // LIMIT clause (optional)
        if (limitValue != null && limitValue > 0) {
            clauses.add("LIMIT " + limitValue);
        }
        
        return String.join(" ", clauses);
    }
    
    /**
     * Builder pattern for fluent API
     */
    public static class Builder {
        private SQLQueryExpression query = new SQLQueryExpression();
        
        public Builder select(String... columns) {
            if (columns.length == 0) {
                query.select(new SelectExpression());
            } else {
                List<String> columnList = List.of(columns);
                query.select(new SelectExpression(columnList));
            }
            return this;
        }
        
        public Builder from(String table) {
            query.from(new FromExpression(table));
            return this;
        }
        
        public Builder from(String table, String alias) {
            query.from(new FromExpression(table, alias));
            return this;
        }
        
        public Builder where(String column, String operator, String value) {
            query.where(new WhereExpression(column, operator, value));
            return this;
        }
        
        public Builder whereParam(String column, String operator, String paramName) {
            query.where(new WhereExpression(column, operator, paramName, true));
            return this;
        }
        
        public Builder orderBy(String column) {
            query.orderBy(new OrderByExpression(column));
            return this;
        }
        
        public Builder orderBy(String column, String direction) {
            query.orderBy(new OrderByExpression(column, direction));
            return this;
        }
        
        public Builder innerJoin(String table, String onCondition) {
            query.join(JoinExpression.innerJoin(table, onCondition));
            return this;
        }
        
        public Builder leftJoin(String table, String onCondition) {
            query.join(JoinExpression.leftJoin(table, onCondition));
            return this;
        }
        
        public Builder rightJoin(String table, String onCondition) {
            query.join(JoinExpression.rightJoin(table, onCondition));
            return this;
        }
        
        public Builder limit(int limit) {
            query.limit(limit);
            return this;
        }
        
        public SQLQueryExpression build() {
            return query;
        }
    }
} 