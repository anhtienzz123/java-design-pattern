package behavioral.chainofresponsibility.supportticketsystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create handlers
		SupportHandler level1 = new Level1Support();
		SupportHandler level2 = new Level2Support();
		SupportHandler level3 = new Level3Support();

		// Set up the chain: Level 1 -> Level 2 -> Level 3
		level1.setNextHandler(level2);
		level2.setNextHandler(level3);

		// Create tickets with different severity levels
		Ticket ticket1 = new Ticket("Login issue", 1); // Low severity
		Ticket ticket2 = new Ticket("Server crash", 3); // High severity
		Ticket ticket3 = new Ticket("Database error", 2); // Medium severity
		Ticket ticket4 = new Ticket("Unknown issue", 4); // Unhandled severity

		// Send tickets to the chain
		System.out.println("Processing ticket 1:");
		level1.handleRequest(ticket1);

		System.out.println("\nProcessing ticket 2:");
		level1.handleRequest(ticket2);

		System.out.println("\nProcessing ticket 3:");
		level1.handleRequest(ticket3);

		System.out.println("\nProcessing ticket 4:");
		level1.handleRequest(ticket4);
		
//		== Ouput: 
//		Processing ticket 1:
//		Level 1 Support: Handling ticket - Login issue
//
//		Processing ticket 2:
//		Level 1 Support: Passing ticket to next level
//		Level 2 Support: Passing ticket to next level
//		Level 3 Support: Handling ticket - Server crash
//
//		Processing ticket 3:
//		Level 1 Support: Passing ticket to next level
//		Level 2 Support: Handling ticket - Database error
//
//		Processing ticket 4:
//		Level 1 Support: Passing ticket to next level
//		Level 2 Support: Passing ticket to next level
//		Level 3 Support: No handler available for ticket - Unknown issue
	}
}
