package structural.proxy.virtualproxy;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create proxies for images (not loaded yet)
		Image image1 = new ImageProxy("photo1.jpg");
		Image image2 = new ImageProxy("photo2.jpg");

		// Display image1 (loads and displays)
		System.out.println("First display of image1:");
		image1.display();

		// Display image1 again (already loaded, no loading)
		System.out.println("\nSecond display of image1:");
		image1.display();

		// Display image2 (loads and displays)
		System.out.println("\nFirst display of image2:");
		image2.display();
		
//		== Output: 
//		First display of image1:
//		Loading image from file: photo1.jpg
//		Displaying image: photo1.jpg
//
//		Second display of image1:
//		Displaying image: photo1.jpg
//
//		First display of image2:
//		Loading image from file: photo2.jpg
//		Displaying image: photo2.jpg
	}
}
