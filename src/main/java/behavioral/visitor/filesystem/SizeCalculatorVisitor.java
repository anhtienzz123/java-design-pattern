package behavioral.visitor.filesystem;

// Concrete Visitor: Calculates total size of files
public class SizeCalculatorVisitor implements FileSystemVisitor {
	private long totalSize = 0;

	@Override
	public void visit(File file) {
		totalSize += file.getSize();
		System.out.println("File: " + file.getName() + ", Size: " + file.getSize() + " bytes");
	}

	@Override
	public void visit(Directory directory) {
		System.out.println("Entering directory: " + directory.getName());
		for (FileSystemElement element : directory.getElements()) {
			element.accept(this); // Recursively visit elements
		}
		System.out.println("Exiting directory: " + directory.getName());
	}

	public long getTotalSize() {
		return totalSize;
	}
}
