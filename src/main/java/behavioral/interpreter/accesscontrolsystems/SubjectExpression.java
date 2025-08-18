package behavioral.interpreter.accesscontrolsystems;

/**
 * Terminal expression that evaluates subject-based access rules.
 */
public class SubjectExpression implements AccessExpression {
    private final String attributeType;
    private final String expectedValue;
    private final ComparisonOperator operator;
    
    public enum ComparisonOperator {
        EQUALS, NOT_EQUALS, CONTAINS, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL
    }
    
    public SubjectExpression(String attributeType, String expectedValue) {
        this(attributeType, expectedValue, ComparisonOperator.EQUALS);
    }
    
    public SubjectExpression(String attributeType, String expectedValue, ComparisonOperator operator) {
        this.attributeType = attributeType;
        this.expectedValue = expectedValue;
        this.operator = operator;
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        boolean result = switch (attributeType.toLowerCase()) {
            case "role" -> evaluateRole(context.subject());
            case "group" -> evaluateGroup(context.subject());
            case "clearance" -> evaluateClearance(context.subject());
            case "id" -> evaluateId(context.subject());
            case "attribute" -> evaluateAttribute(context.subject());
            default -> false;
        };
        
        String reason = String.format("Subject %s %s %s: %s", 
            attributeType, operator, expectedValue, result ? "MATCH" : "NO_MATCH");
        
        return result ? 
            AccessResult.permit(reason, "SubjectRule") : 
            AccessResult.notApplicable(reason, "SubjectRule");
    }
    
    private boolean evaluateRole(AccessContext.Subject subject) {
        return switch (operator) {
            case EQUALS -> subject.hasRole(expectedValue);
            case NOT_EQUALS -> !subject.hasRole(expectedValue);
            case CONTAINS -> subject.roles().stream().anyMatch(role -> role.contains(expectedValue));
            default -> false;
        };
    }
    
    private boolean evaluateGroup(AccessContext.Subject subject) {
        return switch (operator) {
            case EQUALS -> subject.belongsToGroup(expectedValue);
            case NOT_EQUALS -> !subject.belongsToGroup(expectedValue);
            case CONTAINS -> subject.groups().stream().anyMatch(group -> group.contains(expectedValue));
            default -> false;
        };
    }
    
    private boolean evaluateClearance(AccessContext.Subject subject) {
        int requiredLevel = Integer.parseInt(expectedValue);
        return switch (operator) {
            case EQUALS -> subject.clearanceLevel() == requiredLevel;
            case GREATER_THAN -> subject.clearanceLevel() > requiredLevel;
            case GREATER_EQUAL -> subject.clearanceLevel() >= requiredLevel;
            case LESS_THAN -> subject.clearanceLevel() < requiredLevel;
            case LESS_EQUAL -> subject.clearanceLevel() <= requiredLevel;
            default -> false;
        };
    }
    
    private boolean evaluateId(AccessContext.Subject subject) {
        return switch (operator) {
            case EQUALS -> subject.id().equals(expectedValue);
            case NOT_EQUALS -> !subject.id().equals(expectedValue);
            case CONTAINS -> subject.id().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateAttribute(AccessContext.Subject subject) {
        String[] parts = expectedValue.split("=", 2);
        if (parts.length != 2) return false;
        
        String attrName = parts[0];
        String attrValue = parts[1];
        String actualValue = subject.getAttribute(attrName);
        
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
        return String.format("Subject[%s %s %s]", attributeType, operator, expectedValue);
    }
}