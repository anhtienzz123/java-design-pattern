package behavioral.visitor.documentprocessing;

public class Heading implements DocumentElement {
	private String text;
	private int level;
	private boolean numbered;
	
	public Heading(String text, int level, boolean numbered) {
		this.text = text;
		this.level = level;
		this.numbered = numbered;
	}
	
	public String getText() {
		return text;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isNumbered() {
		return numbered;
	}
	
	@Override
	public void accept(DocumentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Heading{text='" + text + "', level=" + level + ", numbered=" + numbered + "}";
	}
}