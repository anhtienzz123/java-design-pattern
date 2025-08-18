package other.combinator.datapipeline;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Combinator for processing data records.
 * Combines filtering, transformation, and validation into composable processing units.
 */
@FunctionalInterface
public interface DataProcessor {
    
    PipelineResult process(List<DataRecord> records);
    
    // Sequential combination - process with this, then with other
    default DataProcessor then(DataProcessor other) {
        return records -> {
            PipelineResult firstResult = this.process(records);
            if (!firstResult.isSuccess() && firstResult.isEmpty()) {
                return firstResult; // Short-circuit on complete failure
            }
            
            PipelineResult secondResult = other.process(firstResult.getRecords());
            return firstResult.combine(secondResult, "Sequential processing");
        };
    }
    
    // Parallel combination - process with both and combine results
    default DataProcessor parallel(DataProcessor other) {
        return records -> {
            PipelineResult firstResult = this.process(records);
            PipelineResult secondResult = other.process(records);
            return firstResult.combine(secondResult, "Parallel processing");
        };
    }
    
    // Alternative processing - try this first, if it fails completely, try other
    default DataProcessor orElse(DataProcessor other) {
        return records -> {
            PipelineResult firstResult = this.process(records);
            if (firstResult.isSuccess() || !firstResult.isEmpty()) {
                return firstResult;
            }
            return other.process(records);
        };
    }
    
    // Conditional processing
    default DataProcessor when(DataFilter condition) {
        return records -> {
            List<DataRecord> matchingRecords = condition.apply(records).getRecords();
            if (matchingRecords.isEmpty()) {
                return PipelineResult.success(records, "No records matched condition - no processing applied");
            }
            return this.process(matchingRecords);
        };
    }
    
    // Static factory methods
    
    // Filter-based processor
    static DataProcessor filter(DataFilter filter) {
        return records -> filter.apply(records);
    }
    
    // Transform-based processor
    static DataProcessor transform(DataTransformer transformer) {
        return records -> transformer.apply(records);
    }
    
    // Transform with error handling
    static DataProcessor transformSafely(DataTransformer transformer) {
        return records -> transformer.applyWithErrorHandling(records);
    }
    
    // Combined filter and transform
    static DataProcessor filterAndTransform(DataFilter filter, DataTransformer transformer) {
        return records -> {
            PipelineResult filterResult = filter.apply(records);
            if (!filterResult.isSuccess() || filterResult.isEmpty()) {
                return filterResult;
            }
            
            PipelineResult transformResult = transformer.apply(filterResult.getRecords());
            return filterResult.combine(transformResult, "Filter and Transform");
        };
    }
    
    // Validation processor
    static DataProcessor validate(DataFilter validator, String errorMessage) {
        return records -> {
            List<DataRecord> validRecords = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            
            for (DataRecord record : records) {
                if (validator.test(record)) {
                    validRecords.add(record);
                } else {
                    errors.add(String.format("%s - Record: %s", errorMessage, record.getId()));
                }
            }
            
            if (errors.isEmpty()) {
                return PipelineResult.success(validRecords, "Validation passed");
            } else {
                return PipelineResult.partial(validRecords, errors, "Validation completed with errors");
            }
        };
    }
    
    // Aggregation processor
    static DataProcessor aggregate(String description) {
        return records -> {
            if (records.isEmpty()) {
                return PipelineResult.success(records, "No records to aggregate");
            }
            
            // Simple aggregation example - create summary record
            double totalValue = records.stream().mapToDouble(DataRecord::getValue).sum();
            double averageValue = totalValue / records.size();
            long uniqueCategories = records.stream().map(DataRecord::getCategory).distinct().count();
            
            DataRecord summaryRecord = new DataRecord(
                "SUMMARY_" + System.currentTimeMillis(),
                "AGGREGATE",
                averageValue,
                records.get(0).getTimestamp(),
                "COMPUTED"
            )
            .withMetadata("totalValue", totalValue)
            .withMetadata("recordCount", records.size())
            .withMetadata("uniqueCategories", uniqueCategories);
            
            return PipelineResult.success(List.of(summaryRecord), 
                                        String.format("%s - Aggregated %d records", description, records.size()));
        };
    }
    
