package behavioral.mediator.chatapplication;

// Mediator: Defines the interface for communication
public interface ChatMediator {
	void sendMessage(String message, User sender);

	void addUser(User user);
}