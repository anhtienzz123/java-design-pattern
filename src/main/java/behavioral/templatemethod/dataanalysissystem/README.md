# Template Method Pattern: Data Analysis System

## Overview

This example demonstrates the **Template Method Pattern** using a data analysis system that processes different file formats (CSV, JSON, XML). The Template Method pattern defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the algorithm's structure.

## Problem Solved

When analyzing data from different sources and formats, you often need to follow the same general process:
1. Validate input
2. Load data
3. Parse the format
4. Clean the data
5. Perform analysis
6. Generate reports
7. Export results

However, the specific implementation of each step varies depending on the data format. The Template Method pattern allows you to:
- **Enforce a consistent algorithm structure** across all data analyzers
- **Prevent subclasses from changing the overall workflow**
- **Allow format-specific customization** where needed
- **Reuse common functionality** like validation and reporting
- **Maintain code consistency** across different data processors

## Structure

### Abstract Template Class
- **`DataAnalysisTemplate`**: Defines the template method `analyzeData()` and the algorithm skeleton

### Template Method
```java
public final void analyzeData(String filePath) {
    validateInput(filePath);           // Concrete method
    String rawData = loadData(filePath);        // Abstract method
    List<Map<String, Object>> parsedData = parseData(rawData);  // Abstract method
    Map<String, Object> cleanedData = cleanData(parsedData);    // Hook method
    Map<String, Object> results = performAnalysis(cleanedData); // Abstract method
    generateReport(results);           // Hook method
    if (shouldExportResults()) {       // Hook method
        exportResults(results);        // Hook method
    }
}
```

### Concrete Implementations
- **`CSVDataAnalyzer`**: Processes employee data from CSV format
- **`JSONDataAnalyzer`**: Analyzes product inventory from JSON format  
- **`XMLDataAnalyzer`**: Handles customer data from XML format

### Method Types

1. **Template Method** (`analyzeData`): 
   - Final method that defines the algorithm structure
   - Cannot be overridden by subclasses

2. **Abstract Methods** (must be implemented):
   - `loadData()`: Format-specific data loading
   - `parseData()`: Format-specific parsing logic
   - `performAnalysis()`: Format-specific analysis calculations

3. **Hook Methods** (optional to override):
   - `cleanData()`: Default cleaning with option to customize
   - `generateReport()`: Default reporting with option to extend
   - `shouldExportResults()`: Control export behavior
   - `exportResults()`: Custom export logic

4. **Concrete Methods** (shared implementation):
   - `validateInput()`: Common input validation

## Key Benefits

1. **Algorithm Structure Consistency**: All analyzers follow the same workflow
2. **Code Reuse**: Common functionality implemented once in the base class  
3. **Flexibility**: Subclasses can customize specific steps as needed
4. **Maintainability**: Changes to the overall algorithm only need to be made in one place
5. **Template Invariance**: The algorithm structure cannot be accidentally modified
6. **Hollywood Principle**: "Don't call us, we'll call you" - the template controls the flow

## Usage Example

```java
// CSV Analysis
DataAnalysisTemplate csvAnalyzer = new CSVDataAnalyzer();
csvAnalyzer.analyzeData("employees.csv");

// JSON Analysis  
DataAnalysisTemplate jsonAnalyzer = new JSONDataAnalyzer();
jsonAnalyzer.analyzeData("products.json");

// XML Analysis
DataAnalysisTemplate xmlAnalyzer = new XMLDataAnalyzer();
xmlAnalyzer.analyzeData("customers.xml");
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.templatemethod.dataanalysissystem.ZMain"

# Or using java directly
java -cp target/classes behavioral.templatemethod.dataanalysissystem.ZMain
```

## Expected Output

The example demonstrates:
- **CSV Analysis**: Employee data with salary and department statistics
- **JSON Analysis**: Product inventory with category and value analysis
- **XML Analysis**: Customer data with regional revenue breakdown
- **Template Structure**: Shows the invariant algorithm steps
- **Error Handling**: Validates input parameters

## Format-Specific Features

### CSV Analyzer
- Parses comma-separated employee data
- Calculates salary and age statistics
- Groups employees by department
- Exports to spreadsheet format

### JSON Analyzer  
- Parses product inventory data
- Calculates inventory values and stock levels
- Advanced data cleaning (removes invalid entries)
- Groups products by category

### XML Analyzer
- Parses customer relationship data
- Calculates customer lifetime value and regional statistics
- Email validation during data cleaning
- Identifies top customers and regional performance

## When to Use This Pattern

- You have multiple classes that follow similar algorithms with different implementations
- You want to prevent subclasses from changing the algorithm structure
- You need to reuse common parts of an algorithm across multiple classes
- You want to control which parts of an algorithm can be customized
- You're implementing a framework where users can plug in specific behaviors

## Real-World Applications

- **Data Processing Pipelines**: ETL processes for different data sources
- **Report Generation**: Different report formats following the same structure
- **Testing Frameworks**: Test execution with customizable setup/teardown
- **Game Development**: AI behavior trees with different character strategies
- **Web Frameworks**: Request processing pipelines with customizable handlers
- **Build Systems**: Compilation processes for different programming languages