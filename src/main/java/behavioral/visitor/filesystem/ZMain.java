package behavioral.visitor.filesystem;

public class ZMain {
    public static void main(String[] args) {
        // Create file system structure
        FileSystem fileSystem = new FileSystem();

        Directory root = new Directory("root");
        Directory home = new Directory("home");
        File file1 = new File("doc1.txt", 1000);
        File file2 = new File("image.png", 5000);
        File file3 = new File("data.csv", 2000);

        root.addElement(home);
        root.addElement(file1);
        home.addElement(file2);
        home.addElement(file3);

        fileSystem.addElement(root);

        // Visitor 1: Calculate total size
        System.out.println("Calculating total size:");
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        fileSystem.accept(sizeVisitor);
        System.out.println("Total size: " + sizeVisitor.getTotalSize() + " bytes");

        // Visitor 2: Generate file list
        System.out.println("\nGenerating file list:");
        FileListVisitor listVisitor = new FileListVisitor();
        fileSystem.accept(listVisitor);
        System.out.println("File names: " + listVisitor.getFileNames());
        
//        == Output:
//        Calculating total size:
//        Entering directory: root
//        Entering directory: home
//        File: image.png, Size: 5000 bytes
//        File: data.csv, Size: 2000 bytes
//        Exiting directory: home
//        File: doc1.txt, Size: 1000 bytes
//        Exiting directory: root
//        Total size: 8000 bytes
//
//        Generating file list:
//        Listing directory: root
//        Listing directory: home
//        Listed file: image.png
//        Listed file: data.csv
//        Listed file: doc1.txt
//        File names: [image.png, data.csv, doc1.txt]
    }
}