package other.combinator.datapipeline;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the result of a pipeline operation.
 * Contains the processed records and any errors that occurred during processing.
 */
public class PipelineResult {
    private final List<DataRecord> records;
    private final List<String> errors;
    private final long processingTimeMs;
    private final String operationDescription;
    
    private PipelineResult(List<DataRecord> records, List<String> errors, 
                          long processingTimeMs, String operationDescription) {
        this.records = new ArrayList<>(records);
        this.errors = new ArrayList<>(errors);
        this.processingTimeMs = processingTimeMs;
        this.operationDescription = operationDescription;
    }
    
    // Factory methods
    public static PipelineResult success(List<DataRecord> records, String description) {
        return new PipelineResult(records, Collections.emptyList(), 0, description);
    }
    
    public static PipelineResult success(List<DataRecord> records, long processingTime, String description) {
        return new PipelineResult(records, Collections.emptyList(), processingTime, description);
    }
    
    public static PipelineResult failure(List<String> errors, String description) {
        return new PipelineResult(Collections.emptyList(), errors, 0, description);
    }
    
    public static PipelineResult partial(List<DataRecord> records, List<String> errors, String description) {
        return new PipelineResult(records, errors, 0, description);
    }
    
    // Getters
    public List<DataRecord> getRecords() { return new ArrayList<>(records); }
    public List<String> getErrors() { return new ArrayList<>(errors); }
    public long getProcessingTimeMs() { return processingTimeMs; }
    public String getOperationDescription() { return operationDescription; }
    
    // Status checks
    public boolean isSuccess() { return errors.isEmpty(); }
    public boolean hasErrors() { return !errors.isEmpty(); }
    public boolean isEmpty() { return records.isEmpty(); }
    public int getRecordCount() { return records.size(); }
    public int getErrorCount() { return errors.size(); }
    
    // Combine results
    public PipelineResult combine(PipelineResult other, String newDescription) {
        List<DataRecord> combinedRecords = new ArrayList<>(this.records);
        combinedRecords.addAll(other.records);
        
        List<String> combinedErrors = new ArrayList<>(this.errors);
        combinedErrors.addAll(other.errors);
        
        long totalTime = this.processingTimeMs + other.processingTimeMs;
        
        return new PipelineResult(combinedRecords, combinedErrors, totalTime, newDescription);
    }
    
    // Add error to existing result
    public PipelineResult withError(String error) {
        List<String> newErrors = new ArrayList<>(this.errors);
        newErrors.add(error);
        return new PipelineResult(this.records, newErrors, this.processingTimeMs, this.operationDescription);
    }
    
    // Add processing time
    public PipelineResult withProcessingTime(long timeMs) {
        return new PipelineResult(this.records, this.errors, timeMs, this.operationDescription);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PipelineResult{operation='%s', records=%d, errors=%d", 
                               operationDescription, records.size(), errors.size()));
        
        if (processingTimeMs > 0) {
            sb.append(String.format(", processingTime=%dms", processingTimeMs));
        }
        
        if (!errors.isEmpty()) {
            sb.append(", errorDetails=").append(errors);
        }
        
        sb.append("}");
        return sb.toString();
    }
    
    // Detailed string representation
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Pipeline Result: %s ===%n", operationDescription));
        sb.append(String.format("Records processed: %d%n", records.size()));
        sb.append(String.format("Errors encountered: %d%n", errors.size()));
        
        if (processingTimeMs > 0) {
            sb.append(String.format("Processing time: %d ms%n", processingTimeMs));
        }
        
        if (!errors.isEmpty()) {
            sb.append("Errors:%n");
            for (int i = 0; i < errors.size(); i++) {
                sb.append(String.format("  %d. %s%n", i + 1, errors.get(i)));
            }
        }
        
        if (!records.isEmpty() && records.size() <= 10) {
            sb.append("Records:%n");
            for (int i = 0; i < records.size(); i++) {
                sb.append(String.format("  %d. %s%n", i + 1, records.get(i)));
            }
        } else if (records.size() > 10) {
            sb.append("Records (showing first 5):%n");
            for (int i = 0; i < 5; i++) {
                sb.append(String.format("  %d. %s%n", i + 1, records.get(i)));
            }
            sb.append(String.format("  ... and %d more%n", records.size() - 5));
        }
        
        return sb.toString();
    }
}