package behavioral.interpreter.sqlgeneration;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Context class for SQL generation
 * Holds database metadata, table schemas, and query parameters
 */
public class SQLContext {
    private Map<String, List<String>> tableColumns = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private String currentTable = "";
    
    /**
     * Sets the current table for the query
     */
    public void setCurrentTable(String tableName) {
        this.currentTable = tableName;
    }
    
    /**
     * Gets the current table
     */
    public String getCurrentTable() {
        return currentTable;
    }
    
    /**
     * Adds columns for a table
     */
    public void addTableColumns(String tableName, List<String> columns) {
        tableColumns.put(tableName, new ArrayList<>(columns));
    }
    
    /**
     * Gets columns for a table
     */
    public List<String> getTableColumns(String tableName) {
        return tableColumns.getOrDefault(tableName, new ArrayList<>());
    }
    
    /**
     * Sets a parameter value
     */
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }
    
    /**
     * Gets a parameter value
     */
    public String getParameter(String name) {
        return parameters.getOrDefault(name, "");
    }
    
    /**
     * Checks if a table exists in the context
     */
    public boolean hasTable(String tableName) {
        return tableColumns.containsKey(tableName);
    }
    
    /**
     * Checks if a column exists in the given table
     */
    public boolean hasColumn(String tableName, String columnName) {
        List<String> columns = tableColumns.get(tableName);
        return columns != null && columns.contains(columnName);
    }
} 