package behavioral.command.remotecontrol;

// Macro Command: Executes multiple commands in sequence
public class MacroCommand implements Command {
    private Command[] commands;
    private String description;
    
    public MacroCommand(Command[] commands, String description) {
        this.commands = commands;
        this.description = description;
    }
    
    @Override
    public void execute() {
        System.out.println("Executing macro: " + description);
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        System.out.println("Undoing macro: " + description);
        // Undo in reverse order
        for (int i = commands.length - 1; i >= 0; i--) {
            commands[i].undo();
        }
    }
    
    @Override
    public String getDescription() {
        return "Macro: " + description;
    }
}