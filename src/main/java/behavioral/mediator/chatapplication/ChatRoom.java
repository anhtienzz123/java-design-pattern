package behavioral.mediator.chatapplication;

import java.util.ArrayList;
import java.util.List;

// Concrete Mediator: Manages message distribution
public class ChatRoom implements ChatMediator {
	private List<User> users = new ArrayList<>();

	@Override
	public void addUser(User user) {
		users.add(user);
		System.out.println(user.getName() + " joined the chat room");
	}

	@Override
	public void sendMessage(String message, User sender) {
		// Broadcast message to all users except the sender
		for (User user : users) {
			if (user != sender) {
				user.receive(message + " (from " + sender.getName() + ")");
			}
		}
	}
}