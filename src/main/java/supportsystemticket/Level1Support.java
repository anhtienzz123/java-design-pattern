package supportsystemticket;

// Concrete Handler
public class Level1Support implements SupportHandler {

	private SupportHandler nextHandler;
	
	@Override
	public void handleRequest(Ticket ticket) {
		if(ticket.getSeverity() == 1) {
			System.out.println("Level 1 Support: Handling ticket - " + ticket.getDescription());
		}else if(nextHandler != null) {
			System.out.println("Level 1 Support: Passing ticket to next level");
			nextHandler.handleRequest(ticket);
		}else {
			System.out.println("Level 1 Support: No handler available for ticket - " + ticket.getDescription());
		}
	}

	@Override
	public void setNextHandler(SupportHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
}
