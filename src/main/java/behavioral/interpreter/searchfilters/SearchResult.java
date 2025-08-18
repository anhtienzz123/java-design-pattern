package behavioral.interpreter.searchfilters;

import java.util.List;
import java.util.Map;

/**
 * Represents the result of a search operation.
 * Contains matched items, statistics, and metadata about the search execution.
 */
public record SearchResult(
    List<SearchableItem> items,
    long totalMatched,
    long totalEvaluated,
    long executionTimeMs,
    String query,
    Map<String, Object> statistics,
    List<String> appliedFilters,
    boolean hasMore,
    String nextPageToken
) {
    
    /**
     * Creates a simple search result with just items and query.
     */
    public static SearchResult of(List<SearchableItem> items, String query) {
        return new SearchResult(
            items,
            items.size(),
            items.size(),
            0,
            query,
            Map.of(),
            List.of(),
            false,
            null
        );
    }
    
    /**
     * Creates a search result with execution statistics.
     */
    public static SearchResult withStats(List<SearchableItem> items, String query, 
                                       long totalEvaluated, long executionTimeMs) {
        return new SearchResult(
            items,
            items.size(),
            totalEvaluated,
            executionTimeMs,
            query,
            Map.of("efficiency", (double) items.size() / totalEvaluated),
            List.of(),
            false,
            null
        );
    }
    
    /**
     * Gets the number of matched items returned.
     */
    public int size() {
        return items.size();
    }
    
    /**
     * Checks if the search returned any results.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Gets the search efficiency (matched/evaluated ratio).
     */
    public double getEfficiency() {
        return totalEvaluated > 0 ? (double) totalMatched / totalEvaluated : 0.0;
    }
    
    /**
     * Gets the average execution time per evaluated item in microseconds.
     */
    public double getAvgTimePerItem() {
        return totalEvaluated > 0 ? (double) executionTimeMs * 1000 / totalEvaluated : 0.0;
    }
    
    /**
     * Prints a summary of the search results.
     */
    public void printSummary() {
        System.out.printf("Search Results: %d matched out of %d evaluated (%.1f%% efficiency)%n",
            totalMatched, totalEvaluated, getEfficiency() * 100);
        System.out.printf("Execution time: %d ms (%.2f μs per item)%n",
            executionTimeMs, getAvgTimePerItem());
        System.out.printf("Query: %s%n", query);
        
        if (!appliedFilters.isEmpty()) {
            System.out.println("Applied filters: " + String.join(", ", appliedFilters));
        }
        
        if (!statistics.isEmpty()) {
            System.out.println("Statistics: " + statistics);
        }
        
        System.out.println();
    }
}