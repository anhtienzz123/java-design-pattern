package behavioral.interpreter.booleanlogic;

/**
 * Non-terminal expression that represents the logical AND operation.
 * Evaluates to true only if both left and right expressions are true.
 */
public class AndExpression implements BooleanExpression {
    private final BooleanExpression leftExpression;
    private final BooleanExpression rightExpression;
    
    /**
     * Creates a new AND expression.
     * 
     * @param leftExpression The left operand expression
     * @param rightExpression The right operand expression
     */
    public AndExpression(BooleanExpression leftExpression, BooleanExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }
    
    @Override
    public boolean interpret(BooleanContext context) {
        context.incrementEvaluationSteps();
        
        System.out.printf("Evaluating AND expression: (%s) AND (%s)%n", 
                leftExpression.toString(), rightExpression.toString());
        
        // Short-circuit evaluation: if left is false, don't evaluate right
        boolean leftResult = leftExpression.interpret(context);
        if (!leftResult) {
            System.out.printf("  Short-circuit: Left operand is false, result = false%n");
            return false;
        }
        
        boolean rightResult = rightExpression.interpret(context);
        boolean result = leftResult && rightResult;
        
        System.out.printf("  AND result: %s AND %s = %s%n", leftResult, rightResult, result);
        
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
        return String.format("(%s AND %s)", leftExpression.toString(), rightExpression.toString());
    }
} 