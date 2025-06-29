package structural.bridge.graphicsapplication;

// Concrete Implementor: Raster rendering
public class RasterRenderer implements Renderer {
	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing a circle with radius " + radius + " using Raster rendering");
	}

	@Override
	public void renderRectangle(float width, float height) {
		System.out.println(
				"Drawing a rectangle with width " + width + " and height " + height + " using Raster rendering");
	}
}
