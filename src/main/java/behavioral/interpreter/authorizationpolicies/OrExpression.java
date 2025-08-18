package behavioral.interpreter.authorizationpolicies;

/**
 * Non-terminal expression that performs logical OR operation.
 */
public class OrExpression implements Expression {
    private final Expression left;
    private final Expression right;
    
    public OrExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return left.interpret(context) || right.interpret(context);
    }
    
    @Override
    public String toString() {
        return "(" + left + " OR " + right + ")";
    }
}