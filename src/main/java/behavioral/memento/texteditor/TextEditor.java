package behavioral.memento.texteditor;

/**
 * TextEditor - Originator class
 * 
 * This class represents a simple text editor that can save and restore its state.
 * It creates TextMemento objects to save its current state and can restore from them.
 */
public class TextEditor {
    private StringBuilder content;
    private String fileName;
    private int cursorPosition;
    
    public TextEditor(String fileName) {
        this.fileName = fileName;
        this.content = new StringBuilder();
        this.cursorPosition = 0;
    }
    
    /**
     * Types text at the current cursor position
     */
    public void type(String text) {
        content.insert(cursorPosition, text);
        cursorPosition += text.length();
        System.out.println("Typed: '" + text + "' | Content: '" + content.toString() + "'");
    }
    
    /**
     * Deletes a specified number of characters from the current position
     */
    public void delete(int numChars) {
        if (cursorPosition >= numChars) {
            content.delete(cursorPosition - numChars, cursorPosition);
            cursorPosition -= numChars;
            System.out.println("Deleted " + numChars + " characters | Content: '" + content.toString() + "'");
        }
    }
    
    /**
     * Moves the cursor to a specific position
     */
    public void setCursorPosition(int position) {
        if (position >= 0 && position <= content.length()) {
            this.cursorPosition = position;
            System.out.println("Cursor moved to position: " + position);
        }
    }
    
    /**
     * Creates a memento containing the current state of the text editor
     */
    public TextMemento save() {
        System.out.println("Saving current state...");
        return new TextMemento(content.toString(), cursorPosition, System.currentTimeMillis());
    }
    
    /**
     * Restores the text editor state from a memento
     */
    public void restore(TextMemento memento) {
        this.content = new StringBuilder(memento.getContent());
        this.cursorPosition = memento.getCursorPosition();
        System.out.println("Restored state from " + new java.util.Date(memento.getTimestamp()));
        System.out.println("Content: '" + content.toString() + "' | Cursor: " + cursorPosition);
    }
    
    /**
     * Gets the current content of the editor
     */
    public String getContent() {
        return content.toString();
    }
    
    /**
     * Gets the current cursor position
     */
    public int getCursorPosition() {
        return cursorPosition;
    }
    
    /**
     * Gets the file name
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * Displays the current state of the editor
     */
    public void displayState() {
        System.out.println("=== Editor State ===");
        System.out.println("File: " + fileName);
        System.out.println("Content: '" + content.toString() + "'");
        System.out.println("Cursor Position: " + cursorPosition);
        System.out.println("Content Length: " + content.length());
        System.out.println("==================");
    }
} 