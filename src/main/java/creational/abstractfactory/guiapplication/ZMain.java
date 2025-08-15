package creational.abstractfactory.guiapplication;

public class ZMain {

	public static void main(String[] args) {
		// Create application with Windows UI
		GUIFactory windowsFactory = new WindowsFactory();
		Application windowsApplication = new Application(windowsFactory);
		windowsApplication.renderUI();

		System.out.println("--");

		// Create application with Mac UI
		GUIFactory macFactory = new MacFactory();
		Application macApplication = new Application(macFactory);
		macApplication.renderUI();
	}
}
