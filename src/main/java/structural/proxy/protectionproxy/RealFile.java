package structural.proxy.protectionproxy;

// Real Subject: Performs actual file deletion
public class RealFile implements File {
	private String filename;

	public RealFile(String filename) {
		this.filename = filename;
	}

	@Override
	public void delete() {
		System.out.println("Deleting file: " + filename);
	}
}