package structural.decorator.textformatter;

// ConcreteDecorator: Adds color formatting to text
public class ColorDecorator extends TextDecorator {
    private final String color;

    public ColorDecorator(Text text, String color) {
        super(text);
        this.color = color;
    }

    @Override
    public String getContent() {
        return "<span style=\"color:" + color + "\">" + decoratedText.getContent() + "</span>";
    }
}