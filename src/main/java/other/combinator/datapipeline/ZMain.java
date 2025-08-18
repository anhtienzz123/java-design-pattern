package other.combinator.datapipeline;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ZMain {
    
    private static String repeat(String str, int count) {
        return str.repeat(count);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Combinator Pattern - Data Pipeline Processing System Demo ===\n");
        
        // Create sample data records
        List<DataRecord> sampleData = createSampleData();
        
        System.out.println("📊 Initial Dataset:");
        System.out.println(repeat("=", 20));
        sampleData.forEach(record -> System.out.printf("  • %s%n", record));
        
        System.out.println("\n" + repeat("=", 80) + "\n");
        
        // Demo 1: Basic Filter Combinators
        System.out.println("1. Basic Filter Combinators:");
        System.out.println(repeat("=", 35));
        
        // Single filters
        DataFilter highValueFilter = DataFilter.byValueGreaterThan(50);
        DataFilter salesFilter = DataFilter.byCategory("SALES");
        DataFilter recentFilter = DataFilter.byRecentHours(24);
        
        System.out.println("\n🔍 High value records (>50):");
        PipelineResult highValueResult = highValueFilter.apply(sampleData);
        System.out.println(highValueResult.toDetailedString());
        
        // Combined filters using AND
        DataFilter highValueSales = salesFilter.and(highValueFilter);
        System.out.println("🔍 High value SALES records:");
        PipelineResult combinedResult = highValueSales.apply(sampleData);
        System.out.println(combinedResult.toDetailedString());
        
        // Combined filters using OR
        DataFilter salesOrMarketing = DataFilter.byCategory("SALES").or(DataFilter.byCategory("MARKETING"));
        System.out.println("🔍 SALES or MARKETING records:");
        PipelineResult orResult = salesOrMarketing.apply(sampleData);
        System.out.println(orResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 2: Transformation Combinators
        System.out.println("2. Transformation Combinators:");
        System.out.println(repeat("=", 36));
        
        // Single transformations
        DataTransformer valueDoubler = DataTransformer.multiplyValue(2.0);
        DataTransformer categoryUppercase = DataTransformer.uppercaseCategory();
        DataTransformer addTimestamp = DataTransformer.addProcessingTimestamp();
        
        // Chained transformations
        DataTransformer complexTransform = valueDoubler
                .then(categoryUppercase)
                .then(addTimestamp)
                .then(DataTransformer.addValueCategory());
        
        System.out.println("\n🔄 Applying complex transformation pipeline:");
        List<DataRecord> testRecords = sampleData.subList(0, 3);
        PipelineResult transformResult = complexTransform.apply(testRecords);
        System.out.println(transformResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 3: Advanced Processing Combinators
        System.out.println("3. Advanced Processing Combinators:");
        System.out.println(repeat("=", 40));
        
        // Filter and Transform combination
        DataProcessor salesProcessor = DataProcessor.filterAndTransform(
            DataFilter.byCategory("SALES"),
            DataTransformer.applySalesTax(0.08).then(DataTransformer.roundValue(2))
        );
        
        System.out.println("\n💼 Sales Processing Pipeline:");
        PipelineResult salesResult = salesProcessor.process(sampleData);
        System.out.println(salesResult.toDetailedString());
        
        // Sequential processing
        DataProcessor qualityPipeline = DataProcessor.filter(DataFilter.byValueGreaterThan(0))
                .then(DataProcessor.transform(DataTransformer.standardizeRecord()))
                .then(DataProcessor.validate(DataFilter.byStatus("ACTIVE"), "Record must be active"));
        
        System.out.println("🔍 Data Quality Pipeline:");
        PipelineResult qualityResult = qualityPipeline.process(sampleData);
        System.out.println(qualityResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 4: Business Logic Combinators
        System.out.println("4. Business Logic Combinators:");
        System.out.println(repeat("=", 35));
        
        // Complex business rules
        DataFilter premiumCustomer = DataFilter.byValueGreaterThan(100)
                .and(DataFilter.hasMetadata("customerType"))
                .and(DataFilter.hasMetadataValue("customerType", "PREMIUM"));
        
        DataTransformer premiumProcessing = DataTransformer.conditionalTransform(
            premiumCustomer,
            DataTransformer.applyDiscountToHighValue(100, 0.1)
                    .then(DataTransformer.addMetadata("discountTier", "PREMIUM"))
        );
        
        // Add customer metadata to some records for demo
        List<DataRecord> businessData = sampleData.stream()
                .map(record -> record.getValue() > 75 
                     ? record.withMetadata("customerType", "PREMIUM")
                     : record.withMetadata("customerType", "STANDARD"))
                .toList();
        
        System.out.println("\n👑 Premium Customer Processing:");
        PipelineResult businessResult = premiumProcessing.apply(businessData);
        System.out.println(businessResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 5: Error Handling and Recovery
        System.out.println("5. Error Handling and Recovery:");
        System.out.println(repeat("=", 37));
        
        // Processor that might fail
        DataProcessor riskyProcessor = DataProcessor.transform(
            record -> {
                if (record.getValue() < 0) {
                    throw new RuntimeException("Negative values not allowed");
                }
                return record.withValue(record.getValue() * 1.5);
            }
        );
        
        // Safe processor with error handling
        DataProcessor safeProcessor = DataProcessor.transformSafely(
            record -> record.withValue(Math.abs(record.getValue()) * 1.5)
        );
        
        // Add some problematic data
        List<DataRecord> riskyData = Arrays.asList(
            new DataRecord("RISKY-1", "TEST", -10, LocalDateTime.now(), "ACTIVE"),
            new DataRecord("RISKY-2", "TEST", 50, LocalDateTime.now(), "ACTIVE"),
            new DataRecord("RISKY-3", "TEST", -5, LocalDateTime.now(), "ACTIVE")
        );
        
        System.out.println("\n⚠️ Processing risky data with error recovery:");
        DataProcessor recoveryProcessor = DataProcessor.withErrorRecovery(riskyProcessor, safeProcessor);
        PipelineResult recoveryResult = recoveryProcessor.process(riskyData);
        System.out.println(recoveryResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 6: Parallel and Batch Processing
        System.out.println("6. Parallel and Batch Processing:");
        System.out.println(repeat("=", 38));
        
        DataProcessor batchProcessor = DataProcessor.batch(2, 
            DataProcessor.transform(DataTransformer.enrichRecord())
        );
        
        System.out.println("\n📦 Batch Processing (batch size = 2):");
        PipelineResult batchResult = DataProcessor.withMonitoring(batchProcessor, "Batch Enrichment")
                .process(sampleData);
        System.out.println(batchResult.toDetailedString());
        
        // Aggregation example
        System.out.println("📈 Data Aggregation:");
        PipelineResult aggregationResult = DataProcessor.aggregate("Summary Statistics")
                .process(sampleData);
        System.out.println(aggregationResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Demo 7: Real-world Pipeline Composition
        System.out.println("7. Real-world Pipeline Composition:");
        System.out.println(repeat("=", 42));
        
        // Complete data processing pipeline
        DataProcessor completeePipeline = 
            DataProcessor.withMonitoring(
                DataProcessor.dataQualityPipeline()
                    .then(DataProcessor.enrichmentPipeline())
                    .then(DataProcessor.salesDataPipeline().orElse(DataProcessor.identity()))
                    .then(DataProcessor.aggregate("Final Summary")),
                "Complete Data Pipeline"
            );
        
        System.out.println("\n🏭 Complete Enterprise Data Pipeline:");
        PipelineResult finalResult = completeePipeline.process(sampleData);
        System.out.println(finalResult.toDetailedString());
        
        System.out.println(repeat("=", 80) + "\n");
        
        // Benefits summary
        System.out.println("=== Key Benefits of Combinator Pattern Demonstrated ===");
        System.out.println("✅ COMPOSABILITY: Small functions combine to create complex logic");
        System.out.println("✅ REUSABILITY: Individual combinators can be reused across pipelines");
        System.out.println("✅ READABILITY: Pipeline logic is expressed declaratively");
        System.out.println("✅ TESTABILITY: Each combinator can be tested independently");
        System.out.println("✅ FLEXIBILITY: Easy to modify, extend, and recombine processing logic");
        System.out.println("✅ TYPE SAFETY: Compile-time guarantees about data flow");
        
        System.out.println("\n=== Pattern Structure ===");
        System.out.println("DataFilter (Predicate Combinator)");
        System.out.println("├── and() / or() / not() - Logical combinations");
        System.out.println("├── Static factory methods for common filters");
        System.out.println("└── apply() - Execute on data collections");
        System.out.println("");
        System.out.println("DataTransformer (Function Combinator)");
        System.out.println("├── then() / andThen() - Sequential chaining");
        System.out.println("├── Static factory methods for common transformations");
        System.out.println("└── apply() - Execute on data collections");
        System.out.println("");
        System.out.println("DataProcessor (Pipeline Combinator)");
        System.out.println("├── then() / parallel() / orElse() - Composition operators");
        System.out.println("├── withErrorRecovery() / withMonitoring() - Cross-cutting concerns");
        System.out.println("└── process() - Execute complete pipelines");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("• ETL (Extract, Transform, Load) data pipelines");
        System.out.println("• Stream processing and real-time analytics");
        System.out.println("• Data validation and quality assurance");
        System.out.println("• Business rule engines and policy evaluation");
        System.out.println("• API request/response transformation");
        System.out.println("• Configuration-driven data processing workflows");
    }
    
    private static List<DataRecord> createSampleData() {
        LocalDateTime now = LocalDateTime.now();
        
        return Arrays.asList(
            new DataRecord("SALES-001", "SALES", 125.50, now.minusHours(2), "ACTIVE"),
            new DataRecord("MARK-002", "MARKETING", 75.25, now.minusHours(5), "ACTIVE"),
            new DataRecord("SALES-003", "SALES", 200.00, now.minusHours(1), "ACTIVE"),
            new DataRecord("FIN-004", "FINANCE", 45.75, now.minusHours(8), "INACTIVE"),
            new DataRecord("MARK-005", "MARKETING", 90.00, now.minusMinutes(30), "ACTIVE"),
            new DataRecord("SALES-006", "SALES", 15.50, now.minusHours(12), "ACTIVE"),
            new DataRecord("TECH-007", "TECHNOLOGY", 300.00, now.minusHours(3), "ACTIVE"),
            new DataRecord("FIN-008", "FINANCE", 180.25, now.minusHours(6), "ACTIVE"),
            new DataRecord("MARK-009", "MARKETING", 25.00, now.minusHours(24), "INACTIVE"),
            new DataRecord("SALES-010", "SALES", 95.75, now.minusMinutes(45), "ACTIVE")
        );
    }
}