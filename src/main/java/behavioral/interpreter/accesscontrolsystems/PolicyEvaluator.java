package behavioral.interpreter.accesscontrolsystems;

import java.util.ArrayList;
import java.util.List;

/**
 * Policy evaluator that processes multiple access control policies and combines their results.
 * Implements policy combining algorithms for comprehensive access control decisions.
 */
public class PolicyEvaluator {
    private final List<AccessPolicy> policies;
    private final PolicyCombiningAlgorithm combiningAlgorithm;
    private final AccessRuleParser parser;
    
    public enum PolicyCombiningAlgorithm {
        DENY_OVERRIDES,     // If any policy denies, the result is deny
        PERMIT_OVERRIDES,   // If any policy permits, the result is permit
        FIRST_APPLICABLE,   // Use the first applicable policy's decision
        DENY_UNLESS_PERMIT, // Deny unless at least one policy explicitly permits
        PERMIT_UNLESS_DENY  // Permit unless at least one policy explicitly denies
    }
    
    public PolicyEvaluator(PolicyCombiningAlgorithm combiningAlgorithm) {
        this.policies = new ArrayList<>();
        this.combiningAlgorithm = combiningAlgorithm;
        this.parser = new AccessRuleParser();
    }
    
    /**
     * Adds a new access control policy.
     */
    public void addPolicy(String name, String rule, PolicyEffect effect) {
        AccessExpression expression = parser.parse(rule);
        policies.add(new AccessPolicy(name, rule, expression, effect));
    }
    
    /**
     * Evaluates all policies against the given access context.
     */
    public PolicyEvaluationResult evaluate(AccessContext context) {
        List<PolicyDecision> decisions = new ArrayList<>();
        
        // Evaluate each policy
        for (AccessPolicy policy : policies) {
            AccessResult result = policy.expression().interpret(context);
            PolicyDecision decision = new PolicyDecision(policy, result);
            decisions.add(decision);
        }
        
        // Combine decisions using the specified algorithm
        AccessResult finalResult = combineDecisions(decisions);
        
        return new PolicyEvaluationResult(finalResult, decisions, combiningAlgorithm);
    }
    
    private AccessResult combineDecisions(List<PolicyDecision> decisions) {
        return switch (combiningAlgorithm) {
            case DENY_OVERRIDES -> combineDenyOverrides(decisions);
            case PERMIT_OVERRIDES -> combinePermitOverrides(decisions);
            case FIRST_APPLICABLE -> combineFirstApplicable(decisions);
            case DENY_UNLESS_PERMIT -> combineDenyUnlessPermit(decisions);
            case PERMIT_UNLESS_DENY -> combinePermitUnlessDeny(decisions);
        };
    }
    
    private AccessResult combineDenyOverrides(List<PolicyDecision> decisions) {
        boolean hasPermit = false;
        boolean hasIndeterminate = false;
        
        for (PolicyDecision decision : decisions) {
            if (decision.result().decision() == AccessResult.AccessDecision.DENY ||
                (decision.result().decision() == AccessResult.AccessDecision.PERMIT && 
                 decision.policy().effect() == PolicyEffect.DENY)) {
                return AccessResult.deny("Deny overrides: " + decision.policy().name(), "PolicyEvaluator");
            }
            
            if (decision.result().decision() == AccessResult.AccessDecision.PERMIT &&
                decision.policy().effect() == PolicyEffect.PERMIT) {
                hasPermit = true;
            }
            
            if (decision.result().decision() == AccessResult.AccessDecision.INDETERMINATE) {
                hasIndeterminate = true;
            }
        }
        
        if (hasPermit) {
            return AccessResult.permit("Deny overrides: No deny found, has permit", "PolicyEvaluator");
        }
        
        if (hasIndeterminate) {
            return AccessResult.indeterminate("Deny overrides: No deny found, has indeterminate", "PolicyEvaluator");
        }
        
        return AccessResult.notApplicable("Deny overrides: No applicable policies", "PolicyEvaluator");
    }
    
