package behavioral.interpreter.booleanlogic;

/**
 * Non-terminal expression that represents the logical NOT operation.
 * Evaluates to the negation of the operand expression.
 */
public class NotExpression implements BooleanExpression {
    private final BooleanExpression operandExpression;
    
    /**
     * Creates a new NOT expression.
     * 
     * @param operandExpression The operand expression to negate
     */
    public NotExpression(BooleanExpression operandExpression) {
        this.operandExpression = operandExpression;
    }
    
    @Override
    public boolean interpret(BooleanContext context) {
        context.incrementEvaluationSteps();
        
        System.out.printf("Evaluating NOT expression: NOT (%s)%n", operandExpression.toString());
        
        boolean operandResult = operandExpression.interpret(context);
        boolean result = !operandResult;
        
        System.out.printf("  NOT result: NOT %s = %s%n", operandResult, result);
        
        return result;
    }
    
    /**
     * Gets the operand expression.
     * 
     * @return The operand expression
     */
    public BooleanExpression getOperandExpression() {
        return operandExpression;
    }
    
    @Override
    public String toString() {
        return String.format("NOT (%s)", operandExpression.toString());
    }
} 