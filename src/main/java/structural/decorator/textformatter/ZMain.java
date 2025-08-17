package structural.decorator.textformatter;

// Client code demonstrating text formatting with decorators
public class ZMain {
    public static void main(String[] args) {
        // Create plain text
        Text plainText = new PlainText("Hello World");
        System.out.println("Plain Text: " + plainText.getContent());
        System.out.println("Length: " + plainText.getLength());
        System.out.println();

        // Add bold formatting
        Text boldText = new BoldDecorator(plainText);
        System.out.println("Bold Text: " + boldText.getContent());
        System.out.println("Original Length: " + boldText.getLength());
        System.out.println();

        // Add bold and italic formatting
        Text boldItalicText = new ItalicDecorator(new BoldDecorator(plainText));
        System.out.println("Bold + Italic Text: " + boldItalicText.getContent());
        System.out.println("Original Length: " + boldItalicText.getLength());
        System.out.println();

        // Add bold, italic, and underline formatting
        Text tripleFormatText = new UnderlineDecorator(
            new ItalicDecorator(new BoldDecorator(plainText))
        );
        System.out.println("Bold + Italic + Underline Text: " + tripleFormatText.getContent());
        System.out.println("Original Length: " + tripleFormatText.getLength());
        System.out.println();

        // Add color formatting
        Text coloredText = new ColorDecorator(plainText, "red");
        System.out.println("Colored Text: " + coloredText.getContent());
        System.out.println("Original Length: " + coloredText.getLength());
        System.out.println();

        // Complex formatting: Bold, Italic, Red color, and Uppercase
        Text complexFormatText = new UpperCaseDecorator(
            new ColorDecorator(
                new ItalicDecorator(
                    new BoldDecorator(plainText)
                ), "blue"
            )
        );
        System.out.println("Complex Formatted Text: " + complexFormatText.getContent());
        System.out.println("Original Length: " + complexFormatText.getLength());
        System.out.println();

        // Demonstrate flexibility - different order of decorators
        Text alternativeFormat = new BoldDecorator(
            new ColorDecorator(
                new UnderlineDecorator(new PlainText("Design Patterns")), "green"
            )
        );
        System.out.println("Alternative Format: " + alternativeFormat.getContent());
        System.out.println("Original Length: " + alternativeFormat.getLength());
    }
}