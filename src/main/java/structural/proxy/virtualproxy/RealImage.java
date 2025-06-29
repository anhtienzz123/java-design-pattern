package structural.proxy.virtualproxy;

// Real Subject: Loads and displays a high-resolution image
public class RealImage implements Image {
	private String filename;

	public RealImage(String filename) {
		this.filename = filename;
		loadImage(); // Expensive operation
	}

	private void loadImage() {
		System.out.println("Loading image from file: " + filename);
		// Simulate loading a high-resolution image (e.g., from disk or network)
		try {
			Thread.sleep(1000); // Simulate delay
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void display() {
		System.out.println("Displaying image: " + filename);
	}
}
