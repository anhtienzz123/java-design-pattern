# Composite Pattern: Graphics Drawing System

This example demonstrates the Composite pattern through a comprehensive graphics drawing system that treats individual shapes and groups of shapes uniformly, allowing for complex hierarchical drawings and layered compositions.

## Pattern Overview

The Composite pattern composes objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly. In this graphics system:

- **Component**: `Drawable` interface defines common operations for all graphics objects
- **Leaf**: Individual shapes (`Circle`, `Rectangle`, `Line`) that cannot contain other objects
- **Composite**: Container objects (`DrawingGroup`, `Canvas`) that can contain other drawable objects

## Key Components

### Component Interface
- **`Drawable`**: Defines common interface for all graphics objects including `draw()`, `move()`, `getArea()`, and composition operations

### Leaf Objects (Primitive Shapes)
- **`Circle`**: Circular shape with center point, radius, and color
- **`Rectangle`**: Rectangular shape with position, dimensions, and color  
- **`Line`**: Linear shape with start/end points, color, and thickness
- **`Point`**: Helper class representing a 2D coordinate

### Composite Objects (Containers)
- **`DrawingGroup`**: Groups multiple drawable objects that can be manipulated as a single unit
- **`Canvas`**: Represents a drawing surface with multiple layers, each containing drawable objects

## Benefits Demonstrated

### 1. **Uniform Treatment**
All objects implement the same `Drawable` interface, allowing client code to treat individual shapes and complex compositions identically:

```java
// Same method works for both individual shapes and groups
public void processDrawable(Drawable drawable) {
    drawable.draw();           // Works for Circle, DrawingGroup, Canvas
    drawable.move(10, 10);     // Moves shape or entire group
    double area = drawable.getArea(); // Gets area of shape or sum of group
}
```

### 2. **Hierarchical Structure**
Complex drawings can be built from simple components:
- Individual shapes can be grouped together
- Groups can contain other groups (nested composition)
- Canvas can contain multiple layers of groups and shapes
- Unlimited nesting depth is supported

### 3. **Recursive Operations**
Operations on composite objects automatically apply to all contained objects:

```java
DrawingGroup group = new DrawingGroup("My Group");
group.add(circle);
group.add(rectangle);
group.move(20, 30);  // Moves both circle and rectangle
group.draw();        // Draws both shapes
```

### 4. **Transparent Composition**
Client code doesn't need to distinguish between simple and composite objects:
- Same interface for adding/removing elements
- Same drawing and manipulation operations
- Same area calculations (composites sum their children's areas)

## Real-World Applications

This pattern is commonly used in:

1. **Graphics and UI Systems**
   - Vector graphics editors (Illustrator, Inkscape)
   - GUI widget hierarchies (windows containing panels containing buttons)
   - Game object hierarchies (groups of sprites, particle systems)

2. **Document Processing**
   - Document structure (documents containing sections containing paragraphs)
   - HTML DOM tree (elements containing other elements)
   - File system representations (directories containing files and subdirectories)

3. **Organizational Structures**
   - Company hierarchies (departments containing teams containing employees)
   - Menu systems (menus containing submenus containing items)
   - Mathematical expressions (operations containing sub-expressions)

## Usage Example

```java
// Create individual shapes
Circle circle = new Circle(10, 10, 5.0, "red");
Rectangle rectangle = new Rectangle(5, 5, 15.0, 10.0, "blue");

// Create a group
DrawingGroup group = new DrawingGroup("Shapes");
group.add(circle);
group.add(rectangle);

// Create canvas with layers
Canvas canvas = new Canvas("My Drawing", 800, 600);
canvas.add(group);           // Add group as a layer
canvas.add(new Line(0, 0, 100, 100, "black", 2.0)); // Add individual shape

// Treat everything uniformly
canvas.draw();               // Draws entire composition
canvas.move(50, 50);         // Moves everything together
double totalArea = canvas.getArea(); // Sum of all contained areas
```

## Advanced Features

### Layer Management
The `Canvas` class provides sophisticated layer management:
- Multiple layers can be added/removed dynamically
- Each layer can contain complex hierarchical structures
- Layers are rendered in order (painter's algorithm)

### Dynamic Composition
Objects can be moved between groups at runtime:
```java
group1.remove(shape);
group2.add(shape);
```

### Nested Groups
Groups can contain other groups for complex hierarchies:
```java
DrawingGroup masterGroup = new DrawingGroup("Master");
masterGroup.add(shapesGroup);
masterGroup.add(linesGroup);
masterGroup.add(canvas);
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="structural.composite.graphics.ZMain"

# Or with Java directly
java -cp target/classes structural.composite.graphics.ZMain
```

## Design Principles Applied

- **Single Responsibility**: Each class has one clear purpose (drawing shapes, grouping objects, managing canvas)
- **Open/Closed**: New shape types and composite types can be added without modifying existing code
- **Liskov Substitution**: All drawable objects can be used interchangeably
- **Composite Pattern**: Part-whole hierarchies with uniform treatment of individual and composite objects
- **Recursive Structure**: Operations naturally recurse through the object tree