package behavioral.interpreter.searchfilters;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Terminal expression for tag-based filtering.
 * Supports various tag matching strategies including set operations.
 */
public class TagExpression implements SearchExpression {
    private final Set<String> tags;
    private final TagMatchMode mode;
    private final boolean caseSensitive;
    
    public enum TagMatchMode {
        HAS_ANY,        // Item has any of the specified tags
        HAS_ALL,        // Item has all of the specified tags
        HAS_ONLY,       // Item has only the specified tags (no others)
        HAS_NONE,       // Item has none of the specified tags
        EXACT_MATCH     // Item has exactly the specified tags
    }
    
    public TagExpression(Set<String> tags, TagMatchMode mode) {
        this(tags, mode, false);
    }
    
    public TagExpression(Set<String> tags, TagMatchMode mode, boolean caseSensitive) {
        this.tags = caseSensitive ? tags : 
                   tags.stream().map(String::toLowerCase).collect(Collectors.toSet());
        this.mode = mode;
        this.caseSensitive = caseSensitive;
    }
    
    // Convenience constructors
    public static TagExpression hasAny(String... tags) {
        return new TagExpression(Set.of(tags), TagMatchMode.HAS_ANY);
    }
    
    public static TagExpression hasAll(String... tags) {
        return new TagExpression(Set.of(tags), TagMatchMode.HAS_ALL);
    }
    
    public static TagExpression hasOnly(String... tags) {
        return new TagExpression(Set.of(tags), TagMatchMode.HAS_ONLY);
    }
    
    public static TagExpression hasNone(String... tags) {
        return new TagExpression(Set.of(tags), TagMatchMode.HAS_NONE);
    }
    
    public static TagExpression exactMatch(String... tags) {
        return new TagExpression(Set.of(tags), TagMatchMode.EXACT_MATCH);
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        Set<String> itemTags = item.getTags();
        if (itemTags == null || itemTags.isEmpty()) {
            return mode == TagMatchMode.HAS_NONE || 
                   (mode == TagMatchMode.EXACT_MATCH && tags.isEmpty());
        }
        
        // Normalize case if needed
        Set<String> normalizedItemTags = caseSensitive ? itemTags :
            itemTags.stream().map(String::toLowerCase).collect(Collectors.toSet());
        
        return switch (mode) {
            case HAS_ANY -> hasAnyTag(normalizedItemTags);
            case HAS_ALL -> hasAllTags(normalizedItemTags);
            case HAS_ONLY -> hasOnlyTags(normalizedItemTags);
            case HAS_NONE -> hasNoTags(normalizedItemTags);
            case EXACT_MATCH -> hasExactTags(normalizedItemTags);
        };
    }
    
    private boolean hasAnyTag(Set<String> itemTags) {
        return tags.stream().anyMatch(itemTags::contains);
    }
    
    private boolean hasAllTags(Set<String> itemTags) {
        return itemTags.containsAll(tags);
    }
    
    private boolean hasOnlyTags(Set<String> itemTags) {
        return tags.containsAll(itemTags);
    }
    
    private boolean hasNoTags(Set<String> itemTags) {
        return tags.stream().noneMatch(itemTags::contains);
    }
    
    private boolean hasExactTags(Set<String> itemTags) {
        return itemTags.equals(tags);
    }
    
    @Override
    public String getQueryString() {
        String tagList = String.join(", ", tags);
        String modeStr = switch (mode) {
            case HAS_ANY -> "HAS_ANY";
            case HAS_ALL -> "HAS_ALL";
            case HAS_ONLY -> "HAS_ONLY";
            case HAS_NONE -> "HAS_NONE";
            case EXACT_MATCH -> "EXACT_MATCH";
        };
        
        String caseStr = caseSensitive ? " CASE_SENSITIVE" : "";
        return "tags " + modeStr + caseStr + " [" + tagList + "]";
    }
    
    @Override
    public double getSelectivity() {
        return switch (mode) {
            case HAS_ANY -> Math.min(0.8, tags.size() * 0.2);
            case HAS_ALL -> Math.max(0.1, 1.0 / (tags.size() * 2));
            case HAS_ONLY -> 0.2;
            case HAS_NONE -> Math.max(0.2, 1.0 - (tags.size() * 0.15));
            case EXACT_MATCH -> 0.05;
        };
    }
    
    @Override
    public int getPriority() {
        return switch (mode) {
            case EXACT_MATCH -> 10;
            case HAS_ALL -> 9;
            case HAS_ONLY -> 8;
            case HAS_NONE -> 7;
            case HAS_ANY -> 6;
        };
    }
}