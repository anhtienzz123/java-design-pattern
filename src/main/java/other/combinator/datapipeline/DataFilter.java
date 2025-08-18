package other.combinator.datapipeline;

import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * Combinator for filtering data records.
 * Provides composable predicates that can be combined using logical operations.
 */
@FunctionalInterface
public interface DataFilter extends Predicate<DataRecord> {
    
    // Logical AND combination
    default DataFilter and(DataFilter other) {
        return record -> this.test(record) && other.test(record);
    }
    
    // Logical OR combination
    default DataFilter or(DataFilter other) {
        return record -> this.test(record) || other.test(record);
    }
    
    // Logical NOT
    default DataFilter not() {
        return record -> !this.test(record);
    }
    
    // Apply filter to a list of records
    default PipelineResult apply(List<DataRecord> records) {
        try {
            long startTime = System.currentTimeMillis();
            List<DataRecord> filteredRecords = records.stream()
                    .filter(this)
                    .collect(Collectors.toList());
            long endTime = System.currentTimeMillis();
            
            String description = String.format("Filtered %d records to %d records", 
                                             records.size(), filteredRecords.size());
            return PipelineResult.success(filteredRecords, endTime - startTime, description);
        } catch (Exception e) {
            return PipelineResult.failure(List.of("Filter operation failed: " + e.getMessage()), 
                                        "Filter operation failed");
        }
    }
    
    // Static factory methods for common filters
    
    // Category filters
    static DataFilter byCategory(String category) {
        return record -> category.equals(record.getCategory());
    }
    
    static DataFilter byCategoryIn(String... categories) {
        return record -> {
            for (String category : categories) {
                if (category.equals(record.getCategory())) {
                    return true;
                }
            }
            return false;
        };
    }
    
    // Value filters
    static DataFilter byValueGreaterThan(double threshold) {
        return record -> record.getValue() > threshold;
    }
    
    static DataFilter byValueLessThan(double threshold) {
        return record -> record.getValue() < threshold;
    }
    
    static DataFilter byValueBetween(double min, double max) {
        return record -> record.getValue() >= min && record.getValue() <= max;
    }
    
    static DataFilter byValueEquals(double value) {
        return record -> Double.compare(record.getValue(), value) == 0;
    }
    
    // Status filters
    static DataFilter byStatus(String status) {
        return record -> status.equals(record.getStatus());
    }
    
    static DataFilter byStatusIn(String... statuses) {
        return record -> {
            for (String status : statuses) {
                if (status.equals(record.getStatus())) {
                    return true;
                }
            }
            return false;
        };
    }
    
    // Time-based filters
    static DataFilter byRecentHours(int hours) {
        return record -> record.isRecent(hours);
    }
    
    static DataFilter byTimestampAfter(LocalDateTime dateTime) {
        return record -> record.getTimestamp().isAfter(dateTime);
    }
    
    static DataFilter byTimestampBefore(LocalDateTime dateTime) {
        return record -> record.getTimestamp().isBefore(dateTime);
    }
    
    static DataFilter byTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return record -> {
            LocalDateTime timestamp = record.getTimestamp();
            return !timestamp.isBefore(start) && !timestamp.isAfter(end);
        };
    }
    
    // Metadata filters
    static DataFilter hasMetadata(String key) {
        return record -> record.hasMetadata(key);
    }
    
    static DataFilter hasMetadataValue(String key, Object value) {
        return record -> value.equals(record.getMetadata(key));
    }
    
    static DataFilter metadataStringContains(String key, String substring) {
        return record -> {
            Object metaValue = record.getMetadata(key);
            return metaValue instanceof String && 
                   ((String) metaValue).toLowerCase().contains(substring.toLowerCase());
        };
    }
    
    // ID filters
    static DataFilter byIdStartsWith(String prefix) {
        return record -> record.getId().startsWith(prefix);
    }
    
    static DataFilter byIdEndsWith(String suffix) {
        return record -> record.getId().endsWith(suffix);
    }
    
    static DataFilter byIdContains(String substring) {
        return record -> record.getId().toLowerCase().contains(substring.toLowerCase());
    }
    
    static DataFilter byIdMatches(String regex) {
        return record -> record.getId().matches(regex);
    }
    
    // Complex composite filters
    static DataFilter isHighValueRecent(double minValue, int hours) {
        return byValueGreaterThan(minValue).and(byRecentHours(hours));
    }
    
    static DataFilter isActiveHighPriority() {
        return byStatus("ACTIVE").and(hasMetadata("priority")).and(hasMetadataValue("priority", "HIGH"));
    }
    
    static DataFilter isErrorOrWarning() {
        return byStatus("ERROR").or(byStatus("WARNING"));
    }
    
    static DataFilter isValidBusinessRecord() {
        return byCategoryIn("SALES", "MARKETING", "FINANCE")
               .and(byValueGreaterThan(0))
               .and(byStatus("ACTIVE"));
    }
    
    // Always true/false filters
    static DataFilter acceptAll() {
        return record -> true;
    }
    
    static DataFilter rejectAll() {
        return record -> false;
    }
}