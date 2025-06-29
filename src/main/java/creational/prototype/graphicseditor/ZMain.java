package creational.prototype.graphicseditor;

public class ZMain {
	public static void main(String[] args) {
		// Initialize registry with prototype instances
		ShapeRegistry registry = new ShapeRegistry();

		// Create and configure prototypes
		Circle circlePrototype = new Circle(10);
		circlePrototype.addProperty("filled");
		registry.addPrototype("circle", circlePrototype);

		Rectangle rectanglePrototype = new Rectangle(20, 30);
		rectanglePrototype.addProperty("bordered");
		registry.addPrototype("rectangle", rectanglePrototype);

		// Clone shapes from registry
		Shape circle1 = registry.getPrototype("circle");
		circle1.setId("C1");
		((Circle) circle1).setRadius(15);
		((Circle) circle1).addProperty("shadow"); // Modify clone without affecting prototype

		Shape circle2 = registry.getPrototype("circle");
		circle2.setId("C2");

		Shape rectangle1 = registry.getPrototype("rectangle");
		rectangle1.setId("R1");
		((Rectangle) rectangle1).setWidth(25);

		// Draw shapes
		circle1.draw();
		circle2.draw();
		rectangle1.draw();

		// Verify prototype is unchanged
		System.out.println("Original Circle Prototype: ");
		registry.getPrototype("circle").draw();
		
//		== Ouput:
//		Drawing Circle [id=C1, radius=15, properties=[filled, shadow]]
//		Drawing Circle [id=C2, radius=10, properties=[filled]]
//		Drawing Rectangle [id=R1, width=25, height=30, properties=[bordered]]
//		Original Circle Prototype: 
//		Drawing Circle [id=null, radius=10, properties=[filled]]
	}
}