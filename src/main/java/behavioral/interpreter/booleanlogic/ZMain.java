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

//        == Output:
//        🔢 Boolean Logic Interpreter Pattern Demo
//        ==========================================
//
//        📍 BASIC EXPRESSIONS
//        ====================
//        Variables: A=true, B=false, C=true
//
//
//        🔍 Evaluating: A
//        Expression: A
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=0}
//          Evaluating variable 'A' = true
//        📊 RESULT: TRUE (evaluated in 1 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: B
//        Expression: B
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=1}
//          Evaluating variable 'B' = false
//        📊 RESULT: FALSE (evaluated in 1 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: A AND B
//        Expression: (A AND B)
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=1}
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = true
//          Evaluating variable 'B' = false
//          AND result: true AND false = false
//        📊 RESULT: FALSE (evaluated in 3 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: A OR B
//        Expression: (A OR B)
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=3}
//        Evaluating OR expression: (A) OR (B)
//          Evaluating variable 'A' = true
//          Short-circuit: Left operand is true, result = true
//        📊 RESULT: TRUE (evaluated in 2 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: NOT A
//        Expression: NOT (A)
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=2}
//        Evaluating NOT expression: NOT (A)
//          Evaluating variable 'A' = true
//          NOT result: NOT true = false
//        📊 RESULT: FALSE (evaluated in 2 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: NOT B
//        Expression: NOT (B)
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=2}
//        Evaluating NOT expression: NOT (B)
//          Evaluating variable 'B' = false
//          NOT result: NOT false = true
//        📊 RESULT: TRUE (evaluated in 2 steps)
//        ──────────────────────────────────────────────────
//
//        📍 COMPLEX EXPRESSIONS
//        =======================
//        Variables: A=true, B=false, C=true
//
//
//        🔍 Evaluating: (A AND B) OR C
//        Expression: ((A AND B) OR C)
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=0}
//        Evaluating OR expression: ((A AND B)) OR (C)
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = true
//          Evaluating variable 'B' = false
//          AND result: true AND false = false
//          Evaluating variable 'C' = true
//          OR result: false OR true = true
//        📊 RESULT: TRUE (evaluated in 5 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: A AND (B OR C)
//        Expression: (A AND (B OR C))
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=5}
//        Evaluating AND expression: (A) AND ((B OR C))
//          Evaluating variable 'A' = true
//        Evaluating OR expression: (B) OR (C)
//          Evaluating variable 'B' = false
//          Evaluating variable 'C' = true
//          OR result: false OR true = true
//          AND result: true AND true = true
//        📊 RESULT: TRUE (evaluated in 5 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: NOT (A AND B)
//        Expression: NOT ((A AND B))
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=5}
//        Evaluating NOT expression: NOT ((A AND B))
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = true
//          Evaluating variable 'B' = false
//          AND result: true AND false = false
//          NOT result: NOT false = true
//        📊 RESULT: TRUE (evaluated in 4 steps)
//        ──────────────────────────────────────────────────
//
//        🔍 Evaluating: (A OR B) AND (NOT C)
//        Expression: ((A OR B) AND NOT (C))
//        Context: BooleanContext{variables={A=true, B=false, C=true}, steps=4}
//        Evaluating AND expression: ((A OR B)) AND (NOT (C))
//        Evaluating OR expression: (A) OR (B)
//          Evaluating variable 'A' = true
//          Short-circuit: Left operand is true, result = true
//        Evaluating NOT expression: NOT (C)
//          Evaluating variable 'C' = true
//          NOT result: NOT true = false
//          AND result: true AND false = false
//        📊 RESULT: FALSE (evaluated in 5 steps)
//        ──────────────────────────────────────────────────
//
//        📍 PRACTICAL USE CASES
//        ======================
//        1. User Access Control System
//        Expression: (isAdmin OR (isUser AND hasPermission)) AND NOT isBlocked
//
//        --- Scenario 1: Admin User ---
//
//        🔍 Evaluating: User Access
//        Expression: ((ISADMIN OR (ISUSER AND HASPERMISSION)) AND NOT (ISBLOCKED))
//        Context: BooleanContext{variables={HASPERMISSION=false, ISADMIN=true, ISBLOCKED=false, ISUSER=false}, steps=0}
//        Evaluating AND expression: ((ISADMIN OR (ISUSER AND HASPERMISSION))) AND (NOT (ISBLOCKED))
//        Evaluating OR expression: (ISADMIN) OR ((ISUSER AND HASPERMISSION))
//          Evaluating variable 'ISADMIN' = true
//          Short-circuit: Left operand is true, result = true
//        Evaluating NOT expression: NOT (ISBLOCKED)
//          Evaluating variable 'ISBLOCKED' = false
//          NOT result: NOT false = true
//          AND result: true AND true = true
//        📊 RESULT: TRUE (evaluated in 5 steps)
//        ──────────────────────────────────────────────────
//
//        --- Scenario 2: Regular User with Permission ---
//
//        🔍 Evaluating: User Access
//        Expression: ((ISADMIN OR (ISUSER AND HASPERMISSION)) AND NOT (ISBLOCKED))
//        Context: BooleanContext{variables={HASPERMISSION=true, ISADMIN=false, ISBLOCKED=false, ISUSER=true}, steps=0}
//        Evaluating AND expression: ((ISADMIN OR (ISUSER AND HASPERMISSION))) AND (NOT (ISBLOCKED))
//        Evaluating OR expression: (ISADMIN) OR ((ISUSER AND HASPERMISSION))
//          Evaluating variable 'ISADMIN' = false
//        Evaluating AND expression: (ISUSER) AND (HASPERMISSION)
//          Evaluating variable 'ISUSER' = true
//          Evaluating variable 'HASPERMISSION' = true
//          AND result: true AND true = true
//          OR result: false OR true = true
//        Evaluating NOT expression: NOT (ISBLOCKED)
//          Evaluating variable 'ISBLOCKED' = false
//          NOT result: NOT false = true
//          AND result: true AND true = true
//        📊 RESULT: TRUE (evaluated in 8 steps)
//        ──────────────────────────────────────────────────
//
//        --- Scenario 3: Blocked Admin ---
//
//        🔍 Evaluating: User Access
//        Expression: ((ISADMIN OR (ISUSER AND HASPERMISSION)) AND NOT (ISBLOCKED))
//        Context: BooleanContext{variables={HASPERMISSION=false, ISADMIN=true, ISBLOCKED=true, ISUSER=false}, steps=0}
//        Evaluating AND expression: ((ISADMIN OR (ISUSER AND HASPERMISSION))) AND (NOT (ISBLOCKED))
//        Evaluating OR expression: (ISADMIN) OR ((ISUSER AND HASPERMISSION))
//          Evaluating variable 'ISADMIN' = true
//          Short-circuit: Left operand is true, result = true
//        Evaluating NOT expression: NOT (ISBLOCKED)
//          Evaluating variable 'ISBLOCKED' = true
//          NOT result: NOT true = false
//          AND result: true AND false = false
//        📊 RESULT: FALSE (evaluated in 5 steps)
//        ──────────────────────────────────────────────────
//
//
//        2. Business Rule Evaluation
//        Expression: (isPremium AND hasValidCard) OR (isTrialUser AND NOT trialExpired)
//
//        --- Scenario 1: Premium User ---
//
//        🔍 Evaluating: Business Rule
//        Expression: ((ISPREMIUM AND HASVALIDCARD) OR (ISTRIALUSER AND NOT (TRIALEXPIRED)))
//        Context: BooleanContext{variables={ISTRIALUSER=false, TRIALEXPIRED=false, ISPREMIUM=true, HASVALIDCARD=true}, steps=0}
//        Evaluating OR expression: ((ISPREMIUM AND HASVALIDCARD)) OR ((ISTRIALUSER AND NOT (TRIALEXPIRED)))
//        Evaluating AND expression: (ISPREMIUM) AND (HASVALIDCARD)
//          Evaluating variable 'ISPREMIUM' = true
//          Evaluating variable 'HASVALIDCARD' = true
//          AND result: true AND true = true
//          Short-circuit: Left operand is true, result = true
//        📊 RESULT: TRUE (evaluated in 4 steps)
//        ──────────────────────────────────────────────────
//
//        --- Scenario 2: Active Trial User ---
//
//        🔍 Evaluating: Business Rule
//        Expression: ((ISPREMIUM AND HASVALIDCARD) OR (ISTRIALUSER AND NOT (TRIALEXPIRED)))
//        Context: BooleanContext{variables={ISTRIALUSER=true, TRIALEXPIRED=false, ISPREMIUM=false, HASVALIDCARD=false}, steps=0}
//        Evaluating OR expression: ((ISPREMIUM AND HASVALIDCARD)) OR ((ISTRIALUSER AND NOT (TRIALEXPIRED)))
//        Evaluating AND expression: (ISPREMIUM) AND (HASVALIDCARD)
//          Evaluating variable 'ISPREMIUM' = false
//          Short-circuit: Left operand is false, result = false
//        Evaluating AND expression: (ISTRIALUSER) AND (NOT (TRIALEXPIRED))
//          Evaluating variable 'ISTRIALUSER' = true
//        Evaluating NOT expression: NOT (TRIALEXPIRED)
//          Evaluating variable 'TRIALEXPIRED' = false
//          NOT result: NOT false = true
//          AND result: true AND true = true
//          OR result: false OR true = true
//        📊 RESULT: TRUE (evaluated in 7 steps)
//        ──────────────────────────────────────────────────
//
//        📍 DE MORGAN'S LAWS VERIFICATION
//        =================================
//        Verifying: NOT (A AND B) = (NOT A) OR (NOT B)
//
//        Testing with A=true, B=true:
//        Evaluating NOT expression: NOT ((A AND B))
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = true
//          Evaluating variable 'B' = true
//          AND result: true AND true = true
//          NOT result: NOT true = false
//        Evaluating OR expression: (NOT (A)) OR (NOT (B))
//        Evaluating NOT expression: NOT (A)
//          Evaluating variable 'A' = true
//          NOT result: NOT true = false
//        Evaluating NOT expression: NOT (B)
//          Evaluating variable 'B' = true
//          NOT result: NOT true = false
//          OR result: false OR false = false
//        Results: NOT(A AND B)=false, (NOT A) OR (NOT B)=false -> ✓ EQUAL
//
//        Testing with A=true, B=false:
//        Evaluating NOT expression: NOT ((A AND B))
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = true
//          Evaluating variable 'B' = false
//          AND result: true AND false = false
//          NOT result: NOT false = true
//        Evaluating OR expression: (NOT (A)) OR (NOT (B))
//        Evaluating NOT expression: NOT (A)
//          Evaluating variable 'A' = true
//          NOT result: NOT true = false
//        Evaluating NOT expression: NOT (B)
//          Evaluating variable 'B' = false
//          NOT result: NOT false = true
//          OR result: false OR true = true
//        Results: NOT(A AND B)=true, (NOT A) OR (NOT B)=true -> ✓ EQUAL
//
//        Testing with A=false, B=true:
//        Evaluating NOT expression: NOT ((A AND B))
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = false
//          Short-circuit: Left operand is false, result = false
//          NOT result: NOT false = true
//        Evaluating OR expression: (NOT (A)) OR (NOT (B))
//        Evaluating NOT expression: NOT (A)
//          Evaluating variable 'A' = false
//          NOT result: NOT false = true
//          Short-circuit: Left operand is true, result = true
//        Results: NOT(A AND B)=true, (NOT A) OR (NOT B)=true -> ✓ EQUAL
//
//        Testing with A=false, B=false:
//        Evaluating NOT expression: NOT ((A AND B))
//        Evaluating AND expression: (A) AND (B)
//          Evaluating variable 'A' = false
//          Short-circuit: Left operand is false, result = false
//          NOT result: NOT false = true
//        Evaluating OR expression: (NOT (A)) OR (NOT (B))
//        Evaluating NOT expression: NOT (A)
//          Evaluating variable 'A' = false
//          NOT result: NOT false = true
//          Short-circuit: Left operand is true, result = true
//        Results: NOT(A AND B)=true, (NOT A) OR (NOT B)=true -> ✓ EQUAL
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