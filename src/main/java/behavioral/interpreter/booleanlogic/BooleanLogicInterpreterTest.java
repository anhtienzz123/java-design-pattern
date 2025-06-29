package behavioral.interpreter.booleanlogic;

/**
 * Test class for the Boolean Logic Interpreter Pattern implementation.
 * Tests all components including terminal expressions, non-terminal expressions,
 * context functionality, and complex use cases.
 */
public class BooleanLogicInterpreterTest {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("🧪 Boolean Logic Interpreter - Test Suite");
        System.out.println("==========================================");
        
        // Run all test suites
        testTerminalExpressions();
        testNonTerminalExpressions();
        testContextFunctionality();
        testComplexExpressions();
        testPracticalUseCases();
        testEdgeCases();
        
        // Print summary
        printTestSummary();
    }
    
    private static void testTerminalExpressions() {
        System.out.println("\n📍 TESTING TERMINAL EXPRESSIONS");
        System.out.println("===============================");
        
        BooleanContext context = new BooleanContext();
        
        // Test VariableExpression
        System.out.println("\n--- Testing VariableExpression ---");
        context.setVariable("TEST_VAR", true);
        VariableExpression varExpr = new VariableExpression("TEST_VAR");
        
        assertBoolean("Variable expression should return true", 
                varExpr.interpret(context), true);
        assertString("Variable name should match", 
                varExpr.getVariableName(), "TEST_VAR");
        
        context.setVariable("FALSE_VAR", false);
        VariableExpression falseVarExpr = new VariableExpression("FALSE_VAR");
        assertBoolean("Variable expression should return false", 
                falseVarExpr.interpret(context), false);
        
        // Test LiteralExpression
        System.out.println("\n--- Testing LiteralExpression ---");
        LiteralExpression trueLiteral = new LiteralExpression(true);
        LiteralExpression falseLiteral = new LiteralExpression(false);
        
        assertBoolean("True literal should return true", 
                trueLiteral.interpret(context), true);
        assertBoolean("False literal should return false", 
                falseLiteral.interpret(context), false);
        assertString("True literal toString should be TRUE", 
                trueLiteral.toString(), "TRUE");
        assertString("False literal toString should be FALSE", 
                falseLiteral.toString(), "FALSE");
    }
    
    private static void testNonTerminalExpressions() {
        System.out.println("\n📍 TESTING NON-TERMINAL EXPRESSIONS");
        System.out.println("===================================");
        
        BooleanContext context = new BooleanContext();
        context.setVariable("A", true);
        context.setVariable("B", false);
        
        VariableExpression varA = new VariableExpression("A");
        VariableExpression varB = new VariableExpression("B");
        
        // Test AndExpression
        System.out.println("\n--- Testing AndExpression ---");
        AndExpression andExpr = new AndExpression(varA, varB);
        assertBoolean("true AND false should be false", 
                andExpr.interpret(context), false);
        
        context.setVariable("B", true);
        assertBoolean("true AND true should be true", 
                andExpr.interpret(context), true);
        
        // Test OrExpression
        System.out.println("\n--- Testing OrExpression ---");
        context.setVariable("B", false);
        OrExpression orExpr = new OrExpression(varA, varB);
        assertBoolean("true OR false should be true", 
                orExpr.interpret(context), true);
        
        context.setVariable("A", false);
        assertBoolean("false OR false should be false", 
                orExpr.interpret(context), false);
        
        // Test NotExpression
        System.out.println("\n--- Testing NotExpression ---");
        NotExpression notExpr = new NotExpression(varA);
        assertBoolean("NOT false should be true", 
                notExpr.interpret(context), true);
        
        context.setVariable("A", true);
        assertBoolean("NOT true should be false", 
                notExpr.interpret(context), false);
    }
    
    private static void testContextFunctionality() {
        System.out.println("\n📍 TESTING CONTEXT FUNCTIONALITY");
        System.out.println("================================");
        
        BooleanContext context = new BooleanContext();
        
        // Test variable setting and getting
        context.setVariable("VAR1", true);
        context.setVariable("VAR2", false);
        
        assertBoolean("Context should return correct variable value", 
                context.getVariable("VAR1"), true);
        assertBoolean("Context should return correct variable value", 
                context.getVariable("VAR2"), false);
        assertBoolean("Context should return false for non-existent variable", 
                context.getVariable("NON_EXISTENT"), false);
        
        // Test variable existence check
        assertBoolean("Context should detect existing variable", 
                context.hasVariable("VAR1"), true);
        assertBoolean("Context should detect non-existing variable", 
                context.hasVariable("NON_EXISTENT"), false);
        
        // Test case insensitivity
        context.setVariable("testVar", true);
        assertBoolean("Context should be case insensitive", 
                context.getVariable("TESTVAR"), true);
        
        // Test evaluation steps
        int initialSteps = context.getEvaluationSteps();
        context.incrementEvaluationSteps();
        assertInt("Evaluation steps should increment", 
                context.getEvaluationSteps(), initialSteps + 1);
        
        context.resetEvaluationSteps();
        assertInt("Evaluation steps should reset", 
                context.getEvaluationSteps(), 0);
        
        // Test clear functionality
        context.clear();
        assertBoolean("Context should be empty after clear", 
                context.getVariableNames().isEmpty(), true);
        assertInt("Evaluation steps should be reset after clear", 
                context.getEvaluationSteps(), 0);
    }
    
    private static void testComplexExpressions() {
        System.out.println("\n📍 TESTING COMPLEX EXPRESSIONS");
        System.out.println("==============================");
        
        BooleanContext context = new BooleanContext();
        context.setVariable("A", true);
        context.setVariable("B", false);
        context.setVariable("C", true);
        
        // Test (A AND B) OR C = (true AND false) OR true = false OR true = true
        BooleanExpression complexExpr1 = new OrExpression(
            new AndExpression(
                new VariableExpression("A"),
                new VariableExpression("B")
            ),
            new VariableExpression("C")
        );
        
        assertBoolean("(true AND false) OR true should be true", 
                complexExpr1.interpret(context), true);
        
        // Test A AND (B OR C) = true AND (false OR true) = true AND true = true
        BooleanExpression complexExpr2 = new AndExpression(
            new VariableExpression("A"),
            new OrExpression(
                new VariableExpression("B"),
                new VariableExpression("C")
            )
        );
        
        assertBoolean("true AND (false OR true) should be true", 
                complexExpr2.interpret(context), true);
        
        // Test NOT (A AND B) = NOT (true AND false) = NOT false = true
        BooleanExpression complexExpr3 = new NotExpression(
            new AndExpression(
                new VariableExpression("A"),
                new VariableExpression("B")
            )
        );
        
        assertBoolean("NOT (true AND false) should be true", 
                complexExpr3.interpret(context), true);
        
        // Test nested NOT expressions: NOT (NOT A) = NOT (NOT true) = NOT false = true
        BooleanExpression complexExpr4 = new NotExpression(
            new NotExpression(new VariableExpression("A"))
        );
        
        assertBoolean("NOT (NOT true) should be true", 
                complexExpr4.interpret(context), true);
    }
    
    private static void testPracticalUseCases() {
        System.out.println("\n📍 TESTING PRACTICAL USE CASES");
        System.out.println("==============================");
        
        // Test User Access Control
        System.out.println("\n--- Testing User Access Control ---");
        BooleanContext userContext = new BooleanContext();
        
        // Test case 1: Admin user (should have access)
        userContext.clear();
        userContext.setVariable("isAdmin", true);
        userContext.setVariable("isUser", false);
        userContext.setVariable("hasPermission", false);
        userContext.setVariable("isBlocked", false);
        
        BooleanExpression accessExpr = BooleanExpressionParser.createUserAccessExpression();
        assertBoolean("Admin user should have access", 
                accessExpr.interpret(userContext), true);
        
        // Test case 2: Regular user with permission (should have access)
        userContext.clear();
        userContext.setVariable("isAdmin", false);
        userContext.setVariable("isUser", true);
        userContext.setVariable("hasPermission", true);
        userContext.setVariable("isBlocked", false);
        
        assertBoolean("User with permission should have access", 
                accessExpr.interpret(userContext), true);
        
        // Test case 3: Blocked admin (should not have access)
        userContext.clear();
        userContext.setVariable("isAdmin", true);
        userContext.setVariable("isUser", false);
        userContext.setVariable("hasPermission", false);
        userContext.setVariable("isBlocked", true);
        
        assertBoolean("Blocked admin should not have access", 
                accessExpr.interpret(userContext), false);
        
        // Test Business Rules
        System.out.println("\n--- Testing Business Rules ---");
        BooleanContext businessContext = new BooleanContext();
        
        // Test case 1: Premium user with valid card (should pass)
        businessContext.clear();
        businessContext.setVariable("isPremium", true);
        businessContext.setVariable("hasValidCard", true);
        businessContext.setVariable("isTrialUser", false);
        businessContext.setVariable("trialExpired", false);
        
        BooleanExpression businessExpr = BooleanExpressionParser.createBusinessRuleExpression();
        assertBoolean("Premium user with valid card should pass", 
                businessExpr.interpret(businessContext), true);
        
        // Test case 2: Active trial user (should pass)
        businessContext.clear();
        businessContext.setVariable("isPremium", false);
        businessContext.setVariable("hasValidCard", false);
        businessContext.setVariable("isTrialUser", true);
        businessContext.setVariable("trialExpired", false);
        
        assertBoolean("Active trial user should pass", 
                businessExpr.interpret(businessContext), true);
        
        // Test case 3: Expired trial user (should not pass)
        businessContext.clear();
        businessContext.setVariable("isPremium", false);
        businessContext.setVariable("hasValidCard", false);
        businessContext.setVariable("isTrialUser", true);
        businessContext.setVariable("trialExpired", true);
        
        assertBoolean("Expired trial user should not pass", 
                businessExpr.interpret(businessContext), false);
    }
    
    private static void testEdgeCases() {
        System.out.println("\n📍 TESTING EDGE CASES");
        System.out.println("=====================");
        
        BooleanContext context = new BooleanContext();
        
        // Test undefined variables (should default to false)
        VariableExpression undefinedVar = new VariableExpression("UNDEFINED");
        assertBoolean("Undefined variable should default to false", 
                undefinedVar.interpret(context), false);
        
        // Test with all literals
        BooleanExpression allLiterals = new AndExpression(
            new LiteralExpression(true),
            new NotExpression(new LiteralExpression(false))
        );
        assertBoolean("true AND (NOT false) should be true", 
                allLiterals.interpret(context), true);
        
        // Test deeply nested expression
        BooleanExpression deeplyNested = new AndExpression(
            new OrExpression(
                new NotExpression(new LiteralExpression(false)),
                new LiteralExpression(true)
            ),
            new LiteralExpression(true)
        );
        
        assertBoolean("Deeply nested expression should evaluate correctly", 
                deeplyNested.interpret(context), true);
    }
    
    // Test utility methods
    private static void assertBoolean(String message, boolean actual, boolean expected) {
        totalTests++;
        if (actual == expected) {
            passedTests++;
            System.out.printf("✅ PASS: %s%n", message);
        } else {
            System.out.printf("❌ FAIL: %s (expected: %s, actual: %s)%n", message, expected, actual);
        }
    }
    
    private static void assertString(String message, String actual, String expected) {
        totalTests++;
        if (actual.equals(expected)) {
            passedTests++;
            System.out.printf("✅ PASS: %s%n", message);
        } else {
            System.out.printf("❌ FAIL: %s (expected: %s, actual: %s)%n", message, expected, actual);
        }
    }
    
    private static void assertInt(String message, int actual, int expected) {
        totalTests++;
        if (actual == expected) {
            passedTests++;
            System.out.printf("✅ PASS: %s%n", message);
        } else {
            System.out.printf("❌ FAIL: %s (expected: %d, actual: %d)%n", message, expected, actual);
        }
    }
    
    private static void printTestSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 TEST SUMMARY");
        System.out.println("=".repeat(50));
        System.out.printf("Total Tests: %d%n", totalTests);
        System.out.printf("Passed: %d%n", passedTests);
        System.out.printf("Failed: %d%n", totalTests - passedTests);
        System.out.printf("Success Rate: %.1f%%%n", (passedTests * 100.0) / totalTests);
        
        if (passedTests == totalTests) {
            System.out.println("🎉 ALL TESTS PASSED!");
        } else {
            System.out.println("⚠️  SOME TESTS FAILED");
        }
        System.out.println("=".repeat(50));
    }
} 