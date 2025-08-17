package behavioral.interpreter.simplearithmeticexpressionlanguage;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create context with variable values
		Context context = new Context();
		context.setVariable("x", 10);
		context.setVariable("y", 3);

		// Build expression: x + 5 - y
		// Equivalent to: (x + 5) - y
		Expression expression = new SubtractExpression(
				new AddExpression(new VariableExpression("x"), new NumberExpression(5)), new VariableExpression("y"));

		// Interpret the expression
		int result = expression.interpret(context);
		System.out.println("Expression (x + 5 - y) with x=10, y=3 evaluates to: " + result);

		// Test another expression: y - 2
		Expression anotherExpression = new SubtractExpression(new VariableExpression("y"), new NumberExpression(2));
		int anotherResult = anotherExpression.interpret(context);
		System.out.println("Expression (y - 2) with y=3 evaluates to: " + anotherResult);
	}
}
