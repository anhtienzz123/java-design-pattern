package behavioral.interpreter.accesscontrolsystems;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite expression that performs logical OR operation on multiple access expressions.
 * At least one expression must evaluate to PERMIT for the overall result to be PERMIT.
 */
public class OrExpression extends CompositeExpression {
    
    public OrExpression(List<AccessExpression> expressions) {
        super(expressions, "OR");
    }
    
    public static OrExpression of(AccessExpression... expressions) {
        return new OrExpression(List.of(expressions));
    }
    
    @Override
    public AccessResult interpret(AccessContext context) {
        List<AccessResult> results = new ArrayList<>();
        boolean hasIndeterminate = false;
        boolean hasNotApplicable = false;
        
        for (AccessExpression expression : expressions) {
            AccessResult result = expression.interpret(context);
            results.add(result);
            
            // Short-circuit evaluation: if any expression permits, return immediately
            if (result.decision() == AccessResult.AccessDecision.PERMIT) {
                return AccessResult.permit(
                    formatReason("OR", results) + " - Early termination on PERMIT", 
                    "OrExpression"
                );
            }
            
            // Track other decision types for final evaluation
            if (result.decision() == AccessResult.AccessDecision.INDETERMINATE) {
                hasIndeterminate = true;
            } else if (result.decision() == AccessResult.AccessDecision.NOT_APPLICABLE) {
                hasNotApplicable = true;
            }
        }
        
        // No expression evaluated to PERMIT
        // Determine the final decision based on what we encountered
        if (hasIndeterminate) {
            return AccessResult.indeterminate(
                formatReason("OR", results) + " - No PERMIT found, has INDETERMINATE", 
                "OrExpression"
            );
        }
        
        if (hasNotApplicable) {
            return AccessResult.notApplicable(
                formatReason("OR", results) + " - No PERMIT found, has NOT_APPLICABLE", 
                "OrExpression"
            );
        }
        
        // All expressions evaluated to DENY
        return AccessResult.deny(
            formatReason("OR", results) + " - All conditions denied", 
            "OrExpression"
        );
    }
}