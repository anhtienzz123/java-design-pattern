package structural.bridge.graphicsapplication;

// Concrete Implementor: Vector rendering
public class VectorRenderer implements Renderer {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle with radius " + radius + " using Vector rendering");
	}

	@Override
	public void renderRectangle(float width, float height) {
		System.out.println(
				"Drawing a rectangle with width " + width + " and height " + height + " using Vector rendering");
	}
}
