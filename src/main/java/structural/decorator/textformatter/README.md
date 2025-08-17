# Text Formatter - Decorator Pattern Example

## Overview

This example demonstrates the **Decorator Pattern** using a text formatting system. The Decorator pattern allows you to dynamically add formatting capabilities to text objects without altering their structure, following the principle of composition over inheritance.

## Problem Statement

In text processing applications, you often need to apply various formatting options to text:
- Bold, italic, underline formatting
- Color styling
- Text transformations (uppercase, lowercase)
- Multiple formatting combinations

Without the Decorator pattern, you would need to create numerous subclasses for every possible combination of formatting options, leading to a class explosion problem.

## Solution

The Decorator pattern solves this by:
1. **Component Interface (`Text`)**: Defines the basic text operations
2. **Concrete Component (`PlainText`)**: Implements basic text functionality
3. **Base Decorator (`TextDecorator`)**: Abstract decorator that maintains a reference to a Text object
4. **Concrete Decorators**: Specific formatting implementations that extend functionality

## Class Structure

```
Text (Interface)
├── PlainText (ConcreteComponent)
└── TextDecorator (Decorator)
    ├── BoldDecorator
    ├── ItalicDecorator
    ├── UnderlineDecorator
    ├── ColorDecorator
    └── UpperCaseDecorator
```

## Key Components

### 1. Text Interface
```java
public interface Text {
    String getContent(); // Get formatted text content
    int getLength(); // Get original text length
}
```

### 2. PlainText (Concrete Component)
- Basic implementation that holds raw text content
- Provides foundation for all decorators

### 3. TextDecorator (Base Decorator)
- Abstract class that implements Text interface
- Maintains reference to decorated Text object
- Delegates basic operations to the wrapped object

### 4. Concrete Decorators
- **BoldDecorator**: Wraps text with `<b>` tags
- **ItalicDecorator**: Wraps text with `<i>` tags
- **UnderlineDecorator**: Wraps text with `<u>` tags
- **ColorDecorator**: Adds color styling with `<span>` tags
- **UpperCaseDecorator**: Converts text to uppercase

## Usage Examples

```java
// Basic text
Text plainText = new PlainText("Hello World");

// Single formatting
Text boldText = new BoldDecorator(plainText);

// Multiple formatting (nested decorators)
Text complexText = new UnderlineDecorator(
    new ItalicDecorator(
        new BoldDecorator(plainText)
    )
);

// Parameterized decorators
Text coloredText = new ColorDecorator(plainText, "red");
```

## Benefits

1. **Flexibility**: Add or remove formatting at runtime
2. **Composition**: Combine multiple decorators in any order
3. **Open/Closed Principle**: Add new formatting without modifying existing code
4. **Single Responsibility**: Each decorator handles one specific formatting concern
5. **Reusability**: Decorators can be applied to any Text object

## Real-World Applications

- **Text Editors**: Microsoft Word, Google Docs formatting
- **Web Development**: CSS class combinations, inline styling
- **Rich Text Libraries**: HTML/Markdown processors
- **Email Clients**: Message formatting options
- **Documentation Tools**: Syntax highlighting, code formatting

## Design Considerations

1. **Order Matters**: Different decorator orders produce different results
2. **Performance**: Multiple nested decorators may impact performance
3. **Debugging**: Deep decorator chains can be harder to debug
4. **Memory**: Each decorator creates a new object wrapper

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="structural.decorator.textformatter.ZMain"

# Or using java directly
java -cp target/classes structural.decorator.textformatter.ZMain
```

## Sample Output

```
Plain Text: Hello World
Length: 11

Bold Text: <b>Hello World</b>
Original Length: 11

Bold + Italic Text: <i><b>Hello World</b></i>
Original Length: 11

Complex Formatted Text: <B><I><SPAN STYLE="COLOR:BLUE">HELLO WORLD</SPAN></I></B>
Original Length: 11
```

## Extension Points

This example can be extended with additional decorators:
- **FontSizeDecorator**: Change text size
- **BackgroundColorDecorator**: Add background colors
- **LinkDecorator**: Convert text to hyperlinks
- **EncryptionDecorator**: Add security formatting
- **TimestampDecorator**: Add timestamps to text

The Decorator pattern's flexibility makes it easy to add new formatting options without disrupting existing functionality.