    private AccessResult combinePermitOverrides(List<PolicyDecision> decisions) {
        boolean hasDeny = false;
        boolean hasIndeterminate = false;
        
        for (PolicyDecision decision : decisions) {
            if (decision.result().decision() == AccessResult.AccessDecision.PERMIT &&
                decision.policy().effect() == PolicyEffect.PERMIT) {
                return AccessResult.permit("Permit overrides: " + decision.policy().name(), "PolicyEvaluator");
            }
            
            if (decision.result().decision() == AccessResult.AccessDecision.DENY ||
                (decision.result().decision() == AccessResult.AccessDecision.PERMIT && 
                 decision.policy().effect() == PolicyEffect.DENY)) {
                hasDeny = true;
            }
            
            if (decision.result().decision() == AccessResult.AccessDecision.INDETERMINATE) {
                hasIndeterminate = true;
            }
        }
        
        if (hasDeny) {
            return AccessResult.deny("Permit overrides: No permit found, has deny", "PolicyEvaluator");
        }
        
        if (hasIndeterminate) {
            return AccessResult.indeterminate("Permit overrides: No permit found, has indeterminate", "PolicyEvaluator");
        }
        
        return AccessResult.notApplicable("Permit overrides: No applicable policies", "PolicyEvaluator");
    }
    
    private AccessResult combineFirstApplicable(List<PolicyDecision> decisions) {
        for (PolicyDecision decision : decisions) {
            if (decision.result().decision() != AccessResult.AccessDecision.NOT_APPLICABLE) {
                boolean finalDecision = decision.result().decision() == AccessResult.AccessDecision.PERMIT ?
                    decision.policy().effect() == PolicyEffect.PERMIT :
                    decision.result().decision() != AccessResult.AccessDecision.DENY;
                
                return finalDecision ?
                    AccessResult.permit("First applicable: " + decision.policy().name(), "PolicyEvaluator") :
                    AccessResult.deny("First applicable: " + decision.policy().name(), "PolicyEvaluator");
            }
        }
        
        return AccessResult.notApplicable("First applicable: No applicable policies", "PolicyEvaluator");
    }
    
    private AccessResult combineDenyUnlessPermit(List<PolicyDecision> decisions) {
        for (PolicyDecision decision : decisions) {
            if (decision.result().decision() == AccessResult.AccessDecision.PERMIT &&
                decision.policy().effect() == PolicyEffect.PERMIT) {
                return AccessResult.permit("Deny unless permit: Found explicit permit", "PolicyEvaluator");
            }
        }
        
        return AccessResult.deny("Deny unless permit: No explicit permit found", "PolicyEvaluator");
    }
    
    private AccessResult combinePermitUnlessDeny(List<PolicyDecision> decisions) {
        for (PolicyDecision decision : decisions) {
            if (decision.result().decision() == AccessResult.AccessDecision.DENY ||
                (decision.result().decision() == AccessResult.AccessDecision.PERMIT && 
                 decision.policy().effect() == PolicyEffect.DENY)) {
                return AccessResult.deny("Permit unless deny: Found explicit deny", "PolicyEvaluator");
            }
        }
        
        return AccessResult.permit("Permit unless deny: No explicit deny found", "PolicyEvaluator");
    }
    
    /**
     * Gets all registered policies.
     */
    public List<AccessPolicy> getPolicies() {
        return new ArrayList<>(policies);
    }
    
    /**
     * Clears all policies.
     */
    public void clearPolicies() {
        policies.clear();
    }
    
    /**
     * Represents an access control policy with its rule and effect.
     */
    public record AccessPolicy(String name, String rule, AccessExpression expression, PolicyEffect effect) {}
    
    /**
     * Represents the effect of a policy when it applies.
     */
    public enum PolicyEffect {
        PERMIT, DENY
    }
    
    /**
     * Represents the decision made by a single policy.
     */
    public record PolicyDecision(AccessPolicy policy, AccessResult result) {}
    
    /**
     * Represents the final result of policy evaluation.
     */
    public record PolicyEvaluationResult(
        AccessResult finalResult,
        List<PolicyDecision> policyDecisions,
        PolicyCombiningAlgorithm algorithm
    ) {
        
        public void printDetails() {
            System.out.println("=== Policy Evaluation Result ===");
            System.out.println("Final Decision: " + finalResult);
            System.out.println("Combining Algorithm: " + algorithm);
            System.out.println("Individual Policy Decisions:");
            
            for (PolicyDecision decision : policyDecisions) {
                System.out.printf("  Policy '%s' (Effect: %s): %s%n",
                    decision.policy().name(),
                    decision.policy().effect(),
                    decision.result());
                System.out.printf("    Rule: %s%n", decision.policy().rule());
            }
            System.out.println();
        }
    }
}