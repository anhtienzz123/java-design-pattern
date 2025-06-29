package behavioral.memento.texteditor;

import java.util.ArrayList;
import java.util.List;

/**
 * EditorHistory - Caretaker class
 * 
 * This class manages the history of TextMemento objects, providing
 * undo/redo functionality and history management.
 */
public class EditorHistory {
    private List<TextMemento> history;
    private int currentPosition;
    private final int maxHistorySize;
    
    public EditorHistory() {
        this(50); // Default maximum of 50 states
    }
    
    public EditorHistory(int maxHistorySize) {
        this.history = new ArrayList<>();
        this.currentPosition = -1;
        this.maxHistorySize = maxHistorySize;
    }
    
    /**
     * Saves a new memento to the history
     */
    public void save(TextMemento memento) {
        // Remove any redo states if we're not at the end
        if (currentPosition < history.size() - 1) {
            history = history.subList(0, currentPosition + 1);
        }
        
        // Add the new memento
        history.add(memento);
        currentPosition++;
        
        // Maintain maximum history size
        if (history.size() > maxHistorySize) {
            history.remove(0);
            currentPosition--;
        }
        
        System.out.println("State saved to history. Position: " + (currentPosition + 1) + "/" + history.size());
    }
    
    /**
     * Gets the previous memento for undo operation
     */
    public TextMemento undo() {
        if (canUndo()) {
            currentPosition--;
            System.out.println("Undo: Moving to position " + (currentPosition + 1) + "/" + history.size());
            return history.get(currentPosition);
        }
        System.out.println("Cannot undo: No previous states available");
        return null;
    }
    
    /**
     * Gets the next memento for redo operation
     */
    public TextMemento redo() {
        if (canRedo()) {
            currentPosition++;
            System.out.println("Redo: Moving to position " + (currentPosition + 1) + "/" + history.size());
            return history.get(currentPosition);
        }
        System.out.println("Cannot redo: No next states available");
        return null;
    }
    
    /**
     * Checks if undo operation is possible
     */
    public boolean canUndo() {
        return currentPosition > 0;
    }
    
    /**
     * Checks if redo operation is possible
     */
    public boolean canRedo() {
        return currentPosition < history.size() - 1;
    }
    
    /**
     * Gets the current memento without changing position
     */
    public TextMemento getCurrent() {
        if (currentPosition >= 0 && currentPosition < history.size()) {
            return history.get(currentPosition);
        }
        return null;
    }
    
    /**
     * Clears the entire history
     */
    public void clearHistory() {
        history.clear();
        currentPosition = -1;
        System.out.println("History cleared");
    }
    
    /**
     * Gets the size of the history
     */
    public int getHistorySize() {
        return history.size();
    }
    
    /**
     * Gets the current position in history
     */
    public int getCurrentPosition() {
        return currentPosition + 1; // 1-based for user display
    }
    
    /**
     * Displays the complete history
     */
    public void displayHistory() {
        System.out.println("\n=== Editor History ===");
        if (history.isEmpty()) {
            System.out.println("No history available");
            return;
        }
        
        for (int i = 0; i < history.size(); i++) {
            String marker = (i == currentPosition) ? " -> " : "    ";
            TextMemento memento = history.get(i);
            System.out.println(marker + (i + 1) + ". " + memento.getMetadata());
            System.out.println("       Preview: '" + memento.getContentPreview() + "'");
        }
        System.out.println("===================\n");
    }
    
    /**
     * Gets a specific memento by index (for advanced operations)
     */
    public TextMemento getMemento(int index) {
        if (index >= 0 && index < history.size()) {
            return history.get(index);
        }
        return null;
    }
} 