package structural.composite.filesystem;

// Component: Common interface for files and directories
public interface FileSystemComponent {
	String getName(); // Get component name

	long getSize(); // Get component size in bytes

	void add(FileSystemComponent component); // Add child (for composites)

	void remove(FileSystemComponent component); // Remove child (for composites)
}
