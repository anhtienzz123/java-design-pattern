package structural.composite.graphics;

public class Circle implements Drawable {
    private Point center;
    private double radius;
    private String color;

    public Circle(int x, int y, double radius, String color) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.printf("🔴 Drawing %s circle at %s with radius %.1f%n", 
                         color, center, radius);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        center.move(deltaX, deltaY);
        System.out.printf("↗️ Moved circle to %s%n", center);
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String getInfo() {
        return String.format("Circle[center=%s, radius=%.1f, color=%s, area=%.2f]", 
                           center, radius, color, getArea());
    }

    @Override
    public void add(Drawable drawable) {
        throw new UnsupportedOperationException("Cannot add to a leaf node");
    }

    @Override
    public void remove(Drawable drawable) {
        throw new UnsupportedOperationException("Cannot remove from a leaf node");
    }

    @Override
    public Drawable getChild(int index) {
        throw new UnsupportedOperationException("Leaf node has no children");
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public String getColor() {
        return color;
    }
}