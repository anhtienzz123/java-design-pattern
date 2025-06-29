package creational.prototype.graphicseditor;

//Prototype interface
public interface Shape extends Cloneable {
	Shape clone(); // Cloning method

	void draw(); // Example method for shape behavior

	void setId(String id); // For customization

	String getId();
}
