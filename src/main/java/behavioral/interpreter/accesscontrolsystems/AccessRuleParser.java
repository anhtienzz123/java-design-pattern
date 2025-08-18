package behavioral.interpreter.accesscontrolsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Parser that converts string-based access control rules into expression trees.
 * Supports a comprehensive access control language for ABAC (Attribute-Based Access Control).
 */
public class AccessRuleParser {
    
    /**
     * Parses an access control rule string and returns an expression tree.
     * 
     * Supported syntax:
     * - Subject: subject.role = "admin", subject.group = "managers", subject.clearance >= 3
     * - Object: object.type = "document", object.owner = "$self", object.tag = "confidential"
     * - Action: action.verb = "read", action.category = "write", action.type = "file_operation"
     * - Environment: env.time = "business_hours", env.network = "trusted", env.location = "headquarters"
     * - Logical operators: AND, OR, NOT
     * - Conditional: IF condition THEN expression ELSE expression
     * - Parentheses for grouping
     * 
     * Examples:
     * - "subject.role = admin AND object.type = document"
     * - "subject.clearance >= object.classification AND env.time = business_hours"
     * - "IF subject.role = manager THEN action.category = read OR action.category = write"
     */
    public AccessExpression parse(String rule) {
        if (rule == null || rule.trim().isEmpty()) {
            throw new IllegalArgumentException("Access rule cannot be null or empty");
        }
        
        String[] tokens = tokenize(rule.trim());
        return parseExpression(tokens, new int[]{0});
    }
    
    private String[] tokenize(String rule) {
        return rule.replace("(", " ( ")
                   .replace(")", " ) ")
                   .replace(">=", " >= ")
                   .replace("<=", " <= ")
                   .replace("!=", " != ")
                   .replace("=", " = ")
                   .replace(">", " > ")
                   .replace("<", " < ")
                   .replaceAll("\\s+", " ")
                   .split(" ");
    }
    
    private AccessExpression parseExpression(String[] tokens, int[] position) {
        AccessExpression left = parseTerm(tokens, position);
        
        while (position[0] < tokens.length && isLogicalOperator(tokens[position[0]])) {
            String operator = tokens[position[0]++];
            AccessExpression right = parseTerm(tokens, position);
            
            left = switch (operator.toUpperCase()) {
                case "AND" -> AndExpression.of(left, right);
                case "OR" -> OrExpression.of(left, right);
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        }
        
        return left;
    }
    
    private AccessExpression parseTerm(String[] tokens, int[] position) {
        if (position[0] >= tokens.length) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }
        
        String token = tokens[position[0]];
        
        return switch (token.toUpperCase()) {
            case "NOT" -> {
                position[0]++; // consume 'NOT'
                yield new NotExpression(parseTerm(tokens, position));
            }
            case "IF" -> parseConditionalExpression(tokens, position);
            case "(" -> {
                position[0]++; // consume '('
                AccessExpression expr = parseExpression(tokens, position);
                if (position[0] >= tokens.length || !tokens[position[0]].equals(")")) {
                    throw new IllegalArgumentException("Missing closing parenthesis");
                }
                position[0]++; // consume ')'
                yield expr;
            }
            default -> {
                if (token.startsWith("subject.")) {
                    yield parseSubjectExpression(tokens, position);
                } else if (token.startsWith("object.")) {
                    yield parseObjectExpression(tokens, position);
                } else if (token.startsWith("action.")) {
                    yield parseActionExpression(tokens, position);
                } else if (token.startsWith("env.")) {
                    yield parseEnvironmentExpression(tokens, position);
                } else {
                    throw new IllegalArgumentException("Unknown token: " + token);
                }
            }
        };
    }
    
    private AccessExpression parseConditionalExpression(String[] tokens, int[] position) {
        position[0]++; // consume 'IF'
        
        AccessExpression condition = parseExpression(tokens, position);
        
        if (position[0] >= tokens.length || !tokens[position[0]].equalsIgnoreCase("THEN")) {
            throw new IllegalArgumentException("Expected THEN after IF condition");
        }
        position[0]++; // consume 'THEN'
        
        AccessExpression thenExpr = parseExpression(tokens, position);
        
        if (position[0] < tokens.length && tokens[position[0]].equalsIgnoreCase("ELSE")) {
            position[0]++; // consume 'ELSE'
            AccessExpression elseExpr = parseExpression(tokens, position);
            return new ConditionalExpression(condition, thenExpr, elseExpr);
        } else {
            return new ConditionalExpression(condition, thenExpr);
        }
    }
    
    private AccessExpression parseSubjectExpression(String[] tokens, int[] position) {
        String attribute = tokens[position[0]++].substring("subject.".length());
        SubjectExpression.ComparisonOperator operator = parseOperator(tokens, position);
        String value = parseValue(tokens, position);
        
        return new SubjectExpression(attribute, value, operator);
    }
    
    private AccessExpression parseObjectExpression(String[] tokens, int[] position) {
        String attribute = tokens[position[0]++].substring("object.".length());
        SubjectExpression.ComparisonOperator operator = parseOperator(tokens, position);
        String value = parseValue(tokens, position);
        
        return new ObjectExpression(attribute, value, operator);
    }
    
    private AccessExpression parseActionExpression(String[] tokens, int[] position) {
        String attribute = tokens[position[0]++].substring("action.".length());
        SubjectExpression.ComparisonOperator operator = parseOperator(tokens, position);
        String value = parseValue(tokens, position);
        
        return new ActionExpression(attribute, value, operator);
    }
    
    private AccessExpression parseEnvironmentExpression(String[] tokens, int[] position) {
        String attribute = tokens[position[0]++].substring("env.".length());
        SubjectExpression.ComparisonOperator operator = parseOperator(tokens, position);
        String value = parseValue(tokens, position);
        
        return new EnvironmentExpression(attribute, value, operator);
    }
    
    private SubjectExpression.ComparisonOperator parseOperator(String[] tokens, int[] position) {
        if (position[0] >= tokens.length) {
            throw new IllegalArgumentException("Expected operator");
        }
        
        String op = tokens[position[0]++];
        return switch (op) {
            case "=" -> SubjectExpression.ComparisonOperator.EQUALS;
            case "!=" -> SubjectExpression.ComparisonOperator.NOT_EQUALS;
            case ">" -> SubjectExpression.ComparisonOperator.GREATER_THAN;
            case "<" -> SubjectExpression.ComparisonOperator.LESS_THAN;
            case ">=" -> SubjectExpression.ComparisonOperator.GREATER_EQUAL;
            case "<=" -> SubjectExpression.ComparisonOperator.LESS_EQUAL;
            case "contains" -> SubjectExpression.ComparisonOperator.CONTAINS;
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }
    
    private String parseValue(String[] tokens, int[] position) {
        if (position[0] >= tokens.length) {
            throw new IllegalArgumentException("Expected value");
        }
        
        String value = tokens[position[0]++];
        
        // Remove quotes if present
        if ((value.startsWith("\"") && value.endsWith("\"")) || 
            (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length() - 1);
        }
        
        return value;
    }
    
    private boolean isLogicalOperator(String token) {
        return "AND".equalsIgnoreCase(token) || "OR".equalsIgnoreCase(token);
    }
}