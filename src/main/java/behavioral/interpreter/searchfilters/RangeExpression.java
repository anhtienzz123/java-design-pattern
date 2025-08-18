package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Terminal expression for range-based filtering.
 * Supports numeric and date/time ranges with inclusive/exclusive bounds.
 */
public class RangeExpression implements SearchExpression {
    private final String fieldName;
    private final Object minValue;
    private final Object maxValue;
    private final boolean minInclusive;
    private final boolean maxInclusive;
    private final RangeType rangeType;
    
    public enum RangeType {
        NUMERIC, DATE_TIME, STRING_LENGTH, LIST_SIZE
    }
    
    public RangeExpression(String fieldName, Object minValue, Object maxValue, 
                          boolean minInclusive, boolean maxInclusive, RangeType rangeType) {
        this.fieldName = fieldName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        this.rangeType = rangeType;
    }
    
    // Convenience constructors
    public static RangeExpression numeric(String fieldName, Number min, Number max) {
        return new RangeExpression(fieldName, min, max, true, true, RangeType.NUMERIC);
    }
    
    public static RangeExpression numericExclusive(String fieldName, Number min, Number max) {
        return new RangeExpression(fieldName, min, max, false, false, RangeType.NUMERIC);
    }
    
    public static RangeExpression dateTime(String fieldName, LocalDateTime min, LocalDateTime max) {
        return new RangeExpression(fieldName, min, max, true, true, RangeType.DATE_TIME);
    }
    
    public static RangeExpression stringLength(String fieldName, int min, int max) {
        return new RangeExpression(fieldName, min, max, true, true, RangeType.STRING_LENGTH);
    }
    
    public static RangeExpression listSize(String fieldName, int min, int max) {
        return new RangeExpression(fieldName, min, max, true, true, RangeType.LIST_SIZE);
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        if (!item.hasField(fieldName)) {
            return false;
        }
        
        Object fieldValue = item.getFieldValue(fieldName);
        if (fieldValue == null) {
            return false;
        }
        
        return switch (rangeType) {
            case NUMERIC -> matchesNumericRange(fieldValue);
            case DATE_TIME -> matchesDateTimeRange(fieldValue);
            case STRING_LENGTH -> matchesStringLengthRange(fieldValue);
            case LIST_SIZE -> matchesListSizeRange(fieldValue);
        };
    }
    
    private boolean matchesNumericRange(Object fieldValue) {
        if (!(fieldValue instanceof Number number)) {
            return false;
        }
        
        double value = number.doubleValue();
        
        // Check minimum bound
        if (minValue != null) {
            double min = ((Number) minValue).doubleValue();
            if (minInclusive ? value < min : value <= min) {
                return false;
            }
        }
        
        // Check maximum bound
        if (maxValue != null) {
            double max = ((Number) maxValue).doubleValue();
            if (maxInclusive ? value > max : value >= max) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean matchesDateTimeRange(Object fieldValue) {
        LocalDateTime dateTime;
        
        if (fieldValue instanceof LocalDateTime localDateTime) {
            dateTime = localDateTime;
        } else if (fieldValue instanceof String dateString) {
            try {
                dateTime = LocalDateTime.parse(dateString);
            } catch (DateTimeParseException e) {
                return false;
            }
        } else {
            return false;
        }
        
        // Check minimum bound
        if (minValue instanceof LocalDateTime min) {
            if (minInclusive ? dateTime.isBefore(min) : !dateTime.isAfter(min)) {
                return false;
            }
        }
        
        // Check maximum bound
        if (maxValue instanceof LocalDateTime max) {
            if (maxInclusive ? dateTime.isAfter(max) : !dateTime.isBefore(max)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean matchesStringLengthRange(Object fieldValue) {
        if (!(fieldValue instanceof String string)) {
            return false;
        }
        
        int length = string.length();
        
        // Check minimum bound
        if (minValue instanceof Number min) {
            int minLen = min.intValue();
            if (minInclusive ? length < minLen : length <= minLen) {
                return false;
            }
        }
        
        // Check maximum bound
        if (maxValue instanceof Number max) {
            int maxLen = max.intValue();
            if (maxInclusive ? length > maxLen : length >= maxLen) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean matchesListSizeRange(Object fieldValue) {
        int size;
        
        if (fieldValue instanceof java.util.Collection<?> collection) {
            size = collection.size();
        } else if (fieldValue instanceof Object[] array) {
            size = array.length;
        } else {
            return false;
        }
        
        // Check minimum bound
        if (minValue instanceof Number min) {
            int minSize = min.intValue();
            if (minInclusive ? size < minSize : size <= minSize) {
                return false;
            }
        }
        
        // Check maximum bound
        if (maxValue instanceof Number max) {
            int maxSize = max.intValue();
            if (maxInclusive ? size > maxSize : size >= maxSize) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        
        String typeStr = switch (rangeType) {
            case NUMERIC -> "";
            case DATE_TIME -> ".date";
            case STRING_LENGTH -> ".length";
            case LIST_SIZE -> ".size";
        };
        sb.append(typeStr);
        
        sb.append(" ");
        
        if (minValue != null && maxValue != null) {
            sb.append(minInclusive ? "[" : "(");
            sb.append(minValue);
            sb.append(", ");
            sb.append(maxValue);
            sb.append(maxInclusive ? "]" : ")");
        } else if (minValue != null) {
            sb.append(minInclusive ? ">= " : "> ");
            sb.append(minValue);
        } else if (maxValue != null) {
            sb.append(maxInclusive ? "<= " : "< ");
            sb.append(maxValue);
        }
        
        return sb.toString();
    }
    
    @Override
    public double getSelectivity() {
        return switch (rangeType) {
            case NUMERIC -> 0.3;
            case DATE_TIME -> 0.25;
            case STRING_LENGTH -> 0.4;
            case LIST_SIZE -> 0.35;
        };
    }
    
    @Override
    public int getPriority() {
        return switch (rangeType) {
            case NUMERIC -> 8;
            case DATE_TIME -> 7;
            case STRING_LENGTH, LIST_SIZE -> 6;
        };
    }
}