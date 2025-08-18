package behavioral.interpreter.accesscontrolsystems;

/**
 * Terminal expression that evaluates action-based access rules.
 */
public class ActionExpression implements AccessExpression {
    private final String attributeType;
    private final String expectedValue;
    private final SubjectExpression.ComparisonOperator operator;
    
    public ActionExpression(String attributeType, String expectedValue) {
        this(attributeType, expectedValue, SubjectExpression.ComparisonOperator.EQUALS);
    }
    
    public ActionExpression(String attributeType, String expectedValue, SubjectExpression.ComparisonOperator operator) {
        this.attributeType = attributeType;
        this.expectedValue = expectedValue;
        this.operator = operator;
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        boolean result = switch (attributeType.toLowerCase()) {
            case "type" -> evaluateType(context.action());
            case "verb" -> evaluateVerb(context.action());
            case "category" -> evaluateCategory(context.action());
            case "parameter" -> evaluateParameter(context.action());
            default -> false;
        };
        
        String reason = String.format("Action %s %s %s: %s", 
            attributeType, operator, expectedValue, result ? "MATCH" : "NO_MATCH");
        
        return result ? 
            AccessResult.permit(reason, "ActionRule") : 
            AccessResult.notApplicable(reason, "ActionRule");
    }
    
    private boolean evaluateType(AccessContext.Action action) {
        return switch (operator) {
            case EQUALS -> action.type().equals(expectedValue);
            case NOT_EQUALS -> !action.type().equals(expectedValue);
            case CONTAINS -> action.type().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateVerb(AccessContext.Action action) {
        return switch (operator) {
            case EQUALS -> action.verb().equalsIgnoreCase(expectedValue);
            case NOT_EQUALS -> !action.verb().equalsIgnoreCase(expectedValue);
            case CONTAINS -> action.verb().toLowerCase().contains(expectedValue.toLowerCase());
            default -> false;
        };
    }
    
    private boolean evaluateCategory(AccessContext.Action action) {
        String category = categorizeAction(action);
        return switch (operator) {
            case EQUALS -> category.equals(expectedValue.toLowerCase());
            case NOT_EQUALS -> !category.equals(expectedValue.toLowerCase());
            default -> false;
        };
    }
    
    private boolean evaluateParameter(AccessContext.Action action) {
        String[] parts = expectedValue.split("=", 2);
        if (parts.length != 2) return false;
        
        String paramName = parts[0];
        String paramValue = parts[1];
        String actualValue = action.getParameter(paramName);
        
        if (actualValue == null) return false;
        
        return switch (operator) {
            case EQUALS -> actualValue.equals(paramValue);
            case NOT_EQUALS -> !actualValue.equals(paramValue);
            case CONTAINS -> actualValue.contains(paramValue);
            default -> false;
        };
    }
    
    private String categorizeAction(AccessContext.Action action) {
        if (action.isReadOperation()) return "read";
        if (action.isWriteOperation()) return "write";
        if (action.isDeleteOperation()) return "delete";
        return "other";
    }
    
    @Override
    public String getExpressionDescription() {
        return String.format("Action[%s %s %s]", attributeType, operator, expectedValue);
    }
}