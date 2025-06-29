package structural.bridge.graphicsapplication;

// Implementor: Defines low-level rendering operations
public interface Renderer {
	void renderCircle(float radius);

	void renderRectangle(float width, float height);
}
