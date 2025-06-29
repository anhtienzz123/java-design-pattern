package structural.bridge.graphicsapplication;

// Refined Abstraction: Circle shape
public class Circle extends Shape {
	private float radius;

	public Circle(Renderer renderer, float radius) {
		super(renderer);
		this.radius = radius;
	}

	@Override
	public void draw() {
		renderer.renderCircle(radius); // Delegate to Implementor
	}
}
