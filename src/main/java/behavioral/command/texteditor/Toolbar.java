package behavioral.command.texteditor;

import java.util.Stack;

// Invoker: Executes commands and supports undo
public class Toolbar {
	private Stack<TextCommand> commandHistory = new Stack<>();

	public void executeCommand(TextCommand command) {
		command.execute();
		commandHistory.push(command); // Store for undo
	}

	public void undo() {
		if (!commandHistory.isEmpty()) {
			TextCommand command = commandHistory.pop();
			command.undo();
		} else {
			System.out.println("Nothing to undo");
		}
	}
}