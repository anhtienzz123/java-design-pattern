package creational.abstractfactory.guiapplication;

// ConcreteProduct
public class MacButton implements Button {

	@Override
	public void render() {
		System.out.println("Rendering a Mac-style button");
	}
}
