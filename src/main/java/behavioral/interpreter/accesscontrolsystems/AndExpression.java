package behavioral.interpreter.accesscontrolsystems;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite expression that performs logical AND operation on multiple access expressions.
 * All expressions must evaluate to PERMIT for the overall result to be PERMIT.
 */
public class AndExpression extends CompositeExpression {
    
    public AndExpression(List<AccessExpression> expressions) {
        super(expressions, "AND");
    }
    
    public static AndExpression of(AccessExpression... expressions) {
        return new AndExpression(List.of(expressions));
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        List<AccessResult> results = new ArrayList<>();
        
        for (AccessExpression expression : expressions) {
            AccessResult result = expression.interpret(context);
            results.add(result);
            
            // Short-circuit evaluation: if any expression denies or is not applicable, return immediately
            if (result.decision() == AccessResult.AccessDecision.DENY) {
                return AccessResult.deny(
                    formatReason("AND", results) + " - Early termination on DENY", 
                    "AndExpression"
                );
            }
            
            if (result.decision() == AccessResult.AccessDecision.NOT_APPLICABLE) {
                return AccessResult.notApplicable(
                    formatReason("AND", results) + " - Early termination on NOT_APPLICABLE", 
                    "AndExpression"
                );
            }
            
            if (result.decision() == AccessResult.AccessDecision.INDETERMINATE) {
                return AccessResult.indeterminate(
                    formatReason("AND", results) + " - Early termination on INDETERMINATE", 
                    "AndExpression"
                );
            }
        }
        
        // All expressions evaluated to PERMIT
        return AccessResult.permit(
            formatReason("AND", results) + " - All conditions satisfied", 
            "AndExpression"
        );
    }
}