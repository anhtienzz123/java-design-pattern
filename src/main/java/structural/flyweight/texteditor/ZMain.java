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
        
//        == Output:
//        Created flyweight for key: h_Arial_12
//        Rendering character 'h' at position (0, 0) with font Arial, size 12
//        Created flyweight for key: e_Arial_12
//        Rendering character 'e' at position (10, 0) with font Arial, size 12
//        Created flyweight for key: l_Arial_12
//        Rendering character 'l' at position (20, 0) with font Arial, size 12
//        Rendering character 'l' at position (30, 0) with font Arial, size 12
//        Created flyweight for key: o_Arial_12
//        Rendering character 'o' at position (40, 0) with font Arial, size 12
//        Created flyweight for key:  _Arial_12
//        Rendering character ' ' at position (50, 0) with font Arial, size 12
//        Created flyweight for key: w_Arial_12
//        Rendering character 'w' at position (60, 0) with font Arial, size 12
//        Rendering character 'o' at position (70, 0) with font Arial, size 12
//        Created flyweight for key: r_Arial_12
//        Rendering character 'r' at position (80, 0) with font Arial, size 12
//        Rendering character 'l' at position (90, 0) with font Arial, size 12
//        Created flyweight for key: d_Arial_12
//        Rendering character 'd' at position (100, 0) with font Arial, size 12
//
//        Rendering additional characters:
//        Rendering character 'h' at position (100, 200) with font Arial, size 12
//        Rendering character 'e' at position (110, 200) with font Arial, size 12
//
//        Total flyweights created: 8
    }
}
