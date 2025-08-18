package other.combinator.datapipeline;

import java.util.function.Function;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Combinator for transforming data records.
 * Provides composable transformations that can be chained together.
 */
@FunctionalInterface
public interface DataTransformer extends Function<DataRecord, DataRecord> {
    
    // Chain transformers (compose)
    default DataTransformer then(DataTransformer next) {
        return record -> next.apply(this.apply(record));
    }
    
    // Alternative chaining method (andThen from Function interface)
    default DataTransformer andThen(DataTransformer next) {
        return then(next);
    }
    
    // Apply transformation to a list of records
    default PipelineResult apply(List<DataRecord> records) {
        try {
            long startTime = System.currentTimeMillis();
            List<DataRecord> transformedRecords = records.stream()
                    .map(this)
                    .collect(Collectors.toList());
            long endTime = System.currentTimeMillis();
            
            String description = String.format("Transformed %d records", records.size());
            return PipelineResult.success(transformedRecords, endTime - startTime, description);
        } catch (Exception e) {
            return PipelineResult.failure(List.of("Transform operation failed: " + e.getMessage()), 
                                        "Transform operation failed");
        }
    }
    
    // Apply transformation with error handling per record
    default PipelineResult applyWithErrorHandling(List<DataRecord> records) {
        long startTime = System.currentTimeMillis();
        List<DataRecord> successfulRecords = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        for (DataRecord record : records) {
            try {
                DataRecord transformed = this.apply(record);
                successfulRecords.add(transformed);
            } catch (Exception e) {
                errors.add(String.format("Failed to transform record %s: %s", 
                                        record.getId(), e.getMessage()));
            }
        }
        
        long endTime = System.currentTimeMillis();
        String description = String.format("Transformed %d/%d records successfully", 
                                          successfulRecords.size(), records.size());
        
        if (errors.isEmpty()) {
            return PipelineResult.success(successfulRecords, endTime - startTime, description);
        } else {
            return PipelineResult.partial(successfulRecords, errors, description);
        }
    }
    
    // Static factory methods for common transformations
    
    // Value transformations
    static DataTransformer multiplyValue(double factor) {
        return record -> record.withValue(record.getValue() * factor);
    }
    
    static DataTransformer addToValue(double addition) {
        return record -> record.withValue(record.getValue() + addition);
    }
    
    static DataTransformer roundValue(int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return record -> {
            double roundedValue = Math.round(record.getValue() * factor) / factor;
            return record.withValue(roundedValue);
        };
    }
    
    static DataTransformer clampValue(double min, double max) {
        return record -> {
            double clampedValue = Math.max(min, Math.min(max, record.getValue()));
            return record.withValue(clampedValue);
        };
    }
    
    static DataTransformer normalizeValue(double minRange, double maxRange) {
        return record -> {
            // Assuming original range is 0-100, normalize to new range
            double normalizedValue = (record.getValue() / 100.0) * (maxRange - minRange) + minRange;
            return record.withValue(normalizedValue);
        };
    }
    
    // Category transformations
    static DataTransformer mapCategory(String fromCategory, String toCategory) {
        return record -> fromCategory.equals(record.getCategory()) 
                        ? record.withCategory(toCategory) 
                        : record;
    }
    
    static DataTransformer uppercaseCategory() {
        return record -> record.withCategory(record.getCategory().toUpperCase());
    }
    
    static DataTransformer lowercaseCategory() {
        return record -> record.withCategory(record.getCategory().toLowerCase());
    }
    
    static DataTransformer prefixCategory(String prefix) {
        return record -> record.withCategory(prefix + record.getCategory());
    }
    
    static DataTransformer suffixCategory(String suffix) {
        return record -> record.withCategory(record.getCategory() + suffix);
    }
    
    // Status transformations
    static DataTransformer setStatus(String status) {
        return record -> record.withStatus(status);
    }
    
    static DataTransformer mapStatus(String fromStatus, String toStatus) {
        return record -> fromStatus.equals(record.getStatus()) 
                        ? record.withStatus(toStatus) 
                        : record;
    }
    
    static DataTransformer activateIfPositiveValue() {
        return record -> record.getValue() > 0 
                        ? record.withStatus("ACTIVE") 
                        : record.withStatus("INACTIVE");
    }
    
    // Metadata transformations
    static DataTransformer addMetadata(String key, Object value) {
        return record -> record.withMetadata(key, value);
    }
    
    static DataTransformer addProcessingTimestamp() {
        return record -> record.withMetadata("processedAt", System.currentTimeMillis());
    }
    
    static DataTransformer addValueCategory() {
        return record -> {
            String valueCategory;
            double value = record.getValue();
            if (value < 10) valueCategory = "LOW";
            else if (value < 50) valueCategory = "MEDIUM";
            else if (value < 100) valueCategory = "HIGH";
            else valueCategory = "VERY_HIGH";
            
            return record.withMetadata("valueCategory", valueCategory);
        };
    }
    
    static DataTransformer addRecordHash() {
        return record -> {
            int hash = (record.getId() + record.getCategory() + record.getValue()).hashCode();
            return record.withMetadata("recordHash", Math.abs(hash));
        };
    }
    
    // Conditional transformations
    static DataTransformer conditionalTransform(DataFilter condition, DataTransformer transformer) {
        return record -> condition.test(record) ? transformer.apply(record) : record;
    }
    
    static DataTransformer ifThenElse(DataFilter condition, 
                                     DataTransformer thenTransformer, 
                                     DataTransformer elseTransformer) {
        return record -> condition.test(record) 
                        ? thenTransformer.apply(record) 
                        : elseTransformer.apply(record);
    }
    
    // Complex composite transformations
    static DataTransformer standardizeRecord() {
        return uppercaseCategory()
               .then(roundValue(2))
               .then(addProcessingTimestamp())
               .then(addValueCategory());
    }
    
    static DataTransformer enrichRecord() {
        return addProcessingTimestamp()
               .then(addRecordHash())
               .then(addValueCategory())
               .then(addMetadata("enriched", true));
    }
    
    static DataTransformer normalizeAndClassify() {
        return roundValue(2)
               .then(clampValue(0, 1000))
               .then(addValueCategory())
               .then(activateIfPositiveValue());
    }
    
    // Business-specific transformations
    static DataTransformer applySalesTax(double taxRate) {
        return conditionalTransform(
            DataFilter.byCategory("SALES"),
            multiplyValue(1 + taxRate).then(addMetadata("taxApplied", taxRate))
        );
    }
    
    static DataTransformer applyDiscountToHighValue(double threshold, double discountRate) {
        return conditionalTransform(
            DataFilter.byValueGreaterThan(threshold),
            multiplyValue(1 - discountRate).then(addMetadata("discountApplied", discountRate))
        );
    }
    
    // Identity transformer (no change)
    static DataTransformer identity() {
        return record -> record;
    }
}