package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser that converts string-based search queries into expression trees.
 * Supports a comprehensive search query language with various operators and functions.
 */
public class SearchQueryParser {
    
    // Regex patterns for different query components
    private static final Pattern FIELD_PATTERN = Pattern.compile(
        "(\\w+)\\s*(=|!=|>|<|>=|<=|CONTAINS|STARTS_WITH|ENDS_WITH|MATCHES)\\s*(.+)",
        Pattern.CASE_INSENSITIVE
    );
    
    private static final Pattern RANGE_PATTERN = Pattern.compile(
        "(\\w+)\\s*BETWEEN\\s+(.+?)\\s+AND\\s+(.+)",
        Pattern.CASE_INSENSITIVE
    );
    
    private static final Pattern TEXT_PATTERN = Pattern.compile(
        "TEXT\\s*\\(\\s*([^,]+?)\\s*(?:,\\s*(\\w+))?\\s*\\)",
        Pattern.CASE_INSENSITIVE
    );
    
    private static final Pattern TAG_PATTERN = Pattern.compile(
        "TAGS\\s+(HAS_ANY|HAS_ALL|HAS_NONE|HAS_ONLY|EXACT)\\s*\\[([^\\]]+)\\]",
        Pattern.CASE_INSENSITIVE
    );
    
    private static final Pattern QUOTED_STRING_PATTERN = Pattern.compile(
        "\"([^\"]*)\"|'([^']*)'"
    );
    
    /**
     * Parses a search query string and returns an expression tree.
     * 
     * Supported syntax:
     * - Field operations: name = "John", age > 25, category CONTAINS "tech"
     * - Range queries: price BETWEEN 10 AND 100, date BETWEEN 2024-01-01 AND 2024-12-31
     * - Text search: TEXT("search terms"), TEXT("search terms", FUZZY)
     * - Tag operations: TAGS HAS_ANY [tag1, tag2], TAGS HAS_ALL [important, urgent]
     * - Logical operators: AND, OR, NOT
     * - Parentheses for grouping: (condition1 OR condition2) AND condition3
     * 
     * Examples:
     * - "name = \"John Doe\" AND age > 25"
     * - "category = tech OR category = science"
     * - "TEXT(\"machine learning\", FUZZY) AND TAGS HAS_ANY [ai, ml]"
     * - "price BETWEEN 100 AND 500 AND NOT status = sold"
     */
    public SearchExpression parse(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }
        
        // Normalize the query
        String normalizedQuery = normalizeQuery(query.trim());
        
