package behavioral.interpreter.accesscontrolsystems;

/**
 * Composite expression that implements conditional logic (if-then-else).
 * Evaluates a condition and executes either the 'then' or 'else' expression based on the result.
 */
public class ConditionalExpression implements AccessExpression {
    private final AccessExpression condition;
    private final AccessExpression thenExpression;
    private final AccessExpression elseExpression;
    
    public ConditionalExpression(AccessExpression condition, AccessExpression thenExpression, AccessExpression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }
    
    public ConditionalExpression(AccessExpression condition, AccessExpression thenExpression) {
        this(condition, thenExpression, new AlwaysExpression(AccessResult.AccessDecision.NOT_APPLICABLE));
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        AccessResult conditionResult = condition.interpret(context);
        
        if (conditionResult.decision() == AccessResult.AccessDecision.PERMIT) {
            AccessResult thenResult = thenExpression.interpret(context);
            return new AccessResult(
                thenResult.granted(),
                String.format("IF(%s) THEN(%s)", conditionResult.reason(), thenResult.reason()),
                "ConditionalExpression",
                thenResult.decision()
            );
        } else {
            AccessResult elseResult = elseExpression.interpret(context);
            return new AccessResult(
                elseResult.granted(),
                String.format("IF(%s) ELSE(%s)", conditionResult.reason(), elseResult.reason()),
                "ConditionalExpression",
                elseResult.decision()
            );
        }
    }
    
    @Override
    public String getExpressionDescription() {
        if (elseExpression instanceof AlwaysExpression) {
            return String.format("IF(%s) THEN(%s)", 
                condition.getExpressionDescription(), 
                thenExpression.getExpressionDescription());
        } else {
            return String.format("IF(%s) THEN(%s) ELSE(%s)", 
                condition.getExpressionDescription(), 
                thenExpression.getExpressionDescription(), 
                elseExpression.getExpressionDescription());
        }
    }
    
    /**
     * Helper expression that always returns a specific decision.
     */
    private static class AlwaysExpression implements AccessExpression {
        private final AccessResult.AccessDecision decision;
        
        public AlwaysExpression(AccessResult.AccessDecision decision) {
            this.decision = decision;
        }
        
        @Override
        public AccessResult interpret(AccessContext context) {
            return switch (decision) {
                case PERMIT -> AccessResult.permit("Always permit", "AlwaysExpression");
                case DENY -> AccessResult.deny("Always deny", "AlwaysExpression");
                case NOT_APPLICABLE -> AccessResult.notApplicable("Always not applicable", "AlwaysExpression");
                case INDETERMINATE -> AccessResult.indeterminate("Always indeterminate", "AlwaysExpression");
            };
        }
        
        @Override
        public String getExpressionDescription() {
            return "ALWAYS(" + decision + ")";
        }
    }
}