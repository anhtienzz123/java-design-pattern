package creational.prototype.graphicseditor;

import java.util.HashMap;
import java.util.Map;

//Prototype Registry
public class ShapeRegistry {
	private Map<String, Shape> prototypes = new HashMap<>();

	public void addPrototype(String key, Shape prototype) {
		prototypes.put(key, prototype);
	}

	public Shape getPrototype(String key) {
		Shape prototype = prototypes.get(key);
		if (prototype == null) {
			throw new IllegalArgumentException("No prototype found for key: " + key);
		}
		return prototype.clone(); // Return a clone, not the original
	}
}