        // Tokenize and parse
        List<String> tokens = tokenize(normalizedQuery);
        return parseExpression(tokens, new int[]{0});
    }
    
    private String normalizeQuery(String query) {
        // Add spaces around operators and parentheses for easier tokenization
        return query.replace("(", " ( ")
                   .replace(")", " ) ")
                   .replaceAll("\\s+", " ")
                   .trim();
    }
    
    private List<String> tokenize(String query) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = '"';
        
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            
            if (!inQuotes && (c == '"' || c == '\'')) {
                inQuotes = true;
                quoteChar = c;
                currentToken.append(c);
            } else if (inQuotes && c == quoteChar) {
                inQuotes = false;
                currentToken.append(c);
            } else if (!inQuotes && c == ' ') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(c);
            }
        }
        
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        
        return tokens;
    }
    
    private SearchExpression parseExpression(List<String> tokens, int[] position) {
        SearchExpression left = parseTerm(tokens, position);
        
        while (position[0] < tokens.size() && isLogicalOperator(tokens.get(position[0]))) {
            String operator = tokens.get(position[0]++);
            SearchExpression right = parseTerm(tokens, position);
            
            left = switch (operator.toUpperCase()) {
                case "AND" -> AndExpression.of(left, right);
                case "OR" -> OrExpression.of(left, right);
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        }
        
        return left;
    }
    
    private SearchExpression parseTerm(List<String> tokens, int[] position) {
        if (position[0] >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of query");
        }
        
        String token = tokens.get(position[0]);
        
        return switch (token.toUpperCase()) {
            case "NOT" -> {
                position[0]++; // consume 'NOT'
                yield new NotExpression(parseTerm(tokens, position));
            }
            case "(" -> {
                position[0]++; // consume '('
                SearchExpression expr = parseExpression(tokens, position);
                if (position[0] >= tokens.size() || !tokens.get(position[0]).equals(")")) {
                    throw new IllegalArgumentException("Missing closing parenthesis");
                }
                position[0]++; // consume ')'
                yield expr;
            }
            case "TEXT" -> parseTextExpression(tokens, position);
            case "TAGS" -> parseTagExpression(tokens, position);
            default -> parseFieldOrRangeExpression(tokens, position);
        };
    }
    
    private SearchExpression parseTextExpression(List<String> tokens, int[] position) {
        // Reconstruct the TEXT(...) expression
        StringBuilder textExpr = new StringBuilder();
        int depth = 0;
        
        while (position[0] < tokens.size()) {
            String token = tokens.get(position[0]++);
            textExpr.append(token).append(" ");
            
            if (token.contains("(")) depth++;
            if (token.contains(")")) depth--;
            
            if (depth == 0) break;
        }
        
        // Parse the TEXT expression
        Matcher matcher = TEXT_PATTERN.matcher(textExpr.toString());
        if (matcher.find()) {
            String searchText = unquote(matcher.group(1));
            String modeStr = matcher.group(2);
            
            TextSearchExpression.TextSearchMode mode = TextSearchExpression.TextSearchMode.ANY_WORD;
            if (modeStr != null) {
                try {
                    mode = TextSearchExpression.TextSearchMode.valueOf(modeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown text search mode: " + modeStr);
                }
            }
            
            return new TextSearchExpression(searchText, mode);
        }
        
        throw new IllegalArgumentException("Invalid TEXT expression: " + textExpr);
    }
    
    private SearchExpression parseTagExpression(List<String> tokens, int[] position) {
        // Reconstruct the TAGS expression
        StringBuilder tagExpr = new StringBuilder();
        
        while (position[0] < tokens.size()) {
            String token = tokens.get(position[0]++);
            tagExpr.append(token).append(" ");
            
            if (token.contains("]")) break;
        }
        
        // Parse the TAGS expression
        Matcher matcher = TAG_PATTERN.matcher(tagExpr.toString());
        if (matcher.find()) {
            String modeStr = matcher.group(1);
            String tagsStr = matcher.group(2);
            
            TagExpression.TagMatchMode mode = switch (modeStr.toUpperCase()) {
                case "HAS_ANY" -> TagExpression.TagMatchMode.HAS_ANY;
                case "HAS_ALL" -> TagExpression.TagMatchMode.HAS_ALL;
                case "HAS_NONE" -> TagExpression.TagMatchMode.HAS_NONE;
                case "HAS_ONLY" -> TagExpression.TagMatchMode.HAS_ONLY;
                case "EXACT" -> TagExpression.TagMatchMode.EXACT_MATCH;
                default -> throw new IllegalArgumentException("Unknown tag mode: " + modeStr);
            };
            
            Set<String> tags = Set.of(tagsStr.split("\\s*,\\s*"));
            return new TagExpression(tags, mode);
        }
        
        throw new IllegalArgumentException("Invalid TAGS expression: " + tagExpr);
    }
    
    private SearchExpression parseFieldOrRangeExpression(List<String> tokens, int[] position) {
        // Reconstruct the field expression
        StringBuilder fieldExpr = new StringBuilder();
        int startPos = position[0];
        
        // Look ahead to determine if this is a BETWEEN expression
        boolean isBetween = false;
        for (int i = position[0]; i < Math.min(position[0] + 5, tokens.size()); i++) {
            if ("BETWEEN".equalsIgnoreCase(tokens.get(i))) {
                isBetween = true;
                break;
            }
        }
        
        if (isBetween) {
            // Parse BETWEEN expression
            while (position[0] < tokens.size()) {
                String token = tokens.get(position[0]++);
                fieldExpr.append(token).append(" ");
                
                if (position[0] > startPos + 1 && 
                    !isPartOfExpression(token) && 
                    !isLogicalOperator(token)) {
                    break;
                }
            }
            
            return parseRangeExpression(fieldExpr.toString().trim());
        } else {
            // Parse regular field expression
            while (position[0] < tokens.size()) {
                String token = tokens.get(position[0]++);
                fieldExpr.append(token).append(" ");
                
                if (position[0] > startPos + 2 && 
                    !isPartOfExpression(token) && 
                    !isLogicalOperator(token)) {
                    break;
                }
            }
            
            return parseFieldExpression(fieldExpr.toString().trim());
        }
    }
    
    private SearchExpression parseFieldExpression(String expression) {
        Matcher matcher = FIELD_PATTERN.matcher(expression);
        if (matcher.find()) {
            String fieldName = matcher.group(1);
            String operatorStr = matcher.group(2);
            String valueStr = matcher.group(3);
            
            FieldExpression.ComparisonOperator operator = switch (operatorStr.toUpperCase()) {
                case "=" -> FieldExpression.ComparisonOperator.EQUALS;
                case "!=" -> FieldExpression.ComparisonOperator.NOT_EQUALS;
                case ">" -> FieldExpression.ComparisonOperator.GREATER_THAN;
                case "<" -> FieldExpression.ComparisonOperator.LESS_THAN;
                case ">=" -> FieldExpression.ComparisonOperator.GREATER_EQUAL;
                case "<=" -> FieldExpression.ComparisonOperator.LESS_EQUAL;
                case "CONTAINS" -> FieldExpression.ComparisonOperator.CONTAINS;
                case "STARTS_WITH" -> FieldExpression.ComparisonOperator.STARTS_WITH;
                case "ENDS_WITH" -> FieldExpression.ComparisonOperator.ENDS_WITH;
                case "MATCHES" -> FieldExpression.ComparisonOperator.REGEX;
                default -> throw new IllegalArgumentException("Unknown operator: " + operatorStr);
            };
            
            Object value = parseValue(valueStr);
            return new FieldExpression(fieldName, operator, value);
        }
        
        throw new IllegalArgumentException("Invalid field expression: " + expression);
    }
    
    private SearchExpression parseRangeExpression(String expression) {
        Matcher matcher = RANGE_PATTERN.matcher(expression);
        if (matcher.find()) {
            String fieldName = matcher.group(1);
            String minValueStr = matcher.group(2);
            String maxValueStr = matcher.group(3);
            
            Object minValue = parseValue(minValueStr);
            Object maxValue = parseValue(maxValueStr);
            
            // Determine range type based on value types
            RangeExpression.RangeType rangeType;
            if (minValue instanceof Number && maxValue instanceof Number) {
                rangeType = RangeExpression.RangeType.NUMERIC;
            } else if (minValue instanceof LocalDateTime && maxValue instanceof LocalDateTime) {
                rangeType = RangeExpression.RangeType.DATE_TIME;
            } else {
                throw new IllegalArgumentException("Incompatible range value types");
            }
            
            return new RangeExpression(fieldName, minValue, maxValue, true, true, rangeType);
        }
        
        throw new IllegalArgumentException("Invalid range expression: " + expression);
    }
    
    private Object parseValue(String valueStr) {
        valueStr = valueStr.trim();
        
        // Handle quoted strings
        if ((valueStr.startsWith("\"") && valueStr.endsWith("\"")) ||
            (valueStr.startsWith("'") && valueStr.endsWith("'"))) {
            return valueStr.substring(1, valueStr.length() - 1);
        }
        
        // Handle numbers
        try {
            if (valueStr.contains(".")) {
                return Double.parseDouble(valueStr);
            } else {
                return Long.parseLong(valueStr);
            }
        } catch (NumberFormatException e) {
            // Not a number, continue
        }
        
        // Handle dates
        try {
            return LocalDateTime.parse(valueStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            // Not a date, continue
        }
        
        // Handle booleans
        if ("true".equalsIgnoreCase(valueStr) || "false".equalsIgnoreCase(valueStr)) {
            return Boolean.parseBoolean(valueStr);
        }
        
        // Default to string
        return valueStr;
    }
    
    private String unquote(String str) {
        if ((str.startsWith("\"") && str.endsWith("\"")) ||
            (str.startsWith("'") && str.endsWith("'"))) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
    
    private boolean isLogicalOperator(String token) {
        return "AND".equalsIgnoreCase(token) || "OR".equalsIgnoreCase(token);
    }
    
    private boolean isPartOfExpression(String token) {
        return token.contains("\"") || token.contains("'") || 
               token.equals("(") || token.equals(")") ||
               "BETWEEN".equalsIgnoreCase(token) || "AND".equalsIgnoreCase(token) ||
               token.matches("^[=!<>]+$");
    }
}