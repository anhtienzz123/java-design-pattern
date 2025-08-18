package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Demonstration of the Interpreter pattern for Search Filters.
 * 
 * This example shows how to use the Interpreter pattern to create a comprehensive
 * search system with complex filtering capabilities, query parsing, and optimization.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Search Filters using Interpreter Pattern ===\n");
        
        // Create and populate search engine
        SearchEngine searchEngine = new SearchEngine(true, 100);
        populateDocuments(searchEngine);
        
        // Show index statistics
        showIndexStatistics(searchEngine);
        
        // Demonstrate various search capabilities
        demonstrateBasicSearches(searchEngine);
        demonstrateComplexQueries(searchEngine);
        demonstrateQueryBuilder(searchEngine);
        demonstrateTextSearch(searchEngine);
        demonstrateRangeAndTagSearch(searchEngine);
        demonstrateFacetedSearch(searchEngine);
        demonstrateQueryOptimization(searchEngine);
        demonstrateMultiIndexSearch(searchEngine);
        demonstrateQuerySuggestions(searchEngine);
    }
    
    private static void populateDocuments(SearchEngine searchEngine) {
        System.out.println("=== Populating Document Index ===");
        
        List<SearchableItem> documents = Arrays.asList(
            DocumentItem.builder()
                .id("1")
                .title("Introduction to Machine Learning")
                .content("Machine learning is a subset of artificial intelligence that focuses on algorithms that can learn from data.")
                .author("Dr. Alice Smith")
                .category("Technology")
                .tags("AI", "ML", "Technology", "Education")
                .keywords("machine", "learning", "artificial", "intelligence", "algorithms")
                .pageCount(250)
                .createdDate(LocalDateTime.of(2024, 1, 15, 10, 0))
                .modifiedDate(LocalDateTime.of(2024, 1, 20, 14, 30))
                .metadata("difficulty", "intermediate")
                .metadata("price", 49.99)
                .score(4.5)
                .build(),
                
            DocumentItem.builder()
                .id("2")
                .title("Advanced Java Programming")
                .content("Deep dive into advanced Java concepts including concurrency, JVM internals, and performance optimization.")
                .author("John Doe")
                .category("Programming")
                .tags("Java", "Programming", "Advanced", "Concurrency")
                .keywords("java", "programming", "concurrency", "jvm", "performance")
                .pageCount(400)
                .createdDate(LocalDateTime.of(2024, 2, 1, 9, 0))
                .modifiedDate(LocalDateTime.of(2024, 2, 5, 16, 45))
                .metadata("difficulty", "advanced")
                .metadata("price", 59.99)
                .score(4.8)
                .build(),
                
            DocumentItem.builder()
                .id("3")
                .title("Data Science Fundamentals")
                .content("Learn the basics of data science including statistics, data visualization, and data analysis techniques.")
                .author("Dr. Alice Smith")
                .category("Data Science")
                .tags("Data Science", "Statistics", "Visualization", "Analysis")
                .keywords("data", "science", "statistics", "visualization", "analysis")
                .pageCount(300)
                .createdDate(LocalDateTime.of(2024, 1, 10, 8, 30))
                .modifiedDate(LocalDateTime.of(2024, 1, 15, 12, 0))
                .metadata("difficulty", "beginner")
                .metadata("price", 39.99)
                .score(4.2)
                .build(),
                
            DocumentItem.builder()
                .id("4")
                .title("Web Development with React")
                .content("Modern web development using React, including hooks, state management, and component architecture.")
                .author("Jane Wilson")
                .category("Web Development")
                .tags("React", "JavaScript", "Web", "Frontend")
                .keywords("react", "web", "development", "javascript", "frontend")
                .pageCount(350)
                .createdDate(LocalDateTime.of(2024, 1, 25, 14, 0))
                .modifiedDate(LocalDateTime.of(2024, 2, 2, 10, 15))
                .metadata("difficulty", "intermediate")
                .metadata("price", 44.99)
                .score(4.6)
                .build(),
                
            DocumentItem.builder()
                .id("5")
                .title("Database Design Principles")
                .content("Comprehensive guide to database design, normalization, indexing, and query optimization.")
                .author("Mike Johnson")
                .category("Database")
                .tags("Database", "SQL", "Design", "Optimization")
                .keywords("database", "design", "sql", "normalization", "indexing")
                .pageCount(280)
                .createdDate(LocalDateTime.of(2024, 2, 10, 11, 30))
                .modifiedDate(LocalDateTime.of(2024, 2, 12, 15, 20))
                .metadata("difficulty", "intermediate")
                .metadata("price", 54.99)
                .score(4.4)
                .build(),
                
            DocumentItem.builder()
                .id("6")
                .title("Python for Beginners")
                .content("Learn Python programming from scratch with practical examples and exercises.")
                .author("Sarah Brown")
                .category("Programming")
                .tags("Python", "Programming", "Beginner", "Tutorial")
                .keywords("python", "programming", "beginner", "tutorial", "examples")
                .pageCount(200)
                .createdDate(LocalDateTime.of(2024, 1, 5, 9, 15))
                .modifiedDate(LocalDateTime.of(2024, 1, 8, 13, 45))
                .metadata("difficulty", "beginner")
                .metadata("price", 29.99)
                .score(4.3)
                .build(),
                
            DocumentItem.builder()
                .id("7")
                .title("Cloud Computing Architecture")
                .content("Design and implement scalable cloud solutions using AWS, Azure, and Google Cloud Platform.")
                .author("David Lee")
                .category("Cloud Computing")
                .tags("Cloud", "AWS", "Azure", "Architecture", "Scalability")
                .keywords("cloud", "computing", "aws", "azure", "architecture")
                .pageCount(450)
                .createdDate(LocalDateTime.of(2024, 2, 15, 10, 45))
                .modifiedDate(LocalDateTime.of(2024, 2, 18, 14, 0))
                .metadata("difficulty", "advanced")
                .metadata("price", 69.99)
                .score(4.7)
                .build()
        );
        
        searchEngine.indexItems("documents", documents);
        System.out.printf("Indexed %d documents%n%n", documents.size());
    }
    
    private static void showIndexStatistics(SearchEngine searchEngine) {
        System.out.println("=== Index Statistics ===");
        searchEngine.getIndexStats("documents").printStats();
    }
    
    private static void demonstrateBasicSearches(SearchEngine searchEngine) {
        System.out.println("=== Basic Search Demonstrations ===");
        
        // Simple field equality
        performSearch(searchEngine, "author = \"Dr. Alice Smith\"", "Find documents by specific author");
        
        // Category search
        performSearch(searchEngine, "category = Programming", "Find programming documents");
        
        // Page count comparison
        performSearch(searchEngine, "pageCount > 300", "Find documents with more than 300 pages");
        
        // Boolean field search
        performSearch(searchEngine, "published = true", "Find published documents");
    }
    
    private static void demonstrateComplexQueries(SearchEngine searchEngine) {
        System.out.println("=== Complex Query Demonstrations ===");
        
        // Multiple conditions with AND
        performSearch(searchEngine, 
            "category = Technology AND pageCount > 200", 
            "Technology documents with more than 200 pages");
        
        // Multiple conditions with OR
        performSearch(searchEngine, 
            "author = \"Dr. Alice Smith\" OR author = \"John Doe\"", 
            "Documents by Dr. Alice Smith or John Doe");
        
        // Complex combination with parentheses
        performSearch(searchEngine, 
            "(category = Programming OR category = \"Data Science\") AND pageCount < 350", 
            "Programming or Data Science documents under 350 pages");
        
        // NOT operator
        performSearch(searchEngine, 
            "category != Programming AND NOT author = \"Sarah Brown\"", 
            "Non-programming documents not by Sarah Brown");
    }
    
    private static void demonstrateQueryBuilder(SearchEngine searchEngine) {
        System.out.println("=== Query Builder Demonstrations ===");
        
        // Using QueryExpression builder
        QueryExpression query1 = QueryExpression.builder()
            .name("Advanced Tech Books")
            .field("category", FieldExpression.ComparisonOperator.EQUALS, "Technology")
            .field("score", FieldExpression.ComparisonOperator.GREATER_EQUAL, 4.0)
            .range("pageCount", 200, 500, RangeExpression.RangeType.NUMERIC)
            .buildOptimized();
        
        SearchResult result1 = searchEngine.search("documents", query1);
        System.out.println("Query Builder Result 1:");
        result1.printSummary();
        printDocuments(result1.items(), 3);
        
        // Complex builder example
        QueryExpression query2 = QueryExpression.builder()
            .name("Intermediate or Advanced Programming")
            .or(
                AndExpression.of(
                    new FieldExpression("category", FieldExpression.ComparisonOperator.EQUALS, "Programming"),
                    new FieldExpression("difficulty", FieldExpression.ComparisonOperator.EQUALS, "intermediate")
                ),
                AndExpression.of(
                    new FieldExpression("category", FieldExpression.ComparisonOperator.EQUALS, "Programming"),
                    new FieldExpression("difficulty", FieldExpression.ComparisonOperator.EQUALS, "advanced")
                )
            )
            .buildOptimized();
        
        SearchResult result2 = searchEngine.search("documents", query2);
        System.out.println("Query Builder Result 2:");
        result2.printSummary();
        printDocuments(result2.items(), 3);
    }
    
    private static void demonstrateTextSearch(SearchEngine searchEngine) {
        System.out.println("=== Text Search Demonstrations ===");
        
        // Simple text search
        performSearch(searchEngine, 
            "TEXT(\"machine learning\")", 
            "Phrase search for 'machine learning'");
        
        // Text search with mode
        performSearch(searchEngine, 
            "TEXT(\"programming java\", ANY_WORD)", 
            "Any word search for 'programming java'");
        
        // Fuzzy text search
        performSearch(searchEngine, 
            "TEXT(\"programing\", FUZZY)", 
            "Fuzzy search for 'programing' (with typo)");
        
        // Complex text + field search
        performSearch(searchEngine, 
            "TEXT(\"data science\") AND score >= 4.0", 
            "Data science content with high score");
    }
    
    private static void demonstrateRangeAndTagSearch(SearchEngine searchEngine) {
        System.out.println("=== Range and Tag Search Demonstrations ===");
        
        // Date range search
        performSearch(searchEngine, 
            "createdDate BETWEEN 2024-01-01T00:00:00 AND 2024-01-31T23:59:59", 
            "Documents created in January 2024");
        
        // Price range search
        performSearch(searchEngine, 
            "price BETWEEN 40 AND 60", 
            "Documents priced between $40 and $60");
        
        // Tag searches
        performSearch(searchEngine, 
            "TAGS HAS_ANY [Programming, Technology]", 
            "Documents with Programming OR Technology tags");
        
        performSearch(searchEngine, 
            "TAGS HAS_ALL [Programming, Advanced]", 
            "Documents with both Programming AND Advanced tags");
        
        // Complex combination
        performSearch(searchEngine, 
            "TAGS HAS_ANY [AI, ML, Programming] AND pageCount > 250", 
            "AI/ML/Programming documents over 250 pages");
    }
    
    private static void demonstrateFacetedSearch(SearchEngine searchEngine) {
        System.out.println("=== Faceted Search Demonstrations ===");
        
        // Faceted search on category and author
        SearchExpression expression = new FieldExpression("score", FieldExpression.ComparisonOperator.GREATER_EQUAL, 4.0);
        SearchEngine.FacetedSearchResult facetedResult = searchEngine.facetedSearch(
            "documents", 
            expression, 
            Arrays.asList("category", "author", "difficulty")
        );
        
        facetedResult.printFacets();
    }
    
    private static void demonstrateQueryOptimization(SearchEngine searchEngine) {
        System.out.println("=== Query Optimization Demonstrations ===");
        
        // Create an unoptimized complex query
        SearchExpression complexQuery = AndExpression.of(
            new TextSearchExpression("programming", TextSearchExpression.TextSearchMode.FUZZY),
            new FieldExpression("score", FieldExpression.ComparisonOperator.GREATER_THAN, 4.0),
            new FieldExpression("category", FieldExpression.ComparisonOperator.EQUALS, "Programming"),
            new RangeExpression("pageCount", 200, 500, true, true, RangeExpression.RangeType.NUMERIC)
        );
        
        QueryExpression unoptimized = new QueryExpression(complexQuery, "Unoptimized", false);
        QueryExpression optimized = unoptimized.optimize();
        
        System.out.println("Unoptimized query: " + unoptimized.getQueryString());
        System.out.println("Optimized query: " + optimized.getQueryString());
        
        // Compare performance (simplified demonstration)
        long start1 = System.nanoTime();
        SearchResult result1 = searchEngine.search("documents", unoptimized);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        SearchResult result2 = searchEngine.search("documents", optimized);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("Unoptimized execution time: %d ns%n", time1);
        System.out.printf("Optimized execution time: %d ns%n", time2);
        System.out.printf("Results match: %s%n%n", result1.items().equals(result2.items()));
    }
    
    private static void demonstrateMultiIndexSearch(SearchEngine searchEngine) {
        System.out.println("=== Multi-Index Search Demonstrations ===");
        
        // Create a second index with different documents
        List<SearchableItem> articles = Arrays.asList(
            DocumentItem.builder()
                .id("a1")
                .title("Understanding Design Patterns")
                .content("Design patterns are reusable solutions to common software design problems.")
                .author("Robert Martin")
                .category("Software Engineering")
                .tags("Design Patterns", "Software", "Architecture")
                .pageCount(15)
                .metadata("type", "article")
                .score(4.1)
                .build(),
                
            DocumentItem.builder()
                .id("a2")
                .title("Introduction to DevOps")
                .content("DevOps practices for continuous integration and deployment.")
                .author("Lisa Chen")
                .category("DevOps")
                .tags("DevOps", "CI/CD", "Automation")
                .pageCount(12)
                .metadata("type", "article")
                .score(4.3)
                .build()
        );
        
        searchEngine.indexItems("articles", articles);
        
        // Search across all indices
        Map<String, SearchResult> allResults = searchEngine.searchAll("category CONTAINS \"Engineering\" OR category = DevOps");
        
        System.out.println("Multi-index search results:");
        for (Map.Entry<String, SearchResult> entry : allResults.entrySet()) {
            System.out.printf("Index '%s':%n", entry.getKey());
            entry.getValue().printSummary();
            printDocuments(entry.getValue().items(), 2);
        }
    }
    
    private static void demonstrateQuerySuggestions(SearchEngine searchEngine) {
        System.out.println("=== Query Suggestion Demonstrations ===");
        
        String[] partialQueries = {"prog", "mach", "data", "java"};
        
        for (String partial : partialQueries) {
            List<String> suggestions = searchEngine.suggestQueries("documents", partial, 5);
            System.out.printf("Suggestions for '%s': %s%n", partial, suggestions);
        }
        System.out.println();
    }
    
    private static void performSearch(SearchEngine searchEngine, String query, String description) {
        System.out.println(description + ":");
        System.out.println("Query: " + query);
        
        try {
            SearchResult result = searchEngine.search("documents", query);
            result.printSummary();
            printDocuments(result.items(), 3);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
        }
    }
    
    private static void printDocuments(List<SearchableItem> documents, int maxCount) {
        int count = Math.min(documents.size(), maxCount);
        for (int i = 0; i < count; i++) {
            SearchableItem doc = documents.get(i);
            System.out.printf("  %s - %s (by %s, Score: %.1f)%n",
                doc.getId(),
                doc.getStringField("title"),
                doc.getStringField("author"),
                doc.getScore());
        }
        if (documents.size() > maxCount) {
            System.out.printf("  ... and %d more%n", documents.size() - maxCount);
        }
        System.out.println();
    }
}