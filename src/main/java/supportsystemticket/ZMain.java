package supportsystemticket;

public class ZMain {

	public static void main(String[] args) {
		SupportHandler level1Handler = new Level1Support();
		SupportHandler level2Handler = new Level2Support();
		
		level1Handler.setNextHandler(level2Handler);
		
		Ticket ticket = new Ticket(3, "level1");
		level1Handler.handleRequest(ticket);
	}
}
