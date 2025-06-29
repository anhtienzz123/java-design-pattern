package structural.proxy.virtualproxy;

// Proxy: Defers creation of RealImage until display is called
public class ImageProxy implements Image {
	private String filename;
	private RealImage realImage; // Reference to Real Subject

	public ImageProxy(String filename) {
		this.filename = filename;
	}

	@Override
	public void display() {
		if (realImage == null) {
			realImage = new RealImage(filename); // Lazy initialization
		}
		realImage.display(); // Delegate to Real Subject
	}
}