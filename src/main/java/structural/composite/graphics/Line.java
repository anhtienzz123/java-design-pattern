package structural.composite.graphics;

public class Line implements Drawable {
    private Point start;
    private Point end;
    private String color;
    private double thickness;

    public Line(int x1, int y1, int x2, int y2, String color, double thickness) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void draw() {
        System.out.printf("📏 Drawing %s line from %s to %s (thickness: %.1f)%n", 
                         color, start, end, thickness);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        start.move(deltaX, deltaY);
        end.move(deltaX, deltaY);
        System.out.printf("↗️ Moved line from %s to %s%n", start, end);
    }

    @Override
    public double getArea() {
        return 0.0; // Lines have no area
    }

    @Override
    public String getInfo() {
        double length = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + 
                                 Math.pow(end.getY() - start.getY(), 2));
        return String.format("Line[start=%s, end=%s, color=%s, thickness=%.1f, length=%.2f]", 
                           start, end, color, thickness, length);
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

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public String getColor() {
        return color;
    }

    public double getThickness() {
        return thickness;
    }
}