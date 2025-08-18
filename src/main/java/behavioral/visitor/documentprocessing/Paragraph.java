package behavioral.visitor.documentprocessing;

public class Paragraph implements DocumentElement {
	private String text;
	private String alignment;
	
	public Paragraph(String text, String alignment) {
		this.text = text;
		this.alignment = alignment;
	}
	
	public String getText() {
		return text;
	}
	
	public String getAlignment() {
		return alignment;
	}
	
	public int getWordCount() {
		return text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
	}
	
	@Override
	public void accept(DocumentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Paragraph{text='" + text + "', alignment='" + alignment + "'}";
	}
}