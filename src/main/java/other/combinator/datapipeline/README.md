# Combinator Pattern - Data Pipeline Processing System

## Overview

This example demonstrates the **Combinator Pattern** through a comprehensive data pipeline processing system. The pattern enables building complex data processing workflows by combining small, focused, reusable functions (combinators) that can be composed together to create sophisticated data transformation and filtering pipelines.

## Problem Statement

In data processing applications, you often need to:

- **Filter data** based on various criteria (category, value ranges, timestamps, metadata)
- **Transform data** through multiple steps (calculations, formatting, enrichment)
- **Validate data** against business rules and quality standards
- **Handle errors** gracefully with fallback strategies
- **Process data in batches** for performance and resource management
- **Compose complex workflows** from simpler building blocks

Without the Combinator pattern, you would end up with:

```java
// Without Combinator - monolithic, hard-to-test, inflexible code
public class DataProcessor {
    public List<DataRecord> processData(List<DataRecord> records) {
        List<DataRecord> result = new ArrayList<>();
        
        for (DataRecord record : records) {
            // Complex nested conditions and transformations
            if (record.getCategory().equals("SALES") && 
                record.getValue() > 50 && 
                record.getStatus().equals("ACTIVE") && 
                record.isRecent(24)) {
                
                // Multiple transformation steps mixed with business logic
                DataRecord transformed = record
                    .withValue(record.getValue() * 1.08) // Tax
                    .withCategory(record.getCategory().toUpperCase())
                    .withMetadata("processed", true)
                    .withMetadata("tax_applied", 0.08);
                
                // More validation and processing...
                if (transformed.getValue() > 0 && transformed.getValue() < 10000) {
                    result.add(transformed);
                }
            }
        }
        return result;
    }
}
```

## Solution: Combinator Pattern

The Combinator pattern breaks down complex operations into small, composable functions:

```java
// With Combinator - composable, reusable, testable
DataProcessor salesPipeline = 
    DataProcessor.filter(
        DataFilter.byCategory("SALES")
            .and(DataFilter.byValueGreaterThan(50))
            .and(DataFilter.byStatus("ACTIVE"))
            .and(DataFilter.byRecentHours(24))
    )
    .then(DataProcessor.transform(
        DataTransformer.applySalesTax(0.08)
            .then(DataTransformer.uppercaseCategory())
            .then(DataTransformer.addProcessingTimestamp())
    ))
    .then(DataProcessor.validate(
        DataFilter.byValueBetween(0, 10000), 
        "Value must be within valid range"
    ));
```

## Implementation Structure

### Core Components

1. **DataRecord**
   - Immutable data structure representing records in the pipeline
   - Builder methods for creating modified copies
   - Rich metadata support for processing context

2. **PipelineResult**
   - Container for processing results including success/failure status
   - Error collection and aggregation
   - Processing time tracking and performance metrics

3. **DataFilter (Predicate Combinator)**
   - Composable filtering logic with `and()`, `or()`, `not()` operations
   - Static factory methods for common filter types
   - Applies to collections with automatic result handling

4. **DataTransformer (Function Combinator)**
   - Chainable transformation logic with `then()` and `andThen()` operations
   - Static factory methods for common transformations
   - Error handling support for robust pipelines

5. **DataProcessor (Pipeline Combinator)**
   - High-level pipeline orchestration combining filters, transformers, and validators
   - Sequential (`then()`), parallel (`parallel()`), and fallback (`orElse()`) composition
   - Cross-cutting concerns like monitoring, error recovery, and batch processing

### Class Diagram

```
DataRecord (Immutable Data)
├── Builder methods for transformations
└── Rich metadata support

PipelineResult (Result Container)
├── Success/failure tracking
├── Error aggregation
└── Performance metrics

DataFilter extends Predicate<DataRecord>
├── and() / or() / not() - Logical combinators
├── Static factory methods (byCategory, byValue, etc.)
└── apply(List<DataRecord>) - Collection processing

DataTransformer extends Function<DataRecord, DataRecord>
├── then() / andThen() - Sequential chaining
├── Static factory methods (multiplyValue, addMetadata, etc.)
└── apply(List<DataRecord>) - Collection processing

DataProcessor (Pipeline Orchestrator)
├── then() / parallel() / orElse() - Composition operators
├── withErrorRecovery() / withMonitoring() - Cross-cutting concerns
└── process(List<DataRecord>) - Pipeline execution
```

## Key Features Demonstrated

### 1. Filter Combinators
```java
// Logical combinations
DataFilter highValueSales = DataFilter.byCategory("SALES")
    .and(DataFilter.byValueGreaterThan(100))
    .and(DataFilter.byRecentHours(24));

DataFilter businessData = DataFilter.byCategoryIn("SALES", "MARKETING", "FINANCE")
    .or(DataFilter.hasMetadata("priority"));

DataFilter activeRecords = DataFilter.byStatus("INACTIVE").not();
```

### 2. Transformation Combinators
```java
// Sequential transformations
DataTransformer enrichment = DataTransformer.multiplyValue(1.1)
    .then(DataTransformer.roundValue(2))
    .then(DataTransformer.addProcessingTimestamp())
    .then(DataTransformer.addValueCategory());

// Conditional transformations
DataTransformer salesTax = DataTransformer.conditionalTransform(
    DataFilter.byCategory("SALES"),
    DataTransformer.applySalesTax(0.08)
);
```

