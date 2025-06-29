package creational.prototype.graphicseditor;

import java.util.ArrayList;
import java.util.List;

//Concrete Prototype: Circle
public class Circle implements Shape {
	private String id;
	private int radius;
	private List<String> properties; // Nested object to demonstrate deep copy

	public Circle(int radius) {
		this.radius = radius;
		this.properties = new ArrayList<>();
	}

	// Deep copy implementation
	@Override
	public Shape clone() {
		try {
			Circle clone = (Circle) super.clone(); // Shallow copy via Object.clone()
			// Deep copy for nested objects
			clone.properties = new ArrayList<>(this.properties);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone not supported", e);
		}
	}

	@Override
	public void draw() {
		System.out.println("Drawing Circle [id=" + id + ", radius=" + radius + ", properties=" + properties + "]");
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
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void addProperty(String property) {
		this.properties.add(property);
	}
}