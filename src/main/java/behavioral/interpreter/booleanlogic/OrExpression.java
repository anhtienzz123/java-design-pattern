package behavioral.interpreter.booleanlogic;

/**
 * Non-terminal expression that represents the logical OR operation.
 * Evaluates to true if at least one of the left or right expressions is true.
 */
public class OrExpression implements BooleanExpression {
    private final BooleanExpression leftExpression;
    private final BooleanExpression rightExpression;
    
    /**
     * Creates a new OR expression.
     * 
     * @param leftExpression The left operand expression
     * @param rightExpression The right operand expression
     */
    public OrExpression(BooleanExpression leftExpression, BooleanExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }
    
    @Override
    public boolean interpret(BooleanContext context) {
        context.incrementEvaluationSteps();
        
        System.out.printf("Evaluating OR expression: (%s) OR (%s)%n", 
                leftExpression.toString(), rightExpression.toString());
        
        // Short-circuit evaluation: if left is true, don't evaluate right
        boolean leftResult = leftExpression.interpret(context);
        if (leftResult) {
            System.out.printf("  Short-circuit: Left operand is true, result = true%n");
            return true;
        }
        
        boolean rightResult = rightExpression.interpret(context);
        boolean result = leftResult || rightResult;
        
        System.out.printf("  OR result: %s OR %s = %s%n", leftResult, rightResult, result);
        
        return result;
    }
    
    /**
     * Gets the left expression.
     * 
     * @return The left expression
     */
    public BooleanExpression getLeftExpression() {
        return leftExpression;
    }
    
    /**
     * Gets the right expression.
     * 
     * @return The right expression
     */
    public BooleanExpression getRightExpression() {
        return rightExpression;
    }
    
    @Override
    public String toString() {
        return String.format("(%s OR %s)", leftExpression.toString(), rightExpression.toString());
    }
} 