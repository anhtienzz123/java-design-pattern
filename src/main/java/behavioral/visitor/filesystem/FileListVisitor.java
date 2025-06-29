package behavioral.visitor.filesystem;

import java.util.ArrayList;
import java.util.List;

// Concrete Visitor: Generates a file list
public class FileListVisitor implements FileSystemVisitor {
	private List<String> fileNames = new ArrayList<>();

	@Override
	public void visit(File file) {
		fileNames.add(file.getName());
		System.out.println("Listed file: " + file.getName());
	}

	@Override
	public void visit(Directory directory) {
		System.out.println("Listing directory: " + directory.getName());
		for (FileSystemElement element : directory.getElements()) {
			element.accept(this); // Recursively visit elements
		}
	}

	public List<String> getFileNames() {
		return fileNames;
	}
}
