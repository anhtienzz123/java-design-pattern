package structural.decorator.textformatter;

// ConcreteComponent: Basic plain text implementation
public class PlainText implements Text {
    private final String content;

    public PlainText(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getLength() {
        return content.length();
    }
}