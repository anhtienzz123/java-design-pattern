package behavioral.interpreter.sqlgeneration;

import java.util.List;
import java.util.ArrayList;

/**
 * Terminal Expression for ORDER BY clause
 * Represents a SQL ORDER BY clause
 */
public class OrderByExpression implements SQLExpression {
    private List<String> columns;
    private List<String> directions;
    
    /**
     * Constructor for ORDER BY with single column
     */
    public OrderByExpression(String column) {
        this.columns = new ArrayList<>();
        this.directions = new ArrayList<>();
        this.columns.add(column);
        this.directions.add("ASC");
    }
    
    /**
     * Constructor for ORDER BY with single column and direction
     */
    public OrderByExpression(String column, String direction) {
        this.columns = new ArrayList<>();
        this.directions = new ArrayList<>();
        this.columns.add(column);
        this.directions.add(direction.toUpperCase());
    }
    
    /**
     * Constructor for ORDER BY with multiple columns and directions
     */
    public OrderByExpression(List<String> columns, List<String> directions) {
        this.columns = new ArrayList<>(columns);
        this.directions = new ArrayList<>();
        
        // Ensure directions list matches columns list
        for (int i = 0; i < columns.size(); i++) {
            if (i < directions.size()) {
                this.directions.add(directions.get(i).toUpperCase());
            } else {
                this.directions.add("ASC"); // Default to ASC
            }
        }
    }
    
    @Override
    public String interpret(SQLContext context) {
        if (columns.isEmpty()) {
            return "";
        }
        
        StringBuilder sql = new StringBuilder("ORDER BY ");
        
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            
            String column = columns.get(i);
            String direction = directions.get(i);
            
            // Validate direction
            if (!direction.equals("ASC") && !direction.equals("DESC")) {
                direction = "ASC";
            }
            
            sql.append(column).append(" ").append(direction);
        }
        
        return sql.toString();
    }
    
    /**
     * Adds a column to the ORDER BY clause
     */
    public void addColumn(String column, String direction) {
        columns.add(column);
        directions.add(direction.toUpperCase());
    }
    
    /**
     * Gets the columns
     */
    public List<String> getColumns() {
        return new ArrayList<>(columns);
    }
    
    /**
     * Gets the directions
     */
    public List<String> getDirections() {
        return new ArrayList<>(directions);
    }
} 