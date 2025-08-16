package structural.bridge.graphicsapplication;

public class ZMain {
	public static void main(String[] args) {
		// Create renderers
		Renderer vectorRenderer = new VectorRenderer();
		Renderer rasterRenderer = new RasterRenderer();

		// Create shapes with different renderers
		Shape circle1 = new Circle(vectorRenderer, 5.0f);
		Shape circle2 = new Circle(rasterRenderer, 10.0f);
		Shape rectangle = new Rectangle(vectorRenderer, 20.0f, 15.0f);

		// Draw shapes
		circle1.draw();
		circle2.draw();
		rectangle.draw();

		// Swap renderer at runtime (demonstrating flexibility)
		((Circle) circle2).renderer = vectorRenderer;
		circle2.draw();
	}
}
