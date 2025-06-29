package behavioral.command.texteditor;

// Concrete Command: Encapsulates typing a character
public class TypeCommand implements TextCommand {
	private TextEditor editor;
	private char character;

	public TypeCommand(TextEditor editor, char character) {
		this.editor = editor;
		this.character = character;
	}

	@Override
	public void execute() {
		editor.type(character);
	}

	@Override
	public void undo() {
		editor.delete(); // Undo typing by deleting the last character
	}
}
