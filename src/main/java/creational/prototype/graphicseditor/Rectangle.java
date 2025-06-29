package creational.prototype.graphicseditor;

import java.util.ArrayList;
import java.util.List;

//Concrete Prototype: Rectangle
public class Rectangle implements Shape {
	private String id;
	private int width;
	private int height;
	private List<String> properties;

	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
		this.properties = new ArrayList<>();
	}

	// Deep copy implementation
	@Override
	public Shape clone() {
		try {
			Rectangle clone = (Rectangle) super.clone();
			clone.properties = new ArrayList<>(this.properties);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone not supported", e);
		}
	}

	@Override
	public void draw() {
		System.out.println("Drawing Rectangle [id=" + id + ", width=" + width + ", height=" + height + ", properties="
				+ properties + "]");
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	// For customization
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void addProperty(String property) {
		this.properties.add(property);
	}
}
