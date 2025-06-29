package structural.proxy.protectionproxy;

// Proxy: Controls access to file deletion based on permissions
public class FileProxy implements File {
	private RealFile realFile;
	private String filename;
	private String userRole;

	public FileProxy(String filename, String userRole) {
		this.filename = filename;
		this.userRole = userRole;
	}

	@Override
	public void delete() {
		if (!userRole.equals("admin")) {
			throw new SecurityException("Access denied: Only admins can delete files");
		}
		if (realFile == null) {
			realFile = new RealFile(filename); // Lazy initialization
		}
		realFile.delete(); // Delegate to Real Subject
	}
}
