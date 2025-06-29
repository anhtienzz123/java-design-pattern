package behavioral.interpreter.booleanlogic;

/**
 * Abstract Expression interface for the Boolean Logic Interpreter Pattern.
 * All concrete expressions must implement the interpret method.
 */
public interface BooleanExpression {
    /**
     * Interprets the expression in the given context and returns a boolean result.
     * 
     * @param context The context containing variable values and evaluation state
     * @return The boolean result of evaluating this expression
     */
    boolean interpret(BooleanContext context);
    
    /**
     * Returns a string representation of this expression for debugging.
     * 
     * @return String representation of the expression
     */
    String toString();
} 