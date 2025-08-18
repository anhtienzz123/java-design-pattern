package behavioral.interpreter.searchfilters;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * High-performance search engine that uses the Interpreter pattern to evaluate
 * complex search queries against collections of searchable items.
 */
public class SearchEngine {
    private final SearchQueryParser parser;
    private final Map<String, List<SearchableItem>> indices;
    private final boolean enableParallelSearch;
    private final int maxResults;
    private final Map<String, SearchExpression> cachedQueries;
    
    public SearchEngine() {
        this(true, 1000);
    }
    
    public SearchEngine(boolean enableParallelSearch, int maxResults) {
        this.parser = new SearchQueryParser();
        this.indices = new ConcurrentHashMap<>();
        this.enableParallelSearch = enableParallelSearch;
        this.maxResults = maxResults;
        this.cachedQueries = new ConcurrentHashMap<>();
    }
    
    /**
     * Indexes a collection of searchable items for faster searching.
     */
    public void indexItems(String indexName, Collection<SearchableItem> items) {
        indices.put(indexName, new ArrayList<>(items));
    }
    
    /**
     * Searches for items using a string query.
     */
    public SearchResult search(String indexName, String query) {
        SearchExpression expression = cachedQueries.computeIfAbsent(query, parser::parse);
        return search(indexName, expression);
    }
    
    /**
     * Searches for items using a pre-built search expression.
     */
    public SearchResult search(String indexName, SearchExpression expression) {
        List<SearchableItem> items = indices.get(indexName);
        if (items == null || items.isEmpty()) {
            return SearchResult.of(List.of(), expression.getQueryString());
        }
        
        long startTime = System.currentTimeMillis();
        
        // Optimize the expression if it's a QueryExpression
        if (expression instanceof QueryExpression queryExpr && !queryExpr.isOptimized()) {
            expression = queryExpr.optimize();
        }
        
        List<SearchableItem> results;
        if (enableParallelSearch && items.size() > 1000) {
            results = parallelSearch(items, expression);
        } else {
            results = sequentialSearch(items, expression);
        }
        
        // Limit results
        if (results.size() > maxResults) {
            results = results.subList(0, maxResults);
        }
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        return SearchResult.withStats(results, expression.getQueryString(), items.size(), executionTime);
    }
    
    /**
     * Searches multiple indices simultaneously.
     */
    public Map<String, SearchResult> searchAll(String query) {
        SearchExpression expression = cachedQueries.computeIfAbsent(query, parser::parse);
        return searchAll(expression);
    }
    
    /**
     * Searches multiple indices simultaneously with a pre-built expression.
     */
    public Map<String, SearchResult> searchAll(SearchExpression expression) {
        if (enableParallelSearch) {
            Map<String, CompletableFuture<SearchResult>> futures = indices.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> CompletableFuture.supplyAsync(() -> search(entry.getKey(), expression))
                ));
            