    // Batch processing
    static DataProcessor batch(int batchSize, DataProcessor processor) {
        return records -> {
            if (records.size() <= batchSize) {
                return processor.process(records);
            }
            
            List<DataRecord> allProcessedRecords = new ArrayList<>();
            List<String> allErrors = new ArrayList<>();
            long totalTime = 0;
            
            for (int i = 0; i < records.size(); i += batchSize) {
                int endIndex = Math.min(i + batchSize, records.size());
                List<DataRecord> batch = records.subList(i, endIndex);
                
                PipelineResult batchResult = processor.process(batch);
                allProcessedRecords.addAll(batchResult.getRecords());
                allErrors.addAll(batchResult.getErrors());
                totalTime += batchResult.getProcessingTimeMs();
            }
            
            String description = String.format("Batch processing (%d batches of max %d records)", 
                                             (records.size() + batchSize - 1) / batchSize, batchSize);
            
            if (allErrors.isEmpty()) {
                return PipelineResult.success(allProcessedRecords, totalTime, description);
            } else {
                return PipelineResult.partial(allProcessedRecords, allErrors, description);
            }
        };
    }
    
    // Error recovery processor
    static DataProcessor withErrorRecovery(DataProcessor mainProcessor, 
                                          DataProcessor fallbackProcessor) {
        return records -> {
            PipelineResult mainResult = mainProcessor.process(records);
            if (mainResult.isSuccess()) {
                return mainResult;
            }
            
            // Try fallback for failed records
            if (mainResult.hasErrors() && !mainResult.isEmpty()) {
                // Main processor partially succeeded, keep successful records
                return mainResult;
            } else {
                // Main processor completely failed, try fallback
                PipelineResult fallbackResult = fallbackProcessor.process(records);
                return PipelineResult.partial(
                    fallbackResult.getRecords(),
                    List.of("Main processor failed, used fallback: " + String.join(", ", mainResult.getErrors())),
                    "Error recovery processing"
                );
            }
        };
    }
    
    // Monitoring processor
    static DataProcessor withMonitoring(DataProcessor processor, String operationName) {
        return records -> {
            System.out.printf("🔄 Starting %s for %d records%n", operationName, records.size());
            long startTime = System.currentTimeMillis();
            
            PipelineResult result = processor.process(records);
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            System.out.printf("✅ Completed %s: %d→%d records, %d errors, %dms%n", 
                             operationName, records.size(), result.getRecordCount(), 
                             result.getErrorCount(), duration);
            
            return result.withProcessingTime(duration);
        };
    }
    
    // Complex business processors
    static DataProcessor salesDataPipeline() {
        return filter(DataFilter.byCategory("SALES"))
               .then(transform(DataTransformer.applySalesTax(0.08)))
               .then(transform(DataTransformer.roundValue(2)))
               .then(validate(DataFilter.byValueGreaterThan(0), "Sales amount must be positive"));
    }
    
    static DataProcessor dataQualityPipeline() {
        return validate(DataFilter.acceptAll().not(), "Record cannot be null")
               .then(transform(DataTransformer.standardizeRecord()))
               .then(validate(DataFilter.byValueBetween(0, 1000000), "Value must be within valid range"))
               .then(filter(DataFilter.byStatus("ACTIVE")));
    }
    
    static DataProcessor enrichmentPipeline() {
        return transform(DataTransformer.enrichRecord())
               .then(transform(DataTransformer.addValueCategory()))
               .then(filter(DataFilter.hasMetadata("enriched")));
    }
    
    // Identity processor (no-op)
    static DataProcessor identity() {
        return records -> PipelineResult.success(records, "Identity processing - no changes");
    }
    
    // Custom processor from supplier
    static DataProcessor custom(Supplier<PipelineResult> resultSupplier) {
        return records -> resultSupplier.get();
    }
}