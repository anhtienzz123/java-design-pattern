package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an item that can be searched and filtered.
 * Provides a flexible interface for accessing various types of data.
 */
public interface SearchableItem {
    
    /**
     * Gets the unique identifier of the item.
     */
    String getId();
    
    /**
     * Gets the type or category of the item.
     */
    String getType();
    
    /**
     * Gets a string field value by name.
     */
    String getStringField(String fieldName);
    
    /**
     * Gets a numeric field value by name.
     */
    Number getNumericField(String fieldName);
    
    /**
     * Gets a boolean field value by name.
     */
    Boolean getBooleanField(String fieldName);
    
    /**
     * Gets a date/time field value by name.
     */
    LocalDateTime getDateTimeField(String fieldName);
    
    /**
     * Gets a list field value by name.
     */
    List<String> getListField(String fieldName);
    
    /**
     * Gets a set of tags associated with the item.
     */
    Set<String> getTags();
    
    /**
     * Gets all available field names.
     */
    Set<String> getFieldNames();
    
    /**
     * Gets custom metadata as key-value pairs.
     */
    Map<String, Object> getMetadata();
    
    /**
     * Checks if the item has a specific field.
     */
    boolean hasField(String fieldName);
    
    /**
     * Gets the raw field value as an Object.
     */
    Object getFieldValue(String fieldName);
    
    /**
     * Gets the full-text searchable content of the item.
     */
    String getSearchableText();
    
    /**
     * Gets the creation or modification timestamp.
     */
    LocalDateTime getTimestamp();
    
    /**
     * Gets the item's score or ranking (if applicable).
     */
    default double getScore() {
        return 0.0;
    }
}