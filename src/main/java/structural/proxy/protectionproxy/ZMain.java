package structural.proxy.protectionproxy;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create proxies for different users
		File file1 = new FileProxy("data.txt", "admin");
		File file2 = new FileProxy("data.txt", "user");

		// Admin tries to delete
		System.out.println("Admin attempting to delete:");
		file1.delete();

		// User tries to delete
		System.out.println("\nUser attempting to delete:");
		try {
			file2.delete();
		} catch (SecurityException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
//		== Output:
//		Admin attempting to delete:
//		Deleting file: data.txt
//
//		User attempting to delete:
//		Error: Access denied: Only admins can delete files
	}
}
