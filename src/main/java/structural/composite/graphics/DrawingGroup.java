package structural.composite.graphics;

import java.util.ArrayList;
import java.util.List;

public class DrawingGroup implements Drawable {
    private List<Drawable> children;
    private String name;
    private Point groupOffset;

    public DrawingGroup(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.groupOffset = new Point(0, 0);
    }

    @Override
    public void draw() {
        System.out.printf("📂 Drawing group '%s' with %d elements:%n", name, children.size());
        for (Drawable child : children) {
            child.draw();
        }
        System.out.printf("✅ Completed drawing group '%s'%n", name);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        groupOffset.move(deltaX, deltaY);
        System.out.printf("📦 Moving group '%s' by (%d,%d)%n", name, deltaX, deltaY);
        for (Drawable child : children) {
            child.move(deltaX, deltaY);
        }
    }

    @Override
    public double getArea() {
        double totalArea = 0.0;
        for (Drawable child : children) {
            totalArea += child.getArea();
        }
        return totalArea;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("DrawingGroup[name=%s, children=%d, totalArea=%.2f]%n", 
                                 name, children.size(), getArea()));
        for (int i = 0; i < children.size(); i++) {
            info.append(String.format("  [%d] %s%n", i, children.get(i).getInfo()));
        }
        return info.toString();
    }

    @Override
    public void add(Drawable drawable) {
        children.add(drawable);
        System.out.printf("➕ Added element to group '%s' (now has %d elements)%n", name, children.size());
    }

    @Override
    public void remove(Drawable drawable) {
        if (children.remove(drawable)) {
            System.out.printf("➖ Removed element from group '%s' (now has %d elements)%n", name, children.size());
        }
    }

    @Override
    public Drawable getChild(int index) {
        if (index >= 0 && index < children.size()) {
            return children.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid child index: " + index);
    }

    public String getName() {
        return name;
    }

    public int getChildCount() {
        return children.size();
    }

    public List<Drawable> getChildren() {
        return new ArrayList<>(children);
    }

    public void clear() {
        children.clear();
        System.out.printf("🗑️ Cleared all elements from group '%s'%n", name);
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }
}