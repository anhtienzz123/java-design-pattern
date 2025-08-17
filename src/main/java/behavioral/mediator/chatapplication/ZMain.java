package behavioral.mediator.chatapplication;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create mediator
		ChatMediator chatRoom = new ChatRoom();

		// Create users
		User alice = new ChatUser(chatRoom, "Alice");
		User bob = new ChatUser(chatRoom, "Bob");
		User charlie = new ChatUser(chatRoom, "Charlie");

		// Register users with the mediator
		chatRoom.addUser(alice);
		chatRoom.addUser(bob);
		chatRoom.addUser(charlie);

		// Users send messages
		alice.send("Hello, everyone!");
		bob.send("Hi, Alice!");
		charlie.send("Hey, what's up?");
	}
}