package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Terminal expression for field-based filtering.
 * Supports various comparison operations on different field types.
 */
public class FieldExpression implements SearchExpression {
    private final String fieldName;
    private final Object expectedValue;
    private final ComparisonOperator operator;
    private final Pattern regexPattern;
    
    public enum ComparisonOperator {
        EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL,
        CONTAINS, NOT_CONTAINS, STARTS_WITH, ENDS_WITH, REGEX, IN, NOT_IN,
        IS_NULL, IS_NOT_NULL, IS_EMPTY, IS_NOT_EMPTY
    }
    
    public FieldExpression(String fieldName, ComparisonOperator operator, Object expectedValue) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.expectedValue = expectedValue;
        this.regexPattern = (operator == ComparisonOperator.REGEX && expectedValue instanceof String) ?
            Pattern.compile((String) expectedValue, Pattern.CASE_INSENSITIVE) : null;
    }
    
    public FieldExpression(String fieldName, ComparisonOperator operator) {
        this(fieldName, operator, null);
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        if (!item.hasField(fieldName)) {
            return operator == ComparisonOperator.IS_NULL;
        }
        
        Object actualValue = item.getFieldValue(fieldName);
        
        return switch (operator) {
            case EQUALS -> equals(actualValue, expectedValue);
            case NOT_EQUALS -> !equals(actualValue, expectedValue);
            case GREATER_THAN -> compareNumbers(actualValue, expectedValue) > 0;
            case LESS_THAN -> compareNumbers(actualValue, expectedValue) < 0;
            case GREATER_EQUAL -> compareNumbers(actualValue, expectedValue) >= 0;
            case LESS_EQUAL -> compareNumbers(actualValue, expectedValue) <= 0;
            case CONTAINS -> contains(actualValue, expectedValue);
            case NOT_CONTAINS -> !contains(actualValue, expectedValue);
            case STARTS_WITH -> startsWith(actualValue, expectedValue);
            case ENDS_WITH -> endsWith(actualValue, expectedValue);
            case REGEX -> matchesRegex(actualValue);
            case IN -> isIn(actualValue, expectedValue);
            case NOT_IN -> !isIn(actualValue, expectedValue);
            case IS_NULL -> actualValue == null;
            case IS_NOT_NULL -> actualValue != null;
            case IS_EMPTY -> isEmpty(actualValue);
            case IS_NOT_EMPTY -> !isEmpty(actualValue);
        };
    }
    
    private boolean equals(Object actual, Object expected) {
        if (actual == null && expected == null) return true;
        if (actual == null || expected == null) return false;
        
        // Handle different numeric types
        if (actual instanceof Number actualNum && expected instanceof Number expectedNum) {
            return Double.compare(actualNum.doubleValue(), expectedNum.doubleValue()) == 0;
        }
        
        // Handle case-insensitive string comparison
        if (actual instanceof String actualStr && expected instanceof String expectedStr) {
            return actualStr.equalsIgnoreCase(expectedStr);
        }
        
        return actual.equals(expected);
    }
    
    private int compareNumbers(Object actual, Object expected) {
        if (!(actual instanceof Number) || !(expected instanceof Number)) {
            throw new IllegalArgumentException("Cannot compare non-numeric values: " + actual + " and " + expected);
        }
        
        double actualDouble = ((Number) actual).doubleValue();
        double expectedDouble = ((Number) expected).doubleValue();
        return Double.compare(actualDouble, expectedDouble);
    }
    
    private boolean contains(Object actual, Object expected) {
        if (actual == null || expected == null) return false;
        
        if (actual instanceof String actualStr && expected instanceof String expectedStr) {
            return actualStr.toLowerCase().contains(expectedStr.toLowerCase());
        }
        
        if (actual instanceof List<?> list) {
            return list.contains(expected);
        }
        
        return false;
    }
    
    private boolean startsWith(Object actual, Object expected) {
        if (actual instanceof String actualStr && expected instanceof String expectedStr) {
            return actualStr.toLowerCase().startsWith(expectedStr.toLowerCase());
        }
        return false;
    }
    
    private boolean endsWith(Object actual, Object expected) {
        if (actual instanceof String actualStr && expected instanceof String expectedStr) {
            return actualStr.toLowerCase().endsWith(expectedStr.toLowerCase());
        }
        return false;
    }
    
    private boolean matchesRegex(Object actual) {
        if (regexPattern == null || !(actual instanceof String)) return false;
        return regexPattern.matcher((String) actual).matches();
    }
    
    private boolean isIn(Object actual, Object expected) {
        if (expected instanceof List<?> list) {
            return list.contains(actual);
        }
        if (expected instanceof Object[] array) {
            for (Object item : array) {
                if (equals(actual, item)) return true;
            }
        }
        return false;
    }
    
    private boolean isEmpty(Object actual) {
        if (actual == null) return true;
        if (actual instanceof String str) return str.trim().isEmpty();
        if (actual instanceof List<?> list) return list.isEmpty();
        return false;
    }
    
    @Override
    public String getQueryString() {
        return switch (operator) {
            case IS_NULL -> fieldName + " IS NULL";
            case IS_NOT_NULL -> fieldName + " IS NOT NULL";
            case IS_EMPTY -> fieldName + " IS EMPTY";
            case IS_NOT_EMPTY -> fieldName + " IS NOT EMPTY";
            default -> fieldName + " " + operatorToString() + " " + valueToString();
        };
    }
    
    private String operatorToString() {
        return switch (operator) {
            case EQUALS -> "=";
            case NOT_EQUALS -> "!=";
            case GREATER_THAN -> ">";
            case LESS_THAN -> "<";
            case GREATER_EQUAL -> ">=";
            case LESS_EQUAL -> "<=";
            case CONTAINS -> "CONTAINS";
            case NOT_CONTAINS -> "NOT CONTAINS";
            case STARTS_WITH -> "STARTS WITH";
            case ENDS_WITH -> "ENDS WITH";
            case REGEX -> "MATCHES";
            case IN -> "IN";
            case NOT_IN -> "NOT IN";
            default -> operator.name();
        };
    }
    
    private String valueToString() {
        if (expectedValue == null) return "NULL";
        if (expectedValue instanceof String) return "\"" + expectedValue + "\"";
        if (expectedValue instanceof List<?> list) return list.toString();
        return expectedValue.toString();
    }
    
    @Override
    public double getSelectivity() {
        return switch (operator) {
            case EQUALS -> 0.1;
            case NOT_EQUALS -> 0.9;
            case CONTAINS, STARTS_WITH, ENDS_WITH -> 0.3;
            case REGEX -> 0.2;
            case GREATER_THAN, LESS_THAN -> 0.4;
            case GREATER_EQUAL, LESS_EQUAL -> 0.5;
            case IN -> expectedValue instanceof List<?> list ? 
                Math.min(0.8, list.size() * 0.1) : 0.3;
            case IS_NULL, IS_EMPTY -> 0.05;
            case IS_NOT_NULL, IS_NOT_EMPTY -> 0.95;
            default -> 0.5;
        };
    }
    
    @Override
    public int getPriority() {
        return switch (operator) {
            case EQUALS -> 10;
            case IS_NULL, IS_NOT_NULL -> 9;
            case IN, NOT_IN -> 8;
            case GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL -> 7;
            case STARTS_WITH, ENDS_WITH -> 6;
            case CONTAINS, NOT_CONTAINS -> 5;
            case REGEX -> 3;
            default -> 1;
        };
    }
}