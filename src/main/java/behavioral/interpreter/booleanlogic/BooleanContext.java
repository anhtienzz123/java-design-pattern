package behavioral.interpreter.booleanlogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Context class that maintains the state during interpretation.
 * Stores boolean variable values and provides lookup functionality.
 */
public class BooleanContext {
    private final Map<String, Boolean> variables = new HashMap<>();
    private int evaluationSteps = 0;
    
    /**
     * Sets the value of a boolean variable.
     * 
     * @param name The variable name
     * @param value The boolean value
     */
    public void setVariable(String name, boolean value) {
        variables.put(name.toUpperCase(), value);
    }
    
    /**
     * Gets the value of a boolean variable.
     * 
     * @param name The variable name
     * @return The boolean value, or false if variable doesn't exist
     */
    public boolean getVariable(String name) {
        return variables.getOrDefault(name.toUpperCase(), false);
    }
    
    /**
     * Checks if a variable exists in the context.
     * 
     * @param name The variable name
     * @return true if the variable exists, false otherwise
     */
    public boolean hasVariable(String name) {
        return variables.containsKey(name.toUpperCase());
    }
    
    /**
     * Increments the evaluation step counter.
     */
    public void incrementEvaluationSteps() {
        evaluationSteps++;
    }
    
    /**
     * Gets the number of evaluation steps taken.
     * 
     * @return The number of evaluation steps
     */
    public int getEvaluationSteps() {
        return evaluationSteps;
    }
    
    /**
     * Resets the evaluation step counter.
     */
    public void resetEvaluationSteps() {
        evaluationSteps = 0;
    }
    
    /**
     * Gets all variable names in the context.
     * 
     * @return Set of variable names
     */
    public Set<String> getVariableNames() {
        return variables.keySet();
    }
    
    /**
     * Clears all variables from the context.
     */
    public void clear() {
        variables.clear();
        evaluationSteps = 0;
    }
    
    @Override
    public String toString() {
        return "BooleanContext{variables=" + variables + ", steps=" + evaluationSteps + "}";
    }
} 