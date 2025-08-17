package structural.decorator.textformatter;

// Component: Defines the text interface
public interface Text {
    String getContent(); // Get formatted text content
    int getLength(); // Get text length (excluding formatting)
}