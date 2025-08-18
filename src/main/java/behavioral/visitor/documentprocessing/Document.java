package behavioral.visitor.documentprocessing;

import java.util.ArrayList;
import java.util.List;

public class Document {
	private List<DocumentElement> elements = new ArrayList<>();
	private String title;
	private String author;
	
	public Document(String title, String author) {
		this.title = title;
		this.author = author;
	}
	
	public void addElement(DocumentElement element) {
		elements.add(element);
	}
	
	public void accept(DocumentVisitor visitor) {
		for (DocumentElement element : elements) {
			element.accept(visitor);
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public List<DocumentElement> getElements() {
		return new ArrayList<>(elements);
	}
	
	public int getElementCount() {
		return elements.size();
	}
}