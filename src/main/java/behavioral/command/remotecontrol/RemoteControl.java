package behavioral.command.remotecontrol;

import java.util.Stack;

// Invoker: Universal Remote Control with 7 slots
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Stack<Command> undoHistory;
    private NoCommand noCommand;
    
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        undoHistory = new Stack<>();
        noCommand = new NoCommand();
        
        // Initialize all slots with NoCommand
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        if (slot >= 0 && slot < 7) {
            onCommands[slot] = onCommand;
            offCommands[slot] = offCommand;
        }
    }
    
    public void onButtonPressed(int slot) {
        if (slot >= 0 && slot < 7) {
            System.out.println("[Remote] ON button " + slot + " pressed: " + onCommands[slot].getDescription());
            onCommands[slot].execute();
            undoHistory.push(onCommands[slot]);
        }
    }
    
    public void offButtonPressed(int slot) {
        if (slot >= 0 && slot < 7) {
            System.out.println("[Remote] OFF button " + slot + " pressed: " + offCommands[slot].getDescription());
            offCommands[slot].execute();
            undoHistory.push(offCommands[slot]);
        }
    }
    
    public void undoButtonPressed() {
        if (!undoHistory.isEmpty()) {
            Command lastCommand = undoHistory.pop();
            System.out.println("[Remote] UNDO button pressed: Undoing " + lastCommand.getDescription());
            lastCommand.undo();
        } else {
            System.out.println("[Remote] UNDO button pressed: Nothing to undo");
        }
    }
    
    public void displayStatus() {
        System.out.println("\n------ Remote Control Status ------");
        for (int i = 0; i < 7; i++) {
            System.out.printf("[slot %d] ON: %-25s OFF: %s%n", 
                i, onCommands[i].getDescription(), offCommands[i].getDescription());
        }
        System.out.println("Undo history size: " + undoHistory.size());
        System.out.println("-----------------------------------\n");
    }
}