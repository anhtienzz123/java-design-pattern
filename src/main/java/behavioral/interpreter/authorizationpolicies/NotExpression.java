package behavioral.interpreter.authorizationpolicies;

/**
 * Non-terminal expression that performs logical NOT operation.
 */
public class NotExpression implements Expression {
    private final Expression expression;
    
    public NotExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return !expression.interpret(context);
    }
    
    @Override
    public String toString() {
        return "NOT(" + expression + ")";
    }
}