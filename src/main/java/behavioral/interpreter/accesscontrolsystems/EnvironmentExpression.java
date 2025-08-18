package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Terminal expression that evaluates environment-based access rules.
 */
public class EnvironmentExpression implements AccessExpression {
    private final String attributeType;
    private final String expectedValue;
    private final SubjectExpression.ComparisonOperator operator;
    
    public EnvironmentExpression(String attributeType, String expectedValue) {
        this(attributeType, expectedValue, SubjectExpression.ComparisonOperator.EQUALS);
    }
    
    public EnvironmentExpression(String attributeType, String expectedValue, SubjectExpression.ComparisonOperator operator) {
        this.attributeType = attributeType;
        this.expectedValue = expectedValue;
        this.operator = operator;
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        boolean result = switch (attributeType.toLowerCase()) {
            case "time" -> evaluateTime(context.environment());
            case "day" -> evaluateDay(context.environment());
            case "location" -> evaluateLocation(context.environment());
            case "network" -> evaluateNetwork(context.environment());
            case "ip" -> evaluateIp(context.environment());
            case "useragent" -> evaluateUserAgent(context.environment());
            case "attribute" -> evaluateAttribute(context.environment());
            default -> false;
        };
        
        String reason = String.format("Environment %s %s %s: %s", 
            attributeType, operator, expectedValue, result ? "MATCH" : "NO_MATCH");
        
        return result ? 
            AccessResult.permit(reason, "EnvironmentRule") : 
            AccessResult.notApplicable(reason, "EnvironmentRule");
    }
    
    private boolean evaluateTime(AccessContext.Environment environment) {
        return switch (expectedValue.toLowerCase()) {
            case "business_hours" -> environment.isWithinBusinessHours();
            case "after_hours" -> !environment.isWithinBusinessHours();
            case "weekend" -> environment.isWeekend();
            case "weekday" -> !environment.isWeekend();
            default -> {
                try {
                    LocalDateTime targetTime = LocalDateTime.parse(expectedValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    yield switch (operator) {
                        case EQUALS -> environment.requestTime().equals(targetTime);
                        case GREATER_THAN -> environment.requestTime().isAfter(targetTime);
                        case LESS_THAN -> environment.requestTime().isBefore(targetTime);
                        case GREATER_EQUAL -> !environment.requestTime().isBefore(targetTime);
                        case LESS_EQUAL -> !environment.requestTime().isAfter(targetTime);
                        default -> false;
                    };
                } catch (Exception e) {
                    yield false;
                }
            }
        };
    }
    
    private boolean evaluateDay(AccessContext.Environment environment) {
        String currentDay = environment.requestTime().getDayOfWeek().toString().toLowerCase();
        return switch (operator) {
            case EQUALS -> currentDay.equals(expectedValue.toLowerCase());
            case NOT_EQUALS -> !currentDay.equals(expectedValue.toLowerCase());
            default -> false;
        };
    }
    
    private boolean evaluateLocation(AccessContext.Environment environment) {
        return switch (operator) {
            case EQUALS -> environment.location().equals(expectedValue);
            case NOT_EQUALS -> !environment.location().equals(expectedValue);
            case CONTAINS -> environment.location().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateNetwork(AccessContext.Environment environment) {
        return switch (expectedValue.toLowerCase()) {
            case "trusted" -> environment.isFromTrustedNetwork();
            case "untrusted" -> !environment.isFromTrustedNetwork();
            default -> false;
        };
    }
    
    private boolean evaluateIp(AccessContext.Environment environment) {
        return switch (operator) {
            case EQUALS -> environment.clientIp().equals(expectedValue);
            case NOT_EQUALS -> !environment.clientIp().equals(expectedValue);
            case CONTAINS -> environment.clientIp().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateUserAgent(AccessContext.Environment environment) {
        return switch (operator) {
            case EQUALS -> environment.userAgent().equals(expectedValue);
            case NOT_EQUALS -> !environment.userAgent().equals(expectedValue);
            case CONTAINS -> environment.userAgent().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateAttribute(AccessContext.Environment environment) {
        String[] parts = expectedValue.split("=", 2);
        if (parts.length != 2) return false;
        
        String attrName = parts[0];
        String attrValue = parts[1];
        String actualValue = environment.getContextAttribute(attrName);
        
        if (actualValue == null) return false;
        
        return switch (operator) {
            case EQUALS -> actualValue.equals(attrValue);
            case NOT_EQUALS -> !actualValue.equals(attrValue);
            case CONTAINS -> actualValue.contains(attrValue);
            default -> false;
        };
    }
    
    @Override
    public String getExpressionDescription() {
        return String.format("Environment[%s %s %s]", attributeType, operator, expectedValue);
    }
}