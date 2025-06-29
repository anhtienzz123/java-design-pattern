package creational.abstractfactory.guiapplication;

// ConcreteProduct
public class WindowsButton implements Button {

	@Override
	public void render() {
		System.out.println("Rendering a Windows-style button");
	}
}
