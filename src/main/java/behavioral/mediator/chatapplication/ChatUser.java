package behavioral.mediator.chatapplication;

// Concrete Colleague: Represents a chat user
public class ChatUser extends User {
	public ChatUser(ChatMediator mediator, String name) {
		super(mediator, name);
	}

	@Override
	public void send(String message) {
		System.out.println(name + " sends: " + message);
		mediator.sendMessage(message, this);
	}

	@Override
	public void receive(String message) {
		System.out.println(name + " received: " + message);
	}
}
