package behavioral.visitor.filesystem;

// Concrete Element: Represents a file
public class File implements FileSystemElement {
	private String name;
	private long size;

	public File(String name, long size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	@Override
	public void accept(FileSystemVisitor visitor) {
		visitor.visit(this);
	}
}
