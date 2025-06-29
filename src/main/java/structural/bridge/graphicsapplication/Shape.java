package structural.bridge.graphicsapplication;

// Abstraction: Defines high-level shape operations
public abstract class Shape {
	protected Renderer renderer; // Bridge to Implementor

	protected Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract void draw(); // High-level operation
}
