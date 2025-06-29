package behavioral.visitor.filesystem;

import java.util.ArrayList;
import java.util.List;

// Object Structure: Manages the file system hierarchy
public class FileSystem {
	private List<FileSystemElement> elements = new ArrayList<>();

	public void addElement(FileSystemElement element) {
		elements.add(element);
	}

	public void accept(FileSystemVisitor visitor) {
		for (FileSystemElement element : elements) {
			element.accept(visitor);
		}
	}
}
