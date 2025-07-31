package behavioral.command.remotecontrol;

// Command interface for home automation devices
public interface Command {
    void execute();
    void undo();
    String getDescription();
}