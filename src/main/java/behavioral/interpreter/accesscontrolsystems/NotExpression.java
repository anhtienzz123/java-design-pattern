package behavioral.interpreter.accesscontrolsystems;

/**
 * Composite expression that performs logical NOT operation on a single access expression.
 * Inverts the result of the wrapped expression.
 */
public class NotExpression implements AccessExpression {
    private final AccessExpression expression;
    
    public NotExpression(AccessExpression expression) {
        this.expression = expression;
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        AccessResult result = expression.interpret(context);
        
        return switch (result.decision()) {
            case PERMIT -> AccessResult.deny(
                "NOT(" + result.reason() + ") - Inverted PERMIT to DENY", 
                "NotExpression"
            );
            case DENY -> AccessResult.permit(
                "NOT(" + result.reason() + ") - Inverted DENY to PERMIT", 
                "NotExpression"
            );
            case NOT_APPLICABLE -> AccessResult.notApplicable(
                "NOT(" + result.reason() + ") - NOT_APPLICABLE remains NOT_APPLICABLE", 
                "NotExpression"
            );
            case INDETERMINATE -> AccessResult.indeterminate(
                "NOT(" + result.reason() + ") - INDETERMINATE remains INDETERMINATE", 
                "NotExpression"
            );
        };
    }
    
    @Override
    public String getExpressionDescription() {
        return "NOT(" + expression.getExpressionDescription() + ")";
    }
}