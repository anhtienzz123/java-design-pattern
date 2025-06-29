package structural.flyweight.texteditor;

// Concrete Flyweight: Stores intrinsic state (character, font, size)
public class ConcreteCharacterFlyweight implements CharacterFlyweight {
	private char character; // Intrinsic state
	private String font;
	private int fontSize;

	public ConcreteCharacterFlyweight(char character, String font, int fontSize) {
		this.character = character;
		this.font = font;
		this.fontSize = fontSize;
	}

	@Override
	public void render(int x, int y) {
		System.out.println("Rendering character '" + character + "' at position (" + x + ", " + y + ") with font "
				+ font + ", size " + fontSize);
	}
}
