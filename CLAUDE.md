# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a comprehensive Java Design Patterns educational repository that demonstrates 23+ design patterns with practical implementations. The project uses Java 21+ features and follows SOLID, DRY, KISS, and YAGNI principles with OWASP security best practices.

## Build and Development Commands

### Maven Commands
- **Compile**: `mvn compile`
- **Clean and compile**: `mvn clean compile`
- **Run a specific main class**: `mvn exec:java -Dexec.mainClass="behavioral.chainofresponsibility.approvalsystem.ZMain"`
- **Package**: `mvn package`

### Running Examples
Each pattern implementation has a `ZMain.java` class that demonstrates the pattern. Examples:
- Chain of Responsibility: `java -cp target/classes behavioral.chainofresponsibility.approvalsystem.ZMain`
- Boolean Logic Interpreter: `java -cp target/classes behavioral.interpreter.booleanlogic.ZMain`
- Singleton patterns: `java -cp target/classes creational.singleton.loggersystem.ZMain`

### Testing
Some patterns include dedicated test classes (e.g., `BooleanLogicInterpreterTest.java`):
- Compile: `javac src/main/java/behavioral/interpreter/booleanlogic/*.java`
- Run tests: `java -cp target/classes behavioral.interpreter.booleanlogic.BooleanLogicInterpreterTest`

## Code Architecture

### Project Structure
```
src/main/java/
├── behavioral/          # Behavioral design patterns
│   ├── chainofresponsibility/
│   ├── command/
│   ├── interpreter/
│   ├── iterator/
│   ├── mediator/
│   ├── memento/
│   ├── observer/
│   ├── state/
│   ├── strategy/
│   ├── templatemethod/
│   └── visitor/
├── creational/          # Creational design patterns
│   ├── abstractfactory/
│   ├── builder/
│   ├── factorymethod/
│   ├── prototype/
│   ├── simplefactory/
│   └── singleton/
├── structural/          # Structural design patterns
│   ├── adapter/
│   ├── bridge/
│   ├── composite/
│   ├── decorator/
│   ├── facade/
│   ├── flyweight/
│   └── proxy/
└── other/              # Additional patterns
    ├── combinator/
    ├── fluentinterface/
    └── servicelocator/
```

### Pattern Implementation Standards
- Each pattern is implemented in its own package with real-world scenarios
- Main demonstration classes are named `ZMain.java`
- Complex patterns include comprehensive README.md documentation
- Some patterns include dedicated test suites
- Thread-safe implementations where applicable (especially Singleton patterns)

### Key Implementation Details
- **Behavioral Patterns**: Focus on communication between objects and assignment of responsibilities
  - Chain of Responsibility: Multi-level approval systems with configurable chains
  - Interpreter: Boolean logic evaluation with comprehensive test coverage
  - Memento: Game state and text editor undo/redo functionality
  
- **Creational Patterns**: Deal with object creation mechanisms
  - Singleton: Thread-safe implementations for logging, caching, configuration, and database connection pooling
  - Abstract Factory: Database connectivity and GUI application factories
  - Builder: Car configuration with fluent interface
  
- **Structural Patterns**: Deal with object composition and relationships
  - Adapter: Database and media player integration
  - Composite: File system and organizational hierarchy modeling
  - Decorator: Coffee shop customization system

### Development Guidelines (from .cursor/rules)
- You are an experienced Senior Java Developer.
- Use Java 21+ features (records, sealed classes, pattern matching, virtual threads)
- Follow SOLID, DRY, KISS, and YAGNI principles
- Adhere to OWASP security best practices
- Break tasks into smallest units and solve step-by-step
- Maintain thread safety for shared resources

## Adding New Patterns
When implementing new design patterns:
1. Create appropriate package under behavioral/, creational/, structural/, or other/
2. Include a `ZMain.java` demonstration class
3. Add comprehensive README.md if the pattern is complex
4. Consider thread safety implications
5. Follow existing naming conventions and project structure
6. Include practical real-world use cases in the implementation