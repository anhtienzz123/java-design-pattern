package behavioral.interpreter.simplearithmeticexpressionlanguage;

// Terminal Expression: Represents a number literal
public class NumberExpression implements Expression {
	private int value;

	public NumberExpression(int value) {
		this.value = value;
	}

	@Override
	public int interpret(Context context) {
		return value;
	}
}
