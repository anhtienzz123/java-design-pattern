package behavioral.interpreter.authorizationpolicies;

/**
 * Non-terminal expression that performs logical AND operation.
 */
public class AndExpression implements Expression {
    private final Expression left;
    private final Expression right;
    
    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public boolean interpret(SecurityContext context) {
        return left.interpret(context) && right.interpret(context);
    }
    
    @Override
    public String toString() {
        return "(" + left + " AND " + right + ")";
    }
}