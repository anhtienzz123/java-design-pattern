package behavioral.command.remotecontrol;

// Null Object Pattern: Empty command that does nothing
public class NoCommand implements Command {
    
    @Override
    public void execute() {
        // Do nothing
    }
    
    @Override
    public void undo() {
        // Do nothing
    }
    
    @Override
    public String getDescription() {
        return "No command assigned";
    }
}