package creational.abstractfactory.guiapplication;

// ConcreteProduct
public class MacCheckbox implements Checkbox {

	@Override
	public void check() {
		System.out.println("Checking a Mac-style checkbox");
	}
}
