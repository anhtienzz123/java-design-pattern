package behavioral.interpreter.accesscontrolsystems;

/**
 * Terminal expression that evaluates object/resource-based access rules.
 */
public class ObjectExpression implements AccessExpression {
    private final String attributeType;
    private final String expectedValue;
    private final SubjectExpression.ComparisonOperator operator;
    
    public ObjectExpression(String attributeType, String expectedValue) {
        this(attributeType, expectedValue, SubjectExpression.ComparisonOperator.EQUALS);
    }
    
    public ObjectExpression(String attributeType, String expectedValue, SubjectExpression.ComparisonOperator operator) {
        this.attributeType = attributeType;
        this.expectedValue = expectedValue;
        this.operator = operator;
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        boolean result = switch (attributeType.toLowerCase()) {
            case "type" -> evaluateType(context.object());
            case "path" -> evaluatePath(context.object());
            case "owner" -> evaluateOwner(context.object(), context.subject());
            case "tag" -> evaluateTag(context.object());
            case "classification" -> evaluateClassification(context.object());
            case "property" -> evaluateProperty(context.object());
            default -> false;
        };
        
        String reason = String.format("Object %s %s %s: %s", 
            attributeType, operator, expectedValue, result ? "MATCH" : "NO_MATCH");
        
        return result ? 
            AccessResult.permit(reason, "ObjectRule") : 
            AccessResult.notApplicable(reason, "ObjectRule");
    }
    
    private boolean evaluateType(AccessContext.AccessObject object) {
        return switch (operator) {
            case EQUALS -> object.type().equals(expectedValue);
            case NOT_EQUALS -> !object.type().equals(expectedValue);
            case CONTAINS -> object.type().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluatePath(AccessContext.AccessObject object) {
        return switch (operator) {
            case EQUALS -> object.path().equals(expectedValue);
            case NOT_EQUALS -> !object.path().equals(expectedValue);
            case CONTAINS -> object.path().contains(expectedValue);
            default -> false;
        };
    }
    
    private boolean evaluateOwner(AccessContext.AccessObject object, AccessContext.Subject subject) {
        return switch (operator) {
            case EQUALS -> {
                if ("$self".equals(expectedValue)) {
                    yield object.isOwnedBy(subject.id());
                }
                yield object.owner().equals(expectedValue);
            }
            case NOT_EQUALS -> {
                if ("$self".equals(expectedValue)) {
                    yield !object.isOwnedBy(subject.id());
                }
                yield !object.owner().equals(expectedValue);
            }
            default -> false;
        };
    }
    
    private boolean evaluateTag(AccessContext.AccessObject object) {
        return switch (operator) {
            case EQUALS -> object.hasTag(expectedValue);
            case NOT_EQUALS -> !object.hasTag(expectedValue);
            case CONTAINS -> object.tags().stream().anyMatch(tag -> tag.contains(expectedValue));
            default -> false;
        };
    }
    
    private boolean evaluateClassification(AccessContext.AccessObject object) {
        int requiredLevel = Integer.parseInt(expectedValue);
        return switch (operator) {
            case EQUALS -> object.classificationLevel() == requiredLevel;
            case GREATER_THAN -> object.classificationLevel() > requiredLevel;
            case GREATER_EQUAL -> object.classificationLevel() >= requiredLevel;
            case LESS_THAN -> object.classificationLevel() < requiredLevel;
            case LESS_EQUAL -> object.classificationLevel() <= requiredLevel;
            default -> false;
        };
    }
    
    private boolean evaluateProperty(AccessContext.AccessObject object) {
        String[] parts = expectedValue.split("=", 2);
        if (parts.length != 2) return false;
        
        String propName = parts[0];
        String propValue = parts[1];
        String actualValue = object.getProperty(propName);
        
        if (actualValue == null) return false;
        
        return switch (operator) {
            case EQUALS -> actualValue.equals(propValue);
            case NOT_EQUALS -> !actualValue.equals(propValue);
            case CONTAINS -> actualValue.contains(propValue);
            default -> false;
        };
    }
    
    @Override
    public String getExpressionDescription() {
        return String.format("Object[%s %s %s]", attributeType, operator, expectedValue);
    }
}