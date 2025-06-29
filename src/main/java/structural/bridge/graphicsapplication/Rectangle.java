package structural.bridge.graphicsapplication;

// Refined Abstraction: Rectangle shape
public class Rectangle extends Shape {
	private float width, height;

	public Rectangle(Renderer renderer, float width, float height) {
		super(renderer);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw() {
		renderer.renderRectangle(width, height); // Delegate to Implementor
	}
}
