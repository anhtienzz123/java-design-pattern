package creational.simplefactory.documentcreationsystem;

// ConcreteProduct
public class PDFDocument implements Document {

	@Override
	public void open() {
		System.out.println("Opening PDF document");
	}

	@Override
	public void save() {
		System.out.println("Saving PDF document");
	}

	@Override
	public void close() {
		System.out.println("Closing PDF document");
	}
}
