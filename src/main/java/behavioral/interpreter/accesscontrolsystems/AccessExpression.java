package behavioral.interpreter.accesscontrolsystems;

/**
 * Abstract expression interface for access control rule evaluation.
 * Defines the contract for interpreting access control policies.
 */
public interface AccessExpression {
    /**
     * Interprets the access control expression in the given context.
     * 
     * @param context The access context containing subject, object, and environment information
     * @return AccessResult indicating whether access is granted and why
     */
    AccessResult interpret(AccessContext context);
    
    /**
     * Returns a string representation of this expression for logging and debugging.
     */
    String getExpressionDescription();
}