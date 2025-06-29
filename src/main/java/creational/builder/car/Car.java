package creational.builder.car;

//Product: Car
public class Car {
	private final String model; // Required
	private final String color; // Required
	private final String engine; // Optional
	private final boolean hasSunroof; // Optional

	// Private constructor to enforce use of Builder
	private Car(Builder builder) {
		this.model = builder.model;
		this.color = builder.color;
		this.engine = builder.engine;
		this.hasSunroof = builder.hasSunroof;
	}

	// Getters (no setters for immutability)
	public String getModel() {
		return model;
	}

	public String getColor() {
		return color;
	}

	public String getEngine() {
		return engine;
	}

	public boolean hasSunroof() {
		return hasSunroof;
	}

	@Override
	public String toString() {
		return "Car [model=" + model + ", color=" + color + ", engine=" + engine + ", hasSunroof=" + hasSunroof + "]";
	}

	// Static nested Builder class
	public static class Builder {
		private final String model; // Required
		private final String color; // Required
		private String engine; // Optional
		private boolean hasSunroof; // Optional

		// Constructor for required fields
		public Builder(String model, String color) {
			this.model = model;
			this.color = color;
		}

		// Fluent methods for optional fields
		public Builder engine(String engine) {
			this.engine = engine;
			return this;
		}

		public Builder hasSunroof(boolean hasSunroof) {
			this.hasSunroof = hasSunroof;
			return this;
		}

		// Build method to create the Product
		public Car build() {
			// Optional: Add validation
			if (model == null || model.isEmpty()) {
				throw new IllegalStateException("Model cannot be empty");
			}
			if (color == null || color.isEmpty()) {
				throw new IllegalStateException("Color cannot be empty");
			}
			return new Car(this);
		}
	}
}
