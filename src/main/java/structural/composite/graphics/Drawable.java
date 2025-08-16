package structural.composite.graphics;

public interface Drawable {
    void draw();
    void move(int deltaX, int deltaY);
    double getArea();
    String getInfo();
    void add(Drawable drawable);
    void remove(Drawable drawable);
    Drawable getChild(int index);
}