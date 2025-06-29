package behavioral.interpreter.simplearithmeticexpressionlanguage;

// Terminal Expression: Represents a variable
public class VariableExpression implements Expression {
	private String name;

	public VariableExpression(String name) {
		this.name = name;
	}

	@Override
	public int interpret(Context context) {
		return context.getVariable(name);
	}
}