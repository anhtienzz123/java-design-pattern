package creational.abstractfactory.guiapplication;

// ConcreteProduct
public class WindowsCheckbox implements Checkbox {

	@Override
	public void check() {
		System.out.println("Checking a Windows-style checkbox");
	}
}
