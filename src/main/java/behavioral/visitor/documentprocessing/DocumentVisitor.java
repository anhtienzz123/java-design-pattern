package behavioral.visitor.documentprocessing;

public interface DocumentVisitor {
	void visit(Paragraph paragraph);
	void visit(Heading heading);
	void visit(Table table);
	void visit(Image image);
}