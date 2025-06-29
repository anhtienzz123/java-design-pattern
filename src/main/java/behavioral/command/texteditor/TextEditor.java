package behavioral.command.texteditor;

// Receiver: The text editor that performs the actual operations
public class TextEditor {
	private StringBuilder text = new StringBuilder();

	public void type(char character) {
		text.append(character);
		System.out.println("Typed: " + character + ", Text: " + text);
	}

	public void delete() {
		if (text.length() > 0) {
			char deleted = text.charAt(text.length() - 1);
			text.deleteCharAt(text.length() - 1);
			System.out.println("Deleted: " + deleted + ", Text: " + text);
		} else {
			System.out.println("Nothing to delete");
		}
	}

	public String getText() {
		return text.toString();
	}
}