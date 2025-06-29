package behavioral.command.texteditor;

// Concrete Command: Encapsulates deleting a character
public class DeleteCommand implements TextCommand {
	private TextEditor editor;
	private char deletedChar; // Store for undo

	public DeleteCommand(TextEditor editor) {
		this.editor = editor;
	}

	@Override
	public void execute() {
		if (editor.getText().length() > 0) {
			deletedChar = editor.getText().charAt(editor.getText().length() - 1);
		}
		editor.delete();
	}

	@Override
	public void undo() {
		if (deletedChar != '\0') {
			editor.type(deletedChar); // Undo deletion by re-typing the character
		}
	}
}