package structural.flyweight.texteditor;

// Flyweight: Defines operations for character rendering
public interface CharacterFlyweight {
	void render(int x, int y); // Extrinsic state: position (x, y)
}