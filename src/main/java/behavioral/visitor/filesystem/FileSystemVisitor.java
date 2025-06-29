package behavioral.visitor.filesystem;

// Visitor: Declares visit methods for each element type
public interface FileSystemVisitor {
	void visit(File file);

	void visit(Directory directory);
}
