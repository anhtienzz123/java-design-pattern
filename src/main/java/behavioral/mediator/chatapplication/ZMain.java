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

//		== Output:
//		Alice joined the chat room
//		Bob joined the chat room
//		Charlie joined the chat room
//		Alice sends: Hello, everyone!
//		Bob received: Hello, everyone! (from Alice)
//		Charlie received: Hello, everyone! (from Alice)
//		Bob sends: Hi, Alice!
//		Alice received: Hi, Alice! (from Bob)
//		Charlie received: Hi, Alice! (from Bob)
//		Charlie sends: Hey, what's up?
//		Alice received: Hey, what's up? (from Charlie)
//		Bob received: Hey, what's up? (from Charlie)
	}
}