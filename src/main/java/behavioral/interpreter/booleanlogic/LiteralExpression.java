package behavioral.interpreter.booleanlogic;

/**
 * Terminal expression that represents a boolean literal (true or false).
 * This is a leaf node in the expression tree.
 */
public class LiteralExpression implements BooleanExpression {
    private final boolean value;
    
    /**
     * Creates a new literal expression.
     * 
     * @param value The boolean literal value
     */
    public LiteralExpression(boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean interpret(BooleanContext context) {
        context.incrementEvaluationSteps();
        
        // Debug output
        System.out.printf("  Evaluating literal = %s%n", value);
        
        return value;
    }
    
    /**
     * Gets the literal value.
     * 
     * @return The boolean literal value
     */
    public boolean getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return Boolean.toString(value).toUpperCase();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LiteralExpression that = (LiteralExpression) obj;
        return value == that.value;
    }
    
    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }
} 