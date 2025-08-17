package structural.flyweight.texteditor;

// Client code
public class ZMain {
    public static void main(String[] args) {
        // Create flyweight factory
        CharacterFlyweightFactory factory = new CharacterFlyweightFactory();

        // Simulate rendering a document with repeated characters
        String text = "hello world"; // Text to render
        int x = 0, y = 0; // Starting position

        for (char c : text.toCharArray()) {
            // Get flyweight for character 'c' with Arial font, size 12
            CharacterFlyweight flyweight = factory.getFlyweight(c, "Arial", 12);
            flyweight.render(x, y); // Pass extrinsic state (position)
            x += 10; // Move to next position
        }

        // Render some characters again with different positions
        System.out.println("\nRendering additional characters:");
        factory.getFlyweight('h', "Arial", 12).render(100, 200);
        factory.getFlyweight('e', "Arial", 12).render(110, 200);

        // Show total flyweights created
        System.out.println("\nTotal flyweights created: " + factory.getFlyweightCount());
    }
}
