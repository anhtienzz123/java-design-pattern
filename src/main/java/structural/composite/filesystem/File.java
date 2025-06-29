package structural.composite.filesystem;

// Leaf: Represents a file
public class File implements FileSystemComponent {
	private String name;
	private long size; // Size in bytes

	public File(String name, long size) {
		this.name = name;
		this.size = size;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public void add(FileSystemComponent component) {
		throw new UnsupportedOperationException("Cannot add to a file");
	}

	@Override
	public void remove(FileSystemComponent component) {
		throw new UnsupportedOperationException("Cannot remove from a file");
	}
}