            return futures.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().join()
                ));
        } else {
            return indices.keySet().stream()
                .collect(Collectors.toMap(
                    indexName -> indexName,
                    indexName -> search(indexName, expression)
                ));
        }
    }
    
    /**
     * Performs faceted search with aggregations.
     */
    public FacetedSearchResult facetedSearch(String indexName, SearchExpression expression, 
                                           List<String> facetFields) {
        SearchResult baseResult = search(indexName, expression);
        Map<String, Map<String, Long>> facets = new HashMap<>();
        
        for (String facetField : facetFields) {
            Map<String, Long> facetCounts = baseResult.items().stream()
                .map(item -> item.getStringField(facetField))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                    value -> value,
                    Collectors.counting()
                ));
            facets.put(facetField, facetCounts);
        }
        
        return new FacetedSearchResult(baseResult, facets);
    }
    
    /**
     * Suggests query completions based on indexed content.
     */
    public List<String> suggestQueries(String indexName, String partialQuery, int maxSuggestions) {
        List<SearchableItem> items = indices.get(indexName);
        if (items == null || partialQuery.length() < 2) {
            return List.of();
        }
        
        String lowerPartial = partialQuery.toLowerCase();
        Set<String> suggestions = new HashSet<>();
        
        for (SearchableItem item : items) {
            String searchableText = item.getSearchableText();
            if (searchableText != null) {
                String[] words = searchableText.toLowerCase().split("\\s+");
                for (String word : words) {
                    if (word.startsWith(lowerPartial) && word.length() > lowerPartial.length()) {
                        suggestions.add(word);
                        if (suggestions.size() >= maxSuggestions) {
                            break;
                        }
                    }
                }
            }
            if (suggestions.size() >= maxSuggestions) {
                break;
            }
        }
        
        return suggestions.stream()
                        .sorted()
                        .limit(maxSuggestions)
                        .collect(Collectors.toList());
    }
    
    /**
     * Gets search statistics for an index.
     */
    public SearchIndexStats getIndexStats(String indexName) {
        List<SearchableItem> items = indices.get(indexName);
        if (items == null) {
            return new SearchIndexStats(indexName, 0, 0, Set.of(), Set.of());
        }
        
        Set<String> fieldNames = items.stream()
            .flatMap(item -> item.getFieldNames().stream())
            .collect(Collectors.toSet());
        
        Set<String> types = items.stream()
            .map(SearchableItem::getType)
            .collect(Collectors.toSet());
        
        long totalTextLength = items.stream()
            .mapToLong(item -> {
                String text = item.getSearchableText();
                return text != null ? text.length() : 0;
            })
            .sum();
        
        return new SearchIndexStats(indexName, items.size(), totalTextLength, fieldNames, types);
    }
    
    /**
     * Clears the query cache.
     */
    public void clearQueryCache() {
        cachedQueries.clear();
    }
    
    /**
     * Gets the available index names.
     */
    public Set<String> getIndexNames() {
        return indices.keySet();
    }
    
    /**
     * Removes an index.
     */
    public boolean removeIndex(String indexName) {
        return indices.remove(indexName) != null;
    }
    
    private List<SearchableItem> sequentialSearch(List<SearchableItem> items, SearchExpression expression) {
        return items.stream()
                   .filter(expression::matches)
                   .collect(Collectors.toList());
    }
    
    private List<SearchableItem> parallelSearch(List<SearchableItem> items, SearchExpression expression) {
        return items.parallelStream()
                   .filter(expression::matches)
                   .collect(Collectors.toList());
    }
    
    /**
     * Represents faceted search results with aggregations.
     */
    public static class FacetedSearchResult {
        private final SearchResult searchResult;
        private final Map<String, Map<String, Long>> facets;
        
        public FacetedSearchResult(SearchResult searchResult, Map<String, Map<String, Long>> facets) {
            this.searchResult = searchResult;
            this.facets = facets;
        }
        
        public SearchResult getSearchResult() {
            return searchResult;
        }
        
        public Map<String, Map<String, Long>> getFacets() {
            return facets;
        }
        
        public Map<String, Long> getFacet(String fieldName) {
            return facets.get(fieldName);
        }
        
        public void printFacets() {
            System.out.println("=== Faceted Search Results ===");
            searchResult.printSummary();
            
            System.out.println("Facets:");
            for (Map.Entry<String, Map<String, Long>> facetEntry : facets.entrySet()) {
                System.out.println("  " + facetEntry.getKey() + ":");
                facetEntry.getValue().entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> System.out.printf("    %s: %d%n", entry.getKey(), entry.getValue()));
            }
            System.out.println();
        }
    }
    
    /**
     * Statistics about a search index.
     */
    public record SearchIndexStats(
        String indexName,
        int itemCount,
        long totalTextLength,
        Set<String> fieldNames,
        Set<String> itemTypes
    ) {
        
        public double avgTextLength() {
            return itemCount > 0 ? (double) totalTextLength / itemCount : 0.0;
        }
        
        public void printStats() {
            System.out.printf("Index: %s%n", indexName);
            System.out.printf("  Items: %d%n", itemCount);
            System.out.printf("  Total text length: %d%n", totalTextLength);
            System.out.printf("  Average text length: %.1f%n", avgTextLength());
            System.out.printf("  Field names: %s%n", fieldNames);
            System.out.printf("  Item types: %s%n", itemTypes);
            System.out.println();
        }
    }
}