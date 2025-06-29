package creational.abstractfactory.guiapplication;

// Client
public class Application {

	private Button button;
	private Checkbox checkbox;

	public Application(GUIFactory guiFactory) {
		// Use the factory to create products
		this.button = guiFactory.createButton();
		this.checkbox = guiFactory.createCheckbox();
	}

	public void renderUI() {
		button.render();
		checkbox.check();
	}
}
