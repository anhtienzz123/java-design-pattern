package behavioral.interpreter.authorizationpolicies;

import java.util.ArrayList;
import java.util.List;

/**
 * Authorization engine that evaluates multiple policies for access control.
 * Uses the Interpreter pattern to parse and evaluate authorization rules.
 */
public class AuthorizationEngine {
    private final List<PolicyRule> policies;
    private final PolicyParser parser;
    
    public AuthorizationEngine() {
        this.policies = new ArrayList<>();
        this.parser = new PolicyParser();
    }
    
    /**
     * Adds a new policy rule to the engine.
     */
    public void addPolicy(String name, String policyExpression) {
        Expression expression = parser.parse(policyExpression);
        policies.add(new PolicyRule(name, policyExpression, expression));
    }
    
    /**
     * Evaluates all policies against the given security context.
     * Returns true if any policy grants access.
     */
    public AuthorizationResult authorize(SecurityContext context) {
        List<PolicyEvaluation> evaluations = new ArrayList<>();
        boolean authorized = false;
        
        for (PolicyRule policy : policies) {
            boolean result = policy.expression().interpret(context);
            evaluations.add(new PolicyEvaluation(policy.name(), policy.originalExpression(), result));
            
            if (result) {
                authorized = true;
            }
        }
        
        return new AuthorizationResult(authorized, evaluations);
    }
    
    /**
     * Evaluates a single policy expression against the context.
     */
    public boolean evaluatePolicy(String policyExpression, SecurityContext context) {
        Expression expression = parser.parse(policyExpression);
        return expression.interpret(context);
    }
    
    /**
     * Gets all registered policies.
     */
    public List<PolicyRule> getPolicies() {
        return new ArrayList<>(policies);
    }
    
    /**
     * Clears all policies.
     */
    public void clearPolicies() {
        policies.clear();
    }
    
    /**
     * Represents a policy rule with its name, expression, and parsed form.
     */
    public record PolicyRule(String name, String originalExpression, Expression expression) {}
    
    /**
     * Represents the evaluation result of a single policy.
     */
    public record PolicyEvaluation(String policyName, String expression, boolean result) {}
    
    /**
     * Represents the overall authorization result with detailed evaluations.
     */
    public record AuthorizationResult(boolean authorized, List<PolicyEvaluation> policyEvaluations) {
        
        public void printDetails() {
            System.out.println("Authorization Result: " + (authorized ? "GRANTED" : "DENIED"));
            System.out.println("Policy Evaluations:");
            for (PolicyEvaluation eval : policyEvaluations) {
                System.out.printf("  %s: %s -> %s%n", 
                    eval.policyName(), 
                    eval.expression(), 
                    eval.result() ? "ALLOW" : "DENY");
            }
        }
    }
}