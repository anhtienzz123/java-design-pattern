package behavioral.memento.texteditor;

/**
 * TextMemento - Memento class
 * 
 * This class stores the state of a TextEditor object. It's immutable and 
 * provides controlled access to the saved state information.
 */
public class TextMemento {
    private final String content;
    private final int cursorPosition;
    private final long timestamp;
    private final String snapshotId;
    
    /**
     * Constructor for creating a memento with editor state
     */
    public TextMemento(String content, int cursorPosition, long timestamp) {
        this.content = content;
        this.cursorPosition = cursorPosition;
        this.timestamp = timestamp;
        this.snapshotId = "SNAPSHOT_" + timestamp;
    }
    
    /**
     * Gets the saved content
     * Package-private to restrict access to originator
     */
    String getContent() {
        return content;
    }
    
    /**
     * Gets the saved cursor position
     * Package-private to restrict access to originator
     */
    int getCursorPosition() {
        return cursorPosition;
    }
    
    /**
     * Gets the timestamp when this memento was created
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Gets a unique identifier for this snapshot
     */
    public String getSnapshotId() {
        return snapshotId;
    }
    
    /**
     * Gets metadata about the saved state (for display purposes)
     */
    public String getMetadata() {
        return String.format("Snapshot: %s | Content Length: %d | Cursor: %d | Time: %s", 
                           snapshotId, 
                           content.length(), 
                           cursorPosition, 
                           new java.util.Date(timestamp).toString());
    }
    
    /**
     * Gets a preview of the content (first 50 characters)
     */
    public String getContentPreview() {
        if (content.length() <= 50) {
            return content;
        }
        return content.substring(0, 47) + "...";
    }
    
    @Override
    public String toString() {
        return String.format("TextMemento{id='%s', contentLength=%d, cursor=%d, time=%s}", 
                           snapshotId, content.length(), cursorPosition, new java.util.Date(timestamp));
    }
} 