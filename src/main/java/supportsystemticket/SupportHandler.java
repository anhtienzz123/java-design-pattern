package supportsystemticket;

// Handler
public interface SupportHandler {

	void handleRequest(Ticket ticket);
	
	void setNextHandler(SupportHandler nextHandler);
}
