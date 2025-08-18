package behavioral.interpreter.authorizationpolicies;

import java.util.Stack;

/**
 * Parser that converts string-based policy rules into expression trees.
 * Supports a simple policy language with roles, permissions, resources, time, and IP conditions.
 */
public class PolicyParser {
    
    /**
     * Parses a policy string and returns an expression tree.
     * 
     * Supported syntax:
     * - ROLE(admin)
     * - PERMISSION(read)
     * - RESOURCE(/api/*)
     * - TIME(business_hours)
     * - IP(internal)
     * - Logical operators: AND, OR, NOT
     * - Parentheses for grouping
     * 
     * Example: "ROLE(admin) AND (PERMISSION(read) OR PERMISSION(write))"
     */
    public Expression parse(String policy) {
        if (policy == null || policy.trim().isEmpty()) {
            throw new IllegalArgumentException("Policy cannot be null or empty");
        }
        
        String[] tokens = tokenize(policy.trim());
        return parseExpression(tokens, new int[]{0});
    }
    
    private String[] tokenize(String policy) {
        return policy.replace("(", " ( ")
                    .replace(")", " ) ")
                    .split("\\s+");
    }
    
    private Expression parseExpression(String[] tokens, int[] position) {
        Expression left = parseTerm(tokens, position);
        
        while (position[0] < tokens.length && isLogicalOperator(tokens[position[0]])) {
            String operator = tokens[position[0]++];
            Expression right = parseTerm(tokens, position);
            
            left = switch (operator.toUpperCase()) {
                case "AND" -> new AndExpression(left, right);
                case "OR" -> new OrExpression(left, right);
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        }
        
        return left;
    }
    
    private Expression parseTerm(String[] tokens, int[] position) {
        if (position[0] >= tokens.length) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }
        
        String token = tokens[position[0]++];
        
        return switch (token.toUpperCase()) {
            case "NOT" -> new NotExpression(parseTerm(tokens, position));
            case "(" -> {
                Expression expr = parseExpression(tokens, position);
                if (position[0] >= tokens.length || !tokens[position[0]].equals(")")) {
                    throw new IllegalArgumentException("Missing closing parenthesis");
                }
                position[0]++; // consume ')'
                yield expr;
            }
            case "ROLE" -> parseTerminalExpression(tokens, position, this::createRoleExpression);
            case "PERMISSION" -> parseTerminalExpression(tokens, position, this::createPermissionExpression);
            case "RESOURCE" -> parseTerminalExpression(tokens, position, this::createResourceExpression);
            case "TIME" -> parseTerminalExpression(tokens, position, this::createTimeExpression);
            case "IP" -> parseTerminalExpression(tokens, position, this::createIpExpression);
            default -> throw new IllegalArgumentException("Unknown token: " + token);
        };
    }
    
    private Expression parseTerminalExpression(String[] tokens, int[] position, ExpressionFactory factory) {
        if (position[0] >= tokens.length || !tokens[position[0]].equals("(")) {
            throw new IllegalArgumentException("Expected opening parenthesis");
        }
        position[0]++; // consume '('
        
        if (position[0] >= tokens.length) {
            throw new IllegalArgumentException("Expected parameter");
        }
        
        String parameter = tokens[position[0]++];
        
        if (position[0] >= tokens.length || !tokens[position[0]].equals(")")) {
            throw new IllegalArgumentException("Expected closing parenthesis");
        }
        position[0]++; // consume ')'
        
        return factory.create(parameter);
    }
    
    private boolean isLogicalOperator(String token) {
        return "AND".equalsIgnoreCase(token) || "OR".equalsIgnoreCase(token);
    }
    
    private Expression createRoleExpression(String role) {
        return new RoleExpression(role);
    }
    
    private Expression createPermissionExpression(String permission) {
        return new PermissionExpression(permission);
    }
    
    private Expression createResourceExpression(String resource) {
        return new ResourceExpression(resource);
    }
    
    private Expression createTimeExpression(String timeCondition) {
        return new TimeBasedExpression(timeCondition);
    }
    
    private Expression createIpExpression(String ipCondition) {
        return new IpAddressExpression(ipCondition);
    }
    
    @FunctionalInterface
    private interface ExpressionFactory {
        Expression create(String parameter);
    }
}