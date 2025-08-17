package supportsystemticket;

public class Ticket {

	private int severity;
	private String description;

	public Ticket(int severity, String description) {
		this.severity = severity;
		this.description = description;
	}
	
	public int getSeverity() {
		return this.severity;
	}
	
	public String getDescription() {
		return this.description;
	}
}
