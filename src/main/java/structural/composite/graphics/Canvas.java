package structural.composite.graphics;

import java.util.ArrayList;
import java.util.List;

public class Canvas implements Drawable {
    private List<Drawable> layers;
    private String title;
    private int width;
    private int height;

    public Canvas(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
    }

    @Override
    public void draw() {
        System.out.printf("🖼️ Rendering canvas '%s' (%dx%d) with %d layers:%n", 
                         title, width, height, layers.size());
        System.out.println("=" + "=".repeat(50));
        
        for (int i = 0; i < layers.size(); i++) {
            System.out.printf("📋 Layer %d:%n", i + 1);
            layers.get(i).draw();
            System.out.println();
        }
        
        System.out.println("=" + "=".repeat(50));
        System.out.printf("🎨 Canvas '%s' rendering complete%n", title);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        System.out.printf("🖼️ Moving entire canvas '%s' by (%d,%d)%n", title, deltaX, deltaY);
        for (Drawable layer : layers) {
            layer.move(deltaX, deltaY);
        }
    }

    @Override
    public double getArea() {
        double totalArea = 0.0;
        for (Drawable layer : layers) {
            totalArea += layer.getArea();
        }
        return totalArea;
    }

    @Override
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("Canvas[title=%s, dimensions=%dx%d, layers=%d, totalArea=%.2f]%n", 
                                 title, width, height, layers.size(), getArea()));
        
        for (int i = 0; i < layers.size(); i++) {
            info.append(String.format("Layer %d:%n", i + 1));
            String layerInfo = layers.get(i).getInfo();
            // Indent layer info
            String[] lines = layerInfo.split("%n");
            for (String line : lines) {
                info.append("  ").append(line).append("%n");
            }
        }
        
        return info.toString();
    }

    @Override
    public void add(Drawable drawable) {
        layers.add(drawable);
        System.out.printf("📋 Added layer to canvas '%s' (now has %d layers)%n", title, layers.size());
    }

    @Override
    public void remove(Drawable drawable) {
        if (layers.remove(drawable)) {
            System.out.printf("🗑️ Removed layer from canvas '%s' (now has %d layers)%n", title, layers.size());
        }
    }

    @Override
    public Drawable getChild(int index) {
        if (index >= 0 && index < layers.size()) {
            return layers.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid layer index: " + index);
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLayerCount() {
        return layers.size();
    }

    public void clearCanvas() {
        layers.clear();
        System.out.printf("🗑️ Cleared all layers from canvas '%s'%n", title);
    }

    public void printStats() {
        System.out.printf("📊 Canvas Statistics:%n");
        System.out.printf("   Title: %s%n", title);
        System.out.printf("   Dimensions: %dx%d%n", width, height);
        System.out.printf("   Layers: %d%n", layers.size());
        System.out.printf("   Total drawing area: %.2f%n", getArea());
    }
}