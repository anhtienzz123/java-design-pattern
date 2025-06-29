package behavioral.chainofresponsibility.supportticketsystem;

// Concrete Handler: Level 3 Support
public class Level3Support implements SupportHandler {
	private SupportHandler nextHandler;

	@Override
	public void setNextHandler(SupportHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void handleRequest(Ticket ticket) {
		if (ticket.getSeverity() == 3) {
			System.out.println("Level 3 Support: Handling ticket - " + ticket.getDescription());
		} else if (nextHandler != null) {
			System.out.println("Level 3 Support: Passing ticket to next level");
			nextHandler.handleRequest(ticket);
		} else {
			System.out.println("Level 3 Support: No handler available for ticket - " + ticket.getDescription());
		}
	}
}