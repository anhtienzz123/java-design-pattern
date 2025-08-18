package behavioral.interpreter.searchfilters;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Terminal expression for full-text search operations.
 * Supports various text matching strategies including fuzzy matching.
 */
public class TextSearchExpression implements SearchExpression {
    private final String searchText;
    private final TextSearchMode mode;
    private final boolean caseSensitive;
    private final int fuzzyDistance;
    private final Pattern regexPattern;
    
    public enum TextSearchMode {
        EXACT_MATCH,        // Exact phrase match
        ALL_WORDS,          // All words must be present
        ANY_WORD,           // Any word must be present
        PHRASE,             // Exact phrase in order
        WILDCARD,           // Wildcard matching with * and ?
        REGEX,              // Regular expression matching
        FUZZY,              // Fuzzy/approximate matching
        PREFIX,             // Prefix matching
        SUFFIX              // Suffix matching
    }
    
    public TextSearchExpression(String searchText, TextSearchMode mode) {
        this(searchText, mode, false, 2);
    }
    
    public TextSearchExpression(String searchText, TextSearchMode mode, boolean caseSensitive, int fuzzyDistance) {
        this.searchText = searchText;
        this.mode = mode;
        this.caseSensitive = caseSensitive;
        this.fuzzyDistance = fuzzyDistance;
        
        // Pre-compile regex pattern if needed
        this.regexPattern = (mode == TextSearchMode.REGEX) ?
            Pattern.compile(searchText, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE) : null;
    }
    
    @Override
    public boolean matches(SearchableItem item) {
        String content = item.getSearchableText();
        if (content == null || content.trim().isEmpty()) {
            return false;
        }
        
        String searchContent = caseSensitive ? content : content.toLowerCase();
        String searchTerm = caseSensitive ? searchText : searchText.toLowerCase();
        
        return switch (mode) {
            case EXACT_MATCH -> searchContent.equals(searchTerm);
            case ALL_WORDS -> containsAllWords(searchContent, searchTerm);
            case ANY_WORD -> containsAnyWord(searchContent, searchTerm);
            case PHRASE -> searchContent.contains(searchTerm);
            case WILDCARD -> matchesWildcard(searchContent, searchTerm);
            case REGEX -> regexPattern != null && regexPattern.matcher(content).find();
            case FUZZY -> isFuzzyMatch(searchContent, searchTerm);
            case PREFIX -> searchContent.startsWith(searchTerm);
            case SUFFIX -> searchContent.endsWith(searchTerm);
        };
    }
    
    private boolean containsAllWords(String content, String searchTerm) {
        String[] words = searchTerm.split("\\s+");
        for (String word : words) {
            if (!content.contains(word.trim())) {
                return false;
            }
        }
        return true;
    }
    
    private boolean containsAnyWord(String content, String searchTerm) {
        String[] words = searchTerm.split("\\s+");
        for (String word : words) {
            if (content.contains(word.trim())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean matchesWildcard(String content, String searchTerm) {
        // Convert wildcard pattern to regex
        String regexPattern = searchTerm
            .replace(".", "\\.")
            .replace("*", ".*")
            .replace("?", ".");
        
        return Pattern.compile(regexPattern, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE)
                     .matcher(content)
                     .find();
    }
    
    private boolean isFuzzyMatch(String content, String searchTerm) {
        // Simple fuzzy matching using Levenshtein distance
        String[] contentWords = content.split("\\s+");
        String[] searchWords = searchTerm.split("\\s+");
        
        // Check if any content word is within fuzzy distance of any search word
        for (String contentWord : contentWords) {
            for (String searchWord : searchWords) {
                if (levenshteinDistance(contentWord, searchWord) <= fuzzyDistance) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        
        if (len1 == 0) return len2;
        if (len2 == 0) return len1;
        
        int[][] dp = new int[len1 + 1][len2 + 1];
        
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                    dp[i - 1][j] + 1,      // deletion
                    dp[i][j - 1] + 1),     // insertion
                    dp[i - 1][j - 1] + cost // substitution
                );
            }
        }
        
        return dp[len1][len2];
    }
    
    @Override
    public String getQueryString() {
        String modeStr = switch (mode) {
            case EXACT_MATCH -> "EXACT";
            case ALL_WORDS -> "ALL_WORDS";
            case ANY_WORD -> "ANY_WORD";
            case PHRASE -> "PHRASE";
            case WILDCARD -> "WILDCARD";
            case REGEX -> "REGEX";
            case FUZZY -> "FUZZY(d=" + fuzzyDistance + ")";
            case PREFIX -> "PREFIX";
            case SUFFIX -> "SUFFIX";
        };
        
        String caseStr = caseSensitive ? " CASE_SENSITIVE" : "";
        return modeStr + caseStr + ": \"" + searchText + "\"";
    }
    
    @Override
    public double getSelectivity() {
        return switch (mode) {
            case EXACT_MATCH -> 0.01;
            case PHRASE -> 0.05;
            case ALL_WORDS -> Math.max(0.1, 1.0 / (searchText.split("\\s+").length * 2));
            case ANY_WORD -> Math.min(0.8, searchText.split("\\s+").length * 0.2);
            case PREFIX, SUFFIX -> 0.15;
            case WILDCARD -> 0.3;
            case REGEX -> 0.2;
            case FUZZY -> 0.4;
        };
    }
    
    @Override
    public int getPriority() {
        return switch (mode) {
            case EXACT_MATCH -> 10;
            case PHRASE -> 9;
            case ALL_WORDS -> 8;
            case PREFIX, SUFFIX -> 7;
            case ANY_WORD -> 6;
            case WILDCARD -> 5;
            case REGEX -> 4;
            case FUZZY -> 3;
        };
    }
}