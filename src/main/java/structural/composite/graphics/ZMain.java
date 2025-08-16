package structural.composite.graphics;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Composite Pattern: Graphics Drawing System ===\n");

        // Create individual shapes (leaf objects)
        Circle redCircle = new Circle(10, 10, 5.0, "red");
        Circle blueCircle = new Circle(30, 30, 8.0, "blue");
        Rectangle greenRect = new Rectangle(5, 5, 15.0, 10.0, "green");
        Rectangle yellowRect = new Rectangle(25, 15, 12.0, 8.0, "yellow");
        Line blackLine = new Line(0, 0, 50, 50, "black", 2.0);
        Line purpleLine = new Line(50, 0, 0, 50, "purple", 1.5);

        System.out.println("--- Creating Basic Shapes ---");
        redCircle.draw();
        greenRect.draw();
        blackLine.draw();

        System.out.println("\n--- Creating Drawing Groups (Composites) ---");

        // Create groups (composite objects)
        DrawingGroup geometricShapes = new DrawingGroup("Geometric Shapes");
        geometricShapes.add(redCircle);
        geometricShapes.add(blueCircle);
        geometricShapes.add(greenRect);
        geometricShapes.add(yellowRect);

        DrawingGroup lines = new DrawingGroup("Lines");
        lines.add(blackLine);
        lines.add(purpleLine);

        // Create a complex group containing other groups
        DrawingGroup complexDrawing = new DrawingGroup("Complex Drawing");
        complexDrawing.add(geometricShapes);
        complexDrawing.add(lines);

        System.out.println("\n--- Drawing Groups ---");
        geometricShapes.draw();
        System.out.println();
        lines.draw();

        System.out.println("\n--- Creating Canvas with Layers ---");
        Canvas mainCanvas = new Canvas("Main Artwork", 800, 600);
        
        // Add groups as layers to canvas
        mainCanvas.add(geometricShapes);
        mainCanvas.add(lines);
        
        // Add individual shapes directly to canvas
        Circle canvasCircle = new Circle(100, 100, 15.0, "orange");
        mainCanvas.add(canvasCircle);

        System.out.println("\n--- Rendering Complete Canvas ---");
        mainCanvas.draw();

        System.out.println("\n--- Demonstrating Uniform Treatment ---");
        System.out.println("Treating individual shapes and groups uniformly:");
        
        // All objects implement the same interface - uniform treatment
        printDrawableInfo(redCircle);
        printDrawableInfo(geometricShapes);
        printDrawableInfo(mainCanvas);

        System.out.println("\n--- Demonstrating Movement Operations ---");
        System.out.println("Moving individual shape:");
        redCircle.move(5, 5);
        
        System.out.println("\nMoving entire group (all children move together):");
        lines.move(10, 10);
        
        System.out.println("\nMoving entire canvas (all layers move together):");
        mainCanvas.move(-5, -5);

        System.out.println("\n--- Area Calculations ---");
        System.out.printf("Red circle area: %.2f%n", redCircle.getArea());
        System.out.printf("Geometric shapes total area: %.2f%n", geometricShapes.getArea());
        System.out.printf("Entire canvas total area: %.2f%n", mainCanvas.getArea());

        System.out.println("\n--- Canvas Statistics ---");
        mainCanvas.printStats();

        System.out.println("\n--- Modifying Composition at Runtime ---");
        
        // Remove a shape from a group
        geometricShapes.remove(yellowRect);
        System.out.printf("After removing yellow rectangle, geometric shapes area: %.2f%n", 
                         geometricShapes.getArea());
        
        // Add the removed shape to another group
        lines.add(yellowRect);
        System.out.printf("After adding yellow rectangle to lines group, lines area: %.2f%n", 
                         lines.getArea());

        System.out.println("\n--- Complex Nested Structure ---");
        DrawingGroup masterpiece = new DrawingGroup("Masterpiece");
        masterpiece.add(mainCanvas);
        masterpiece.add(complexDrawing);
        
        System.out.printf("Masterpiece contains %d top-level elements%n", masterpiece.getChildCount());
        System.out.printf("Total area of masterpiece: %.2f%n", masterpiece.getArea());

        System.out.println("\n=== Composite Pattern Demo Complete ===");
    }

    // Demonstrates treating leaf and composite objects uniformly
    private static void printDrawableInfo(Drawable drawable) {
        System.out.println("Drawable Object Info:");
        System.out.println(drawable.getInfo());
        System.out.printf("Total area: %.2f%n", drawable.getArea());
        System.out.println("---");
    }
}