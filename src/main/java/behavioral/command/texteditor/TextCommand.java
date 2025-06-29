package behavioral.command.texteditor;

// Command: Defines the interface for commands
public interface TextCommand {
	void execute(); // Perform the action

	void undo(); // Reverse the action
}