### 3. Pipeline Combinators
```java
// Sequential pipeline
DataProcessor qualityPipeline = DataProcessor
    .filter(DataFilter.byValueGreaterThan(0))
    .then(DataProcessor.transform(DataTransformer.standardizeRecord()))
    .then(DataProcessor.validate(DataFilter.byStatus("ACTIVE"), "Must be active"));

// Error recovery pipeline
DataProcessor robustPipeline = DataProcessor
    .withErrorRecovery(mainProcessor, fallbackProcessor)
    .then(DataProcessor.withMonitoring(processor, "Quality Check"));
```

### 4. Business Logic Composition
```java
// Complex business rules
DataProcessor premiumCustomerPipeline = DataProcessor
    .filter(DataFilter.byValueGreaterThan(1000)
            .and(DataFilter.hasMetadataValue("tier", "PREMIUM")))
    .then(DataProcessor.transform(
        DataTransformer.applyDiscountToHighValue(1000, 0.15)
            .then(DataTransformer.addMetadata("discount_applied", true))
    ))
    .then(DataProcessor.batch(50, DataProcessor.enrichmentPipeline()));
```

## Benefits Demonstrated

### ✅ Composability
- Small, focused functions combine to create complex workflows
- Easy to build new pipelines from existing components
- Natural expression of data processing logic

### ✅ Reusability
- Individual filters, transformers, and processors can be reused
- Business logic components work across different contexts
- Reduction in code duplication

### ✅ Testability
- Each combinator can be unit tested independently
- Complex pipelines built from well-tested components
- Easy to mock and stub individual processing steps

### ✅ Readability
- Declarative expression of data processing intent
- Self-documenting pipeline structure
- Clear separation between different types of operations

### ✅ Flexibility
- Easy to modify existing pipelines without affecting others
- Dynamic pipeline composition based on configuration
- A/B testing of different processing strategies

### ✅ Error Handling
- Centralized error handling and recovery strategies
- Graceful degradation with fallback processors
- Detailed error reporting and diagnostics

## Real-World Scenarios Demonstrated

### ETL Data Processing
```java
DataProcessor etlPipeline = DataProcessor
    .filter(DataFilter.isValidBusinessRecord()) // Extract
    .then(DataProcessor.transform(DataTransformer.standardizeRecord())) // Transform
    .then(DataProcessor.batch(1000, DataProcessor.persistToDatabase())); // Load
```

### Data Quality Assurance
```java
DataProcessor qualityPipeline = DataProcessor
    .validate(DataFilter.hasRequiredFields(), "Missing required fields")
    .then(DataProcessor.validate(DataFilter.byValueBetween(0, 1000000), "Invalid range"))
    .then(DataProcessor.transform(DataTransformer.enrichRecord()));
```

### Business Rule Processing
```java
DataProcessor businessRules = DataProcessor
    .when(DataFilter.byCategory("SALES"))
    .then(DataProcessor.transform(DataTransformer.applySalesTax(0.08)))
    .orElse(DataProcessor.transform(DataTransformer.addMetadata("tax_exempt", true)));
```

### Real-time Stream Processing
```java
DataProcessor streamProcessor = DataProcessor
    .filter(DataFilter.byRecentHours(1)) // Only recent data
    .then(DataProcessor.transform(DataTransformer.normalizeAndClassify()))
    .then(DataProcessor.aggregate("Real-time Metrics"));
```

## When to Use This Pattern

✅ **Use when:**
- You need to build complex data processing workflows from simpler components
- Processing logic needs to be flexible and configurable
- You want to maximize code reuse across different data pipelines
- Testing individual processing steps is important
- Business rules change frequently and need to be maintainable
- Performance optimization through batch processing and parallelization is needed

❌ **Avoid when:**
- Processing logic is very simple and unlikely to change
- Performance is extremely critical and function composition overhead is unacceptable
- Team is not familiar with functional programming concepts
- Processing steps are highly coupled and don't benefit from separation

## Running the Example

Execute the main class to see the complete data pipeline demonstration:

```bash
mvn exec:java -Dexec.mainClass="other.combinator.datapipeline.ZMain"
```

The demo demonstrates:
1. **Basic Filter Combinators**: Logical combinations and single/multi-criteria filtering
2. **Transformation Combinators**: Sequential transformations and business logic application
3. **Advanced Processing**: Filter+transform combinations and validation pipelines
4. **Business Logic**: Premium customer processing and conditional transformations
5. **Error Handling**: Recovery strategies and safe processing with error collection
6. **Batch Processing**: Performance optimization and monitoring capabilities
7. **Real-world Pipelines**: Complete enterprise data processing workflows

## Pattern Benefits Summary

- **Modularity**: Small, focused functions that do one thing well
- **Composability**: Complex workflows built from simple building blocks
- **Immutability**: Safe data transformations without side effects
- **Type Safety**: Compile-time guarantees about data flow and transformations
- **Performance**: Optimized batch processing and lazy evaluation opportunities
- **Maintainability**: Easy to understand, modify, and extend processing logic

## Advanced Features

### Error Recovery
- Automatic fallback to alternative processing strategies
- Partial success handling with error collection
- Graceful degradation in distributed processing scenarios

### Performance Optimization
- Batch processing for improved throughput
- Parallel processing capabilities
- Monitoring and metrics collection for performance tuning

### Configuration-Driven Workflows
- Dynamic pipeline construction from configuration
- A/B testing of different processing strategies
- Business user-configurable data processing rules

### Cross-Cutting Concerns
- Monitoring and logging integration
- Security and authorization checks
- Caching and memoization for expensive operations

This implementation showcases how the Combinator pattern enables building sophisticated, maintainable, and testable data processing systems while maintaining code clarity and reusability.