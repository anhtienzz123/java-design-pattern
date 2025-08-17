package structural.decorator.textformatter;

// Decorator: Abstract base class for text decorators
public abstract class TextDecorator implements Text {
    protected final Text decoratedText;

    public TextDecorator(Text text) {
        this.decoratedText = text;
    }

    @Override
    public String getContent() {
        return decoratedText.getContent();
    }

    @Override
    public int getLength() {
        return decoratedText.getLength();
    }
}