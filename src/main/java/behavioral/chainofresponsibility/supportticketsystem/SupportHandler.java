package behavioral.chainofresponsibility.supportticketsystem;

// Handler: Defines the interface for handling support tickets
public interface SupportHandler {
	void handleRequest(Ticket ticket); // Process the ticket

	void setNextHandler(SupportHandler nextHandler); // Set the next handler in the chain
}
