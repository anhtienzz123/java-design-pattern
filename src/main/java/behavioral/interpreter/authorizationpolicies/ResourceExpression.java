package behavioral.interpreter.authorizationpolicies;

/**
 * Terminal expression that checks if the requested resource matches.
 */
public class ResourceExpression implements Expression {
    private final String resourcePattern;
    
    public ResourceExpression(String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        if (resourcePattern.contains("*")) {
            String pattern = resourcePattern.replace("*", ".*");
            return context.requestedResource().matches(pattern);
        }
        return context.requestedResource().equals(resourcePattern);
    }
    
    @Override
    public String toString() {
        return "RESOURCE(" + resourcePattern + ")";
    }
}