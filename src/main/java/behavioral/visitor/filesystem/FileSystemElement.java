package behavioral.visitor.filesystem;

// Element: Defines the accept method for visitors
public interface FileSystemElement {
	void accept(FileSystemVisitor visitor);
}
