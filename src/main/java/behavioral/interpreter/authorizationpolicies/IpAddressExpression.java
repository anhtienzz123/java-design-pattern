package behavioral.interpreter.authorizationpolicies;

/**
 * Terminal expression that checks IP address conditions.
 */
public class IpAddressExpression implements Expression {
    private final String ipCondition;
    
    public IpAddressExpression(String ipCondition) {
        this.ipCondition = ipCondition;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return switch (ipCondition.toLowerCase()) {
            case "internal" -> context.isInternalIP();
            case "external" -> !context.isInternalIP();
            default -> context.clientIpAddress().equals(ipCondition);
        };
    }
    
    @Override
    public String toString() {
        return "IP(" + ipCondition + ")";
    }
}