package behavioral.interpreter.authorizationpolicies;

/**
 * Abstract expression interface for authorization policy evaluation.
 * Defines the contract for interpreting authorization rules.
 */
public interface Expression {
    /**
     * Interprets the expression in the given security context.
     * 
     * @param context The security context containing user information and request details
     * @return true if the expression evaluates to authorized, false otherwise
     */
    boolean interpret(SecurityContext context);
}