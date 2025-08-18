package behavioral.visitor.documentprocessing;

public interface DocumentElement {
	void accept(DocumentVisitor visitor);
}