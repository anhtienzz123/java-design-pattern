package behavioral.interpreter.authorizationpolicies;

/**
 * Terminal expression that checks if a user has a specific role.
 */
public class RoleExpression implements Expression {
    private final String requiredRole;
    
    public RoleExpression(String requiredRole) {
        this.requiredRole = requiredRole;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return context.hasRole(requiredRole);
    }
    
    @Override
    public String toString() {
        return "ROLE(" + requiredRole + ")";
    }
}