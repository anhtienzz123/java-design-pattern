package behavioral.interpreter.authorizationpolicies;

/**
 * Terminal expression that checks time-based conditions.
 */
public class TimeBasedExpression implements Expression {
    private final String timeCondition;
    
    public TimeBasedExpression(String timeCondition) {
        this.timeCondition = timeCondition;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return switch (timeCondition.toLowerCase()) {
            case "business_hours" -> context.isWithinBusinessHours();
            case "after_hours" -> !context.isWithinBusinessHours();
            default -> throw new IllegalArgumentException("Unknown time condition: " + timeCondition);
        };
    }
    
    @Override
    public String toString() {
        return "TIME(" + timeCondition + ")";
    }
}