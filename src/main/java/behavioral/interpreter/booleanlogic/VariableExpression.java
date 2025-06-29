package behavioral.interpreter.booleanlogic;

/**
 * Terminal expression that represents a boolean variable.
 * This is a leaf node in the expression tree.
 */
public class VariableExpression implements BooleanExpression {
    private final String variableName;
    
    /**
     * Creates a new variable expression.
     * 
     * @param variableName The name of the boolean variable
     */
    public VariableExpression(String variableName) {
        this.variableName = variableName.toUpperCase();
    }
    
    @Override
    public boolean interpret(BooleanContext context) {
        context.incrementEvaluationSteps();
        boolean result = context.getVariable(variableName);
        
        // Debug output
        System.out.printf("  Evaluating variable '%s' = %s%n", variableName, result);
        
        return result;
    }
    
    /**
     * Gets the variable name.
     * 
     * @return The variable name
     */
    public String getVariableName() {
        return variableName;
    }
    
    @Override
    public String toString() {
        return variableName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VariableExpression that = (VariableExpression) obj;
        return variableName.equals(that.variableName);
    }
    
    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
} 