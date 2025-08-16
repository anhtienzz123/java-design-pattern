package structural.composite.graphics;

public class Rectangle implements Drawable {
    private Point topLeft;
    private double width;
    private double height;
    private String color;

    public Rectangle(int x, int y, double width, double height, String color) {
        this.topLeft = new Point(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.printf("🔳 Drawing %s rectangle at %s with dimensions %.1fx%.1f%n", 
                         color, topLeft, width, height);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        topLeft.move(deltaX, deltaY);
        System.out.printf("↗️ Moved rectangle to %s%n", topLeft);
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String getInfo() {
        return String.format("Rectangle[topLeft=%s, width=%.1f, height=%.1f, color=%s, area=%.2f]", 
                           topLeft, width, height, color, getArea());
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

    public Point getTopLeft() {
        return topLeft;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getColor() {
        return color;
    }
}