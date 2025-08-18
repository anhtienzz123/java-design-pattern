package other.combinator.datapipeline;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a data record that flows through the pipeline.
 * Contains various attributes that can be processed, filtered, and transformed.
 */
public class DataRecord {
    private final String id;
    private final String category;
    private final double value;
    private final LocalDateTime timestamp;
    private final String status;
    private final Map<String, Object> metadata;
    
    public DataRecord(String id, String category, double value, LocalDateTime timestamp, String status) {
        this.id = id;
        this.category = category;
        this.value = value;
        this.timestamp = timestamp;
        this.status = status;
        this.metadata = new HashMap<>();
    }
    
    // Copy constructor for transformations
    public DataRecord(DataRecord original) {
        this.id = original.id;
        this.category = original.category;
        this.value = original.value;
        this.timestamp = original.timestamp;
        this.status = original.status;
        this.metadata = new HashMap<>(original.metadata);
    }
    
    // Builder methods for transformations
    public DataRecord withCategory(String category) {
        DataRecord copy = new DataRecord(this);
        return new DataRecord(copy.id, category, copy.value, copy.timestamp, copy.status);
    }
    
    public DataRecord withValue(double value) {
        DataRecord copy = new DataRecord(this);
        return new DataRecord(copy.id, copy.category, value, copy.timestamp, copy.status);
    }
    
    public DataRecord withStatus(String status) {
        DataRecord copy = new DataRecord(this);
        return new DataRecord(copy.id, copy.category, copy.value, copy.timestamp, status);
    }
    
    public DataRecord withMetadata(String key, Object value) {
        DataRecord copy = new DataRecord(this);
        copy.metadata.put(key, value);
        return copy;
    }
    
    // Getters
    public String getId() { return id; }
    public String getCategory() { return category; }
    public double getValue() { return value; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public Map<String, Object> getMetadata() { return new HashMap<>(metadata); }
    public Object getMetadata(String key) { return metadata.get(key); }
    
    // Helper methods
    public boolean hasMetadata(String key) { return metadata.containsKey(key); }
    
    public boolean isRecent(int hours) {
        return timestamp.isAfter(LocalDateTime.now().minusHours(hours));
    }
    
    @Override
    public String toString() {
        return String.format("DataRecord{id='%s', category='%s', value=%.2f, status='%s', timestamp=%s, metadata=%s}", 
                           id, category, value, status, timestamp, metadata);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataRecord that = (DataRecord) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}