package structural.flyweight.texteditor;

import java.util.HashMap;
import java.util.Map;

// Flyweight Factory: Manages shared flyweight objects
public class CharacterFlyweightFactory {
	private Map<String, CharacterFlyweight> flyweights = new HashMap<>();

	public CharacterFlyweight getFlyweight(char character, String font, int fontSize) {
		// Create a unique key for the intrinsic state
		String key = character + "_" + font + "_" + fontSize;

		// Return existing flyweight or create a new one
		if (!flyweights.containsKey(key)) {
			flyweights.put(key, new ConcreteCharacterFlyweight(character, font, fontSize));
			System.out.println("Created flyweight for key: " + key);
		}
		return flyweights.get(key);
	}

	public int getFlyweightCount() {
		return flyweights.size();
	}
}
