package behavioral.interpreter.authorizationpolicies;

/**
 * Terminal expression that checks if a user has a specific permission.
 */
public class PermissionExpression implements Expression {
    private final String requiredPermission;
    
    public PermissionExpression(String requiredPermission) {
        this.requiredPermission = requiredPermission;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return context.hasPermission(requiredPermission);
    }
    
    @Override
    public String toString() {
        return "PERMISSION(" + requiredPermission + ")";
    }
}