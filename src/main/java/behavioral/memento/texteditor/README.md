# Text Editor - Memento Pattern Implementation

## Overview

This package demonstrates the **Memento Pattern** through a text editor application that supports undo/redo functionality. The Memento pattern allows capturing and restoring an object's internal state without violating encapsulation principles.

## Pattern Components

### 1. Originator: `TextEditor`

-   **Purpose**: The object whose state needs to be saved and restored
-   **Responsibilities**:
    -   Manages text content, cursor position, and file information
    -   Creates mementos containing its current state
    -   Restores its state from provided mementos
    -   Provides text editing operations (type, delete, cursor movement)

### 2. Memento: `TextMemento`

-   **Purpose**: Stores the state of the TextEditor
-   **Characteristics**:
    -   Immutable object that preserves state integrity
    -   Package-private accessors to restrict access to the originator
    -   Contains metadata like timestamp and snapshot ID
    -   Provides preview and display methods for user interface

### 3. Caretaker: `EditorHistory`

-   **Purpose**: Manages collections of mementos and orchestrates undo/redo operations
-   **Responsibilities**:
    -   Maintains history of editor states
    -   Implements undo/redo functionality with position tracking
    -   Manages maximum history size to prevent memory issues
    -   Provides history navigation and display capabilities

## Key Features

### State Management

-   **Complete State Capture**: Saves text content, cursor position, and timestamp
-   **Immutable Mementos**: Ensures state integrity and prevents tampering
-   **Metadata Tracking**: Each memento includes creation time and unique identifier

### Undo/Redo Functionality

-   **Multi-level Undo**: Navigate backward through editing history
-   **Redo Support**: Move forward through previously undone changes
-   **History Branching**: New edits clear forward history (standard behavior)
-   **Boundary Checking**: Graceful handling of undo/redo limits

### Memory Management

-   **Configurable History Size**: Limit maximum number of stored states
-   **Automatic Cleanup**: Removes oldest states when limit is exceeded
-   **Efficient Storage**: Only stores necessary state information

## Usage Example

```java
// Create editor and history manager
TextEditor editor = new TextEditor("document.txt");
EditorHistory history = new EditorHistory(10);

// Make changes and save states
editor.type("Hello World!");
history.save(editor.save());

editor.type(" Welcome to the demo.");
history.save(editor.save());

// Undo last change
TextMemento previousState = history.undo();
if (previousState != null) {
    editor.restore(previousState);
}

// Redo the change
TextMemento nextState = history.redo();
if (nextState != null) {
    editor.restore(nextState);
}
```

## Design Benefits

### 1. **Encapsulation**

-   Memento pattern preserves object encapsulation
-   Internal state details remain hidden from caretaker
-   Clean separation between state management and business logic

### 2. **Flexibility**

-   Easy to add new state information to mementos
-   Configurable history management policies
-   Support for different restoration strategies

### 3. **User Experience**

-   Familiar undo/redo behavior for users
-   Visual feedback through history display
-   Robust error handling for edge cases

### 4. **Memory Efficiency**

-   Controlled memory usage through history limits
-   Immutable mementos prevent unnecessary copying
-   Lazy evaluation of state information

## Real-World Applications

This implementation pattern is commonly used in:

-   **Text Editors**: Microsoft Word, Google Docs, VS Code
-   **Image Editors**: Photoshop, GIMP with layer history
-   **IDEs**: Code editors with unlimited undo functionality
-   **CAD Software**: Engineering applications with complex state
-   **Game Development**: Save/load game states

## Testing the Implementation

Run the `ZMain` class to see:

1. Basic text editing operations
2. State saving and restoration
3. Undo/redo functionality
4. History management and display
5. Edge case handling
6. Memory management with size limits

The demo showcases realistic editing scenarios and demonstrates how the Memento pattern provides a clean, maintainable solution for state management in interactive applications.
