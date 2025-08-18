package behavioral.interpreter.accesscontrolsystems;

/**
 * Represents the result of an access control evaluation.
 */
public record AccessResult(
    boolean granted,
    String reason,
    String ruleName,
    AccessDecision decision
) {
    
    public enum AccessDecision {
        PERMIT,
        DENY,
        NOT_APPLICABLE,
        INDETERMINATE
    }
    
    public static AccessResult permit(String reason, String ruleName) {
        return new AccessResult(true, reason, ruleName, AccessDecision.PERMIT);
    }
    
    public static AccessResult deny(String reason, String ruleName) {
        return new AccessResult(false, reason, ruleName, AccessDecision.DENY);
    }
    
    public static AccessResult notApplicable(String reason, String ruleName) {
        return new AccessResult(false, reason, ruleName, AccessDecision.NOT_APPLICABLE);
    }
    
    public static AccessResult indeterminate(String reason, String ruleName) {
        return new AccessResult(false, reason, ruleName, AccessDecision.INDETERMINATE);
    }
    
    @Override
    public String toString() {
        return String.format("AccessResult{decision=%s, granted=%s, rule='%s', reason='%s'}", 
            decision, granted, ruleName, reason);
    }
}