package behavioral.interpreter.booleanlogic;

import java.util.Map;

/**
 * Demonstration class for the Boolean Logic Interpreter Pattern. Shows various
 * use cases including simple logic, complex expressions, user access control,
 * and business rules evaluation.
 */
public class ZMain {

	public static void main(String[] args) {
		System.out.println("🔢 Boolean Logic Interpreter Pattern Demo");
		System.out.println("==========================================");

		// Demonstrate basic expressions
		demonstrateBasicExpressions();

		// Demonstrate complex expressions
		demonstrateComplexExpressions();

		// Demonstrate practical use cases
		demonstratePracticalUseCases();

		// Demonstrate De Morgan's Laws
		demonstrateDeMorganLaws();
	}

	private static void demonstrateBasicExpressions() {
		System.out.println("\n📍 BASIC EXPRESSIONS");
		System.out.println("====================");

		BooleanContext context = new BooleanContext();
		context.setVariable("A", true);
		context.setVariable("B", false);
		context.setVariable("C", true);

		System.out.println("Variables: A=true, B=false, C=true\n");

		// Test basic operations
		String[] basicTests = { "A", "B", "A AND B", "A OR B", "NOT A", "NOT B" };

		Map<String, BooleanExpression> expressions = BooleanExpressionParser.getExampleExpressions();

		for (String test : basicTests) {
			if (expressions.containsKey(test)) {
				evaluateExpression(test, expressions.get(test), context);
			}
		}
	}

	private static void demonstrateComplexExpressions() {
		System.out.println("\n📍 COMPLEX EXPRESSIONS");
		System.out.println("=======================");

		BooleanContext context = new BooleanContext();
		context.setVariable("A", true);
		context.setVariable("B", false);
		context.setVariable("C", true);

		System.out.println("Variables: A=true, B=false, C=true\n");

		Map<String, BooleanExpression> expressions = BooleanExpressionParser.getExampleExpressions();

		String[] complexTests = { "(A AND B) OR C", "A AND (B OR C)", "NOT (A AND B)", "(A OR B) AND (NOT C)" };

		for (String test : complexTests) {
			if (expressions.containsKey(test)) {
				evaluateExpression(test, expressions.get(test), context);
			}
		}
	}

	private static void demonstratePracticalUseCases() {
		System.out.println("\n📍 PRACTICAL USE CASES");
		System.out.println("======================");

		// User Access Control Example
		System.out.println("1. User Access Control System");
		System.out.println("Expression: (isAdmin OR (isUser AND hasPermission)) AND NOT isBlocked");

		BooleanContext userContext = new BooleanContext();

		// Scenario 1: Admin user
		System.out.println("\n--- Scenario 1: Admin User ---");
		userContext.clear();
		userContext.setVariable("isAdmin", true);
		userContext.setVariable("isUser", false);
		userContext.setVariable("hasPermission", false);
		userContext.setVariable("isBlocked", false);

		BooleanExpression accessExpression = BooleanExpressionParser.createUserAccessExpression();
		evaluateExpression("User Access", accessExpression, userContext);

		// Scenario 2: Regular user with permission
		System.out.println("\n--- Scenario 2: Regular User with Permission ---");
		userContext.clear();
		userContext.setVariable("isAdmin", false);
		userContext.setVariable("isUser", true);
		userContext.setVariable("hasPermission", true);
		userContext.setVariable("isBlocked", false);

		evaluateExpression("User Access", accessExpression, userContext);

		// Scenario 3: Blocked admin (should be denied)
		System.out.println("\n--- Scenario 3: Blocked Admin ---");
		userContext.clear();
		userContext.setVariable("isAdmin", true);
		userContext.setVariable("isUser", false);
		userContext.setVariable("hasPermission", false);
		userContext.setVariable("isBlocked", true);

		evaluateExpression("User Access", accessExpression, userContext);

		// Business Rule Example
		System.out.println("\n\n2. Business Rule Evaluation");
		System.out.println("Expression: (isPremium AND hasValidCard) OR (isTrialUser AND NOT trialExpired)");

		BooleanContext businessContext = new BooleanContext();

		// Scenario 1: Premium user with valid card
		System.out.println("\n--- Scenario 1: Premium User ---");
		businessContext.clear();
		businessContext.setVariable("isPremium", true);
		businessContext.setVariable("hasValidCard", true);
		businessContext.setVariable("isTrialUser", false);
		businessContext.setVariable("trialExpired", false);

		BooleanExpression businessExpression = BooleanExpressionParser.createBusinessRuleExpression();
		evaluateExpression("Business Rule", businessExpression, businessContext);

		// Scenario 2: Trial user with active trial
		System.out.println("\n--- Scenario 2: Active Trial User ---");
		businessContext.clear();
		businessContext.setVariable("isPremium", false);
		businessContext.setVariable("hasValidCard", false);
		businessContext.setVariable("isTrialUser", true);
		businessContext.setVariable("trialExpired", false);

		evaluateExpression("Business Rule", businessExpression, businessContext);
	}

	private static void demonstrateDeMorganLaws() {
		System.out.println("\n📍 DE MORGAN'S LAWS VERIFICATION");
		System.out.println("=================================");
		System.out.println("Verifying: NOT (A AND B) = (NOT A) OR (NOT B)");

		BooleanContext context = new BooleanContext();
		Map<String, BooleanExpression> expressions = BooleanExpressionParser.getExampleExpressions();

		// Test all combinations of A and B
		boolean[][] testCases = { { true, true }, { true, false }, { false, true }, { false, false } };

		for (boolean[] testCase : testCases) {
			boolean a = testCase[0];
			boolean b = testCase[1];

			System.out.printf("\nTesting with A=%s, B=%s:%n", a, b);
			context.clear();
			context.setVariable("A", a);
			context.setVariable("B", b);

			boolean result1 = expressions.get("NOT (A AND B) - De Morgan").interpret(context);
			context.resetEvaluationSteps(); // Reset for clean output
			boolean result2 = expressions.get("(NOT A) OR (NOT B) - De Morgan").interpret(context);

			System.out.printf("Results: NOT(A AND B)=%s, (NOT A) OR (NOT B)=%s -> %s%n", result1, result2,
					result1 == result2 ? "✓ EQUAL" : "✗ NOT EQUAL");
		}
	}

	private static void evaluateExpression(String name, BooleanExpression expression, BooleanContext context) {
		System.out.printf("\n🔍 Evaluating: %s%n", name);
		System.out.printf("Expression: %s%n", expression.toString());
		System.out.printf("Context: %s%n", context.toString());

		context.resetEvaluationSteps();
		boolean result = expression.interpret(context);

		System.out.printf("📊 RESULT: %s (evaluated in %d steps)%n", result ? "TRUE" : "FALSE",
				context.getEvaluationSteps());
		System.out.println("─".repeat(50));
	}
}