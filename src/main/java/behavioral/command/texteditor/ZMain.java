package behavioral.command.texteditor;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create receiver
		TextEditor editor = new TextEditor();

		// Create invoker
		Toolbar toolbar = new Toolbar();

		// Create commands
		TextCommand typeA = new TypeCommand(editor, 'A');
		TextCommand typeB = new TypeCommand(editor, 'B');
		TextCommand delete = new DeleteCommand(editor);

		// Execute commands
		System.out.println("Executing commands:");
		toolbar.executeCommand(typeA); // Type 'A'
		toolbar.executeCommand(typeB); // Type 'B'
		toolbar.executeCommand(delete); // Delete 'B'

		// Undo commands
		System.out.println("\nUndoing commands:");
		toolbar.undo(); // Undo delete (re-type 'B')
		toolbar.undo(); // Undo type 'B' (delete 'B')
		toolbar.undo(); // Undo type 'A' (delete 'A')
		toolbar.undo(); // Nothing to undo
		
//		== Output:
//		Executing commands:
//		Typed: A, Text: A
//		Typed: B, Text: AB
//		Deleted: B, Text: A
//
//		Undoing commands:
//		Typed: B, Text: AB
//		Deleted: B, Text: A
//		Deleted: A, Text: 
//		Nothing to undo
	}
}
