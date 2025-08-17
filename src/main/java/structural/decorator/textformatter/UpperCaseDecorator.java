package structural.decorator.textformatter;

// ConcreteDecorator: Converts text to uppercase
public class UpperCaseDecorator extends TextDecorator {

    public UpperCaseDecorator(Text text) {
        super(text);
    }

    @Override
    public String getContent() {
        return decoratedText.getContent().toUpperCase();
    }
}