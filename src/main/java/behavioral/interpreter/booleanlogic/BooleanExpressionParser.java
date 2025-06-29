package behavioral.interpreter.booleanlogic;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple parser that helps build boolean expressions.
 * This is not part of the core Interpreter pattern but provides utility
 * to create expressions more easily for demonstration purposes.
 */
public class BooleanExpressionParser {
    
    /**
     * Creates pre-built example expressions for demonstration.
     * In a real implementation, this would parse string expressions.
     */
    public static Map<String, BooleanExpression> getExampleExpressions() {
        Map<String, BooleanExpression> expressions = new HashMap<>();
        
        // Simple variable expressions
        expressions.put("A", new VariableExpression("A"));
        expressions.put("B", new VariableExpression("B"));
        expressions.put("C", new VariableExpression("C"));
        
        // Literal expressions
        expressions.put("TRUE", new LiteralExpression(true));
        expressions.put("FALSE", new LiteralExpression(false));
        
        // Simple binary operations
        expressions.put("A AND B", 
            new AndExpression(
                new VariableExpression("A"), 
                new VariableExpression("B")
            )
        );
        
        expressions.put("A OR B", 
            new OrExpression(
                new VariableExpression("A"), 
                new VariableExpression("B")
            )
        );
        
        expressions.put("NOT A", 
            new NotExpression(new VariableExpression("A"))
        );
        
        expressions.put("NOT B", 
            new NotExpression(new VariableExpression("B"))
        );
        
        // Complex expressions
        expressions.put("(A AND B) OR C", 
            new OrExpression(
                new AndExpression(
                    new VariableExpression("A"), 
                    new VariableExpression("B")
                ),
                new VariableExpression("C")
            )
        );
        
        expressions.put("A AND (B OR C)", 
            new AndExpression(
                new VariableExpression("A"),
                new OrExpression(
                    new VariableExpression("B"), 
                    new VariableExpression("C")
                )
            )
        );
        
        expressions.put("NOT (A AND B)", 
            new NotExpression(
                new AndExpression(
                    new VariableExpression("A"), 
                    new VariableExpression("B")
                )
            )
        );
        
        expressions.put("(A OR B) AND (NOT C)", 
            new AndExpression(
                new OrExpression(
                    new VariableExpression("A"), 
                    new VariableExpression("B")
                ),
                new NotExpression(new VariableExpression("C"))
            )
        );
        
        // De Morgan's Law example: NOT (A AND B) = (NOT A) OR (NOT B)
        expressions.put("NOT (A AND B) - De Morgan", 
            new NotExpression(
                new AndExpression(
                    new VariableExpression("A"), 
                    new VariableExpression("B")
                )
            )
        );
        
        expressions.put("(NOT A) OR (NOT B) - De Morgan", 
            new OrExpression(
                new NotExpression(new VariableExpression("A")),
                new NotExpression(new VariableExpression("B"))
            )
        );
        
        return expressions;
    }
    
    /**
     * Creates a user access control expression example.
     * Demonstrates practical use case for boolean logic interpreter.
     */
    public static BooleanExpression createUserAccessExpression() {
        // Expression: (isAdmin OR (isUser AND hasPermission)) AND NOT isBlocked
        return new AndExpression(
            new OrExpression(
                new VariableExpression("isAdmin"),
                new AndExpression(
                    new VariableExpression("isUser"),
                    new VariableExpression("hasPermission")
                )
            ),
            new NotExpression(new VariableExpression("isBlocked"))
        );
    }
    
    /**
     * Creates a business rule expression example.
     * Demonstrates another practical use case.
     */
    public static BooleanExpression createBusinessRuleExpression() {
        // Expression: (isPremium AND hasValidCard) OR (isTrialUser AND NOT trialExpired)
        return new OrExpression(
            new AndExpression(
                new VariableExpression("isPremium"),
                new VariableExpression("hasValidCard")
            ),
            new AndExpression(
                new VariableExpression("isTrialUser"),
                new NotExpression(new VariableExpression("trialExpired"))
            )
        );
    }
} 