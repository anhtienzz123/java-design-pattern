package structural.composite.filesystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create files (leaves)
		FileSystemComponent file1 = new File("document.txt", 1000);
		FileSystemComponent file2 = new File("image.jpg", 2000);
		FileSystemComponent file3 = new File("video.mp4", 5000);

		// Create directories (composites)
		FileSystemComponent dir1 = new Directory("Documents");
		FileSystemComponent dir2 = new Directory("Media");
		FileSystemComponent root = new Directory("Root");

		// Build hierarchy
		dir1.add(file1); // Documents contains document.txt
		dir2.add(file2); // Media contains image.jpg
		dir2.add(file3); // Media contains video.mp4
		root.add(dir1); // Root contains Documents
		root.add(dir2); // Root contains Media

		// Compute sizes
		System.out.println("Size of " + file1.getName() + ": " + file1.getSize() + " bytes");
		System.out.println("Size of " + dir1.getName() + ": " + dir1.getSize() + " bytes");
		System.out.println("Size of " + dir2.getName() + ": " + dir2.getSize() + " bytes");
		System.out.println("Size of " + root.getName() + ": " + root.getSize() + " bytes");

		// Remove a file and recompute size
		dir2.remove(file3);
		System.out.println("\nAfter removing " + file3.getName() + ":");
		System.out.println("Size of " + dir2.getName() + ": " + dir2.getSize() + " bytes");
		System.out.println("Size of " + root.getName() + ": " + root.getSize() + " bytes");
	}
}
