package behavioral.interpreter.sqlgeneration;

import java.util.List;
import java.util.ArrayList;

/**
 * Terminal Expression for SELECT clause
 * Represents a SQL SELECT statement
 */
public class SelectExpression implements SQLExpression {
    private List<String> columns;
    private boolean selectAll;
    
    /**
     * Constructor for SELECT * 
     */
    public SelectExpression() {
        this.selectAll = true;
        this.columns = new ArrayList<>();
    }
    
    /**
     * Constructor for SELECT with specific columns
     */
    public SelectExpression(List<String> columns) {
        this.selectAll = false;
        this.columns = new ArrayList<>(columns);
    }
    
    /**
     * Constructor for SELECT with single column
     */
    public SelectExpression(String column) {
        this.selectAll = false;
        this.columns = new ArrayList<>();
        this.columns.add(column);
    }
    
    @Override
    public String interpret(SQLContext context) {
        if (selectAll) {
            return "SELECT *";
        }
        
        if (columns.isEmpty()) {
            return "SELECT *";
        }
        
        // Validate columns exist in current table if table is set
        String currentTable = context.getCurrentTable();
        if (!currentTable.isEmpty() && context.hasTable(currentTable)) {
            List<String> validColumns = new ArrayList<>();
            List<String> tableColumns = context.getTableColumns(currentTable);
            
            for (String column : columns) {
                if (tableColumns.contains(column)) {
                    validColumns.add(column);
                } else {
                    // Add warning comment or throw exception
                    validColumns.add(column + " /* WARNING: Column may not exist */");
                }
            }
            
            return "SELECT " + String.join(", ", validColumns);
        }
        
        // If no table context, just return the columns as-is
        return "SELECT " + String.join(", ", columns);
    }
} 