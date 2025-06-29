package behavioral.visitor.filesystem;

import java.util.ArrayList;
import java.util.List;

// Concrete Element: Represents a directory
public class Directory implements FileSystemElement {
	private String name;
	private List<FileSystemElement> elements = new ArrayList<>();

	public Directory(String name) {
		this.name = name;
	}

	public void addElement(FileSystemElement element) {
		elements.add(element);
	}

	public String getName() {
		return name;
	}

	public List<FileSystemElement> getElements() {
		return elements;
	}

	@Override
	public void accept(FileSystemVisitor visitor) {
		visitor.visit(this);
	}
}
