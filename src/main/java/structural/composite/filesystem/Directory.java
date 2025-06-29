package structural.composite.filesystem;

import java.util.ArrayList;
import java.util.List;

// Composite: Represents a directory
public class Directory implements FileSystemComponent {
	private String name;
	private List<FileSystemComponent> children;

	public Directory(String name) {
		this.name = name;
		this.children = new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSize() {
		long totalSize = 0;
		for (FileSystemComponent child : children) {
			totalSize += child.getSize(); // Recursively compute size
		}
		return totalSize;
	}

	@Override
	public void add(FileSystemComponent component) {
		children.add(component);
	}

	@Override
	public void remove(FileSystemComponent component) {
		children.remove(component);
	}
}
