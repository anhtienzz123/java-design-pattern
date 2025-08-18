# Visitor Pattern: Document Processing System

## Overview

This example demonstrates the **Visitor Pattern** using a document processing system that can perform various operations on different types of document elements (paragraphs, headings, tables, images). The Visitor pattern allows you to define new operations on a set of objects without changing their classes.

## Problem Solved

When working with complex document structures, you often need to perform various operations like:
- **Word counting** for different element types
- **Validation** of document structure and content
- **Export to different formats** (HTML, PDF, etc.)
- **Statistical analysis** of document content
- **Content transformation** and processing

Without the Visitor pattern, you would need to:
- Add methods to each element class for every new operation
- Violate the Single Responsibility Principle
- Create tightly coupled code
- Make the element classes bloated with unrelated functionality

The Visitor pattern solves this by:
- **Separating operations from the object structure**
- **Allowing new operations without modifying existing classes**
- **Grouping related operations in visitor classes**
- **Following the Open/Closed Principle**

## Structure

### Element Hierarchy
```
DocumentElement (interface)
├── Paragraph
├── Heading  
├── Table
└── Image
```

### Visitor Interface
```java
interface DocumentVisitor {
    void visit(Paragraph paragraph);
    void visit(Heading heading);
    void visit(Table table);
    void visit(Image image);
}
```

### Concrete Visitors
- **`WordCountVisitor`**: Counts words and elements throughout the document
- **`ValidationVisitor`**: Validates document structure, content, and formatting
- **`StatisticsVisitor`**: Gathers comprehensive statistics about document composition
- **`HTMLExportVisitor`**: Exports the document to HTML format

### Document Structure
- **`Document`**: Container class that holds document elements and metadata
- **`DocumentElement`**: Base interface for all document elements
- **Element Classes**: `Paragraph`, `Heading`, `Table`, `Image` with specific properties

## Key Benefits

1. **Separation of Concerns**: Operations are separated from data structures
2. **Easy Extension**: Add new operations without modifying existing classes
3. **Single Responsibility**: Each visitor handles one specific operation
4. **Type Safety**: Compile-time checking of visitor operations
5. **Centralized Logic**: Related operations are grouped in one visitor class
6. **Open/Closed Principle**: Open for extension, closed for modification

## Element Types and Properties

### Paragraph
- Text content with word counting
- Alignment settings (left, center, right, justify)
- Character and word statistics

### Heading
- Hierarchical levels (1-6)
- Numbered/unnumbered options
- Text content with validation

### Table
- Headers and data rows
- Border styling options
- Cell count and structure validation
- Dynamic column/row analysis

### Image
- Dimensions and format information
- Caption support
- File reference and metadata
- Pixel count calculations

## Usage Example

```java
// Create a document
Document document = new Document("My Document", "Author Name");

// Add various elements
document.addElement(new Heading("Introduction", 1, true));
document.addElement(new Paragraph("Welcome to our guide...", "left"));
document.addElement(new Table(headers, rows, true));
document.addElement(new Image("diagram.png", "System Architecture", 800, 600, "PNG"));

// Apply different visitors
WordCountVisitor wordCounter = new WordCountVisitor();
document.accept(wordCounter);
wordCounter.printSummary();

ValidationVisitor validator = new ValidationVisitor();
document.accept(validator);
validator.printValidationResults();

HTMLExportVisitor htmlExporter = new HTMLExportVisitor();
document.accept(htmlExporter);
String html = htmlExporter.getHTML();
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.visitor.documentprocessing.ZMain"

# Or using java directly
java -cp target/classes behavioral.visitor.documentprocessing.ZMain
```

## Expected Output

The example demonstrates:

### 1. Word Count Analysis
- Total word count across all elements
- Element counts by type
- Detailed breakdown of content statistics

### 2. Document Validation
- **Errors**: Critical issues that must be fixed
  - Empty headings or invalid heading levels
  - Invalid paragraph alignments
  - Table structure inconsistencies
  - Images with invalid dimensions
- **Warnings**: Issues that should be reviewed
  - Empty paragraphs or very long content
  - Missing image captions
  - Unsupported image formats
  - Tables without headers

### 3. Document Statistics
- Comprehensive analysis including:
  - Total elements, words, characters
  - Element distribution percentages
  - Heading level breakdown
  - Format-specific statistics
  - Image pixel counts

### 4. HTML Export
- Complete HTML document generation
- Proper HTML escaping for security
- Semantic markup for accessibility
- Styled tables and images
- Structured heading hierarchy

## Advanced Features

### Validation Rules
- **Heading Level Validation**: Ensures proper heading hierarchy (H1 → H2 → H3, etc.)
- **Table Structure Validation**: Verifies consistent column counts across rows
- **Image Format Validation**: Checks for supported image formats
- **Content Length Validation**: Warns about excessively long content

### HTML Export Features
- **Semantic HTML**: Proper use of HTML5 semantic elements
- **XSS Prevention**: HTML escaping for all user content
- **Responsive Images**: Width and height attributes for layout stability
- **Accessibility**: Alt text and figure captions for images

### Statistics Collection
- **Performance Metrics**: Character and pixel counts
- **Content Distribution**: Percentage breakdown of element types
- **Format Analysis**: Usage patterns for alignment, borders, numbering

## When to Use This Pattern

- You have a **stable object structure** but need to add new operations frequently
- You want to **avoid polluting** element classes with unrelated operations
- You need to **perform multiple different operations** on the same set of objects
- You want to **group related operations** together
- You're building a **framework** where users can define custom operations

## Real-World Applications

- **Document Processing**: Word processors, PDF generators, markup converters
- **Compiler Design**: Syntax tree traversal, code generation, optimization
- **Game Development**: Scene graph operations, rendering, collision detection
- **Data Analysis**: Tree/graph traversal, statistical analysis, reporting
- **GUI Frameworks**: Component rendering, event handling, layout calculation
- **Database Systems**: Query optimization, index building, statistics collection

## Extension Example

To add a new operation (e.g., PDF export), you would:

1. **Create a new visitor**:
```java
public class PDFExportVisitor implements DocumentVisitor {
    // Implement visit methods for PDF generation
}
```

2. **No changes needed** to existing classes:
- Document elements remain unchanged
- Existing visitors continue to work
- No modification to the DocumentVisitor interface

This demonstrates the **Open/Closed Principle** in action: the system is open for extension but closed for modification.