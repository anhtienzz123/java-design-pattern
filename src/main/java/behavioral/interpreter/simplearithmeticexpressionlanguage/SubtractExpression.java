package behavioral.interpreter.simplearithmeticexpressionlanguage;

// Non-Terminal Expression: Represents subtraction
public class SubtractExpression implements Expression {
	private Expression left;
	private Expression right;

	public SubtractExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int interpret(Context context) {
		return left.interpret(context) - right.interpret(context);
	}
}
