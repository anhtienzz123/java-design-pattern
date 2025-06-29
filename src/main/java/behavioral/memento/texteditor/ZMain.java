package behavioral.memento.texteditor;

/**
 * ZMain - Demonstration of the Memento Pattern with Text Editor
 * 
 * This class demonstrates how the Memento pattern can be used to implement
 * undo/redo functionality in a text editor application.
 */
public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Memento Pattern: Text Editor Demo ===\n");
        
        // Create text editor and history manager
        TextEditor editor = new TextEditor("document.txt");
        EditorHistory history = new EditorHistory(10); // Keep 10 states max
        
        // Initial state
        editor.displayState();
        
        // Save initial empty state
        history.save(editor.save());
        
        System.out.println("\n--- Writing some text ---");
        editor.type("Hello ");
        history.save(editor.save());
        
        editor.type("World!");
        history.save(editor.save());
        
        editor.type(" This is a demo.");
        history.save(editor.save());
        
        // Show current state
        editor.displayState();
        history.displayHistory();
        
        System.out.println("\n--- Testing Undo Operations ---");
        // Undo last change
        TextMemento previousState = history.undo();
        if (previousState != null) {
            editor.restore(previousState);
        }
        editor.displayState();
        
        // Undo another change
        previousState = history.undo();
        if (previousState != null) {
            editor.restore(previousState);
        }
        editor.displayState();
        
        System.out.println("\n--- Testing Redo Operations ---");
        // Redo one change
        TextMemento nextState = history.redo();
        if (nextState != null) {
            editor.restore(nextState);
        }
        editor.displayState();
        
        System.out.println("\n--- Making new changes (this will clear redo history) ---");
        editor.setCursorPosition(6); // Move cursor after "Hello "
        history.save(editor.save());
        
        editor.type("Beautiful ");
        history.save(editor.save());
        
        editor.displayState();
        history.displayHistory();
        
        System.out.println("\n--- Testing Delete and Cursor Operations ---");
        editor.setCursorPosition(editor.getContent().length()); // Move to end
        history.save(editor.save());
        
        editor.delete(5); // Delete " demo"
        history.save(editor.save());
        
        editor.type(" Example!");
        history.save(editor.save());
        
        editor.displayState();
        
        System.out.println("\n--- Multiple Undo Operations ---");
        // Undo several times
        for (int i = 0; i < 3 && history.canUndo(); i++) {
            System.out.println("Undo operation " + (i + 1) + ":");
            TextMemento undoState = history.undo();
            if (undoState != null) {
                editor.restore(undoState);
            }
        }
        
        editor.displayState();
        history.displayHistory();
        
        System.out.println("\n--- Testing Edge Cases ---");
        // Try to undo beyond available history
        System.out.println("Attempting to undo beyond available history:");
        while (history.canUndo()) {
            TextMemento undoState = history.undo();
            if (undoState != null) {
                editor.restore(undoState);
            }
        }
        
        // Try one more undo (should fail gracefully)
        TextMemento failedUndo = history.undo();
        System.out.println("Failed undo result: " + (failedUndo == null ? "null (expected)" : "unexpected state"));
        
        editor.displayState();
        
        System.out.println("\n--- Final History State ---");
        history.displayHistory();
        
        System.out.println("Can undo: " + history.canUndo());
        System.out.println("Can redo: " + history.canRedo());
        System.out.println("History size: " + history.getHistorySize());
        System.out.println("Current position: " + history.getCurrentPosition());
        
        System.out.println("\n=== Text Editor Memento Demo Complete ===");
    }
} 