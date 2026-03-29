package other.specification;

import java.util.List;

import static other.specification.Product.Category.*;
import static other.specification.ProductSpecifications.*;

/**
 * Demonstration of the Specification Pattern.
 *
 * The Specification pattern allows you to:
 * - Encapsulate business rules as reusable objects
 * - Combine rules using AND, OR, NOT logic
 * - Create complex queries from simple building blocks
 * - Keep domain logic separate from infrastructure
 *
 * Use Cases:
 * - Filtering/querying collections
 * - Validation rules
 * - Policy enforcement
 * - Search criteria
 */
public class ZMain {

    public static void main(String[] args) {
        System.out.println("=== Specification Pattern Demo ===\n");

        // Sample product catalog
        List<Product> products = createSampleProducts();

        System.out.println("--- All Products ---");
        products.forEach(System.out::println);

        // Example 1: Simple specification
        System.out.println("\n--- Example 1: Electronics Only ---");
        Specification<Product> electronics = inCategory(ELECTRONICS);
        electronics.filter(products).forEach(System.out::println);

        // Example 2: AND combination
        System.out.println("\n--- Example 2: Electronics AND In Stock ---");
        Specification<Product> availableElectronics = inCategory(ELECTRONICS).and(inStock());
        availableElectronics.filter(products).forEach(System.out::println);

        // Example 3: OR combination
        System.out.println("\n--- Example 3: Electronics OR Books ---");
        Specification<Product> electronicsOrBooks = inCategory(ELECTRONICS).or(inCategory(BOOKS));
        electronicsOrBooks.filter(products).forEach(System.out::println);

        // Example 4: NOT combination
        System.out.println("\n--- Example 4: NOT Clothing ---");
        Specification<Product> notClothing = inCategory(CLOTHING).not();
        notClothing.filter(products).forEach(System.out::println);

        // Example 5: Complex combination
        System.out.println("\n--- Example 5: In Stock AND (Price <= $100 OR Featured) ---");
        Specification<Product> complexSpec = inStock()
                .and(priceLessThanOrEqual(100).or(featured()));
        complexSpec.filter(products).forEach(System.out::println);

        // Example 6: Price range
        System.out.println("\n--- Example 6: Price between $20 and $100 ---");
        Specification<Product> priceRange = priceBetween(20, 100);
        priceRange.filter(products).forEach(System.out::println);

        // Example 7: Highly rated and in stock
        System.out.println("\n--- Example 7: Highly Rated (4.0+) AND In Stock ---");
        Specification<Product> topRated = highlyRated().and(inStock());
        topRated.filter(products).forEach(System.out::println);

        // Example 8: Good deals
        System.out.println("\n--- Example 8: Good Deals (In Stock + Under $50 + Rating 4.0+) ---");
        Specification<Product> deals = goodDeal(50);
        deals.filter(products).forEach(System.out::println);

        // Example 9: Low stock warning
        System.out.println("\n--- Example 9: Low Stock Warning (1-5 items) ---");
        Specification<Product> lowStockItems = lowStock(5);
        lowStockItems.filter(products).forEach(System.out::println);

        // Example 10: Search by name
        System.out.println("\n--- Example 10: Name Contains 'Pro' ---");
        Specification<Product> searchPro = nameContains("Pro");
        searchPro.filter(products).forEach(System.out::println);

        // Example 11: Multiple categories with other conditions
        System.out.println("\n--- Example 11: (Sports OR Toys) AND In Stock AND Under $75 ---");
        Specification<Product> familyFun = inCategories(SPORTS, TOYS)
                .and(inStock())
                .and(priceLessThanOrEqual(75));
        familyFun.filter(products).forEach(System.out::println);

        // Example 12: Using aggregate methods
        System.out.println("\n--- Example 12: Aggregate Methods ---");
        Specification<Product> affordableInStock = inStock().and(priceLessThanOrEqual(50));

        System.out.println("Total products: " + products.size());
        System.out.println("In stock count: " + inStock().count(products));
        System.out.println("Affordable & in stock count: " + affordableInStock.count(products));
        System.out.println("Any featured? " + featured().anyMatch(products));
        System.out.println("All in stock? " + inStock().allMatch(products));

        // Example 13: Reusable business rules
        System.out.println("\n--- Example 13: Eligible for Holiday Promotion ---");
        System.out.println("Rule: Featured OR (Rating >= 4.5 AND Price <= $200 AND In Stock)");
        Specification<Product> holidayPromo = featured()
                .or(ratingAtLeast(4.5)
                        .and(priceLessThanOrEqual(200))
                        .and(inStock()));
        holidayPromo.filter(products).forEach(System.out::println);

        System.out.println("\n=== Specification Pattern Benefits ===");
        System.out.println("""
                1. COMPOSABLE: Combine simple specs into complex ones using AND/OR/NOT
                2. REUSABLE: Define once, use everywhere
                3. READABLE: Business rules read like natural language
                4. TESTABLE: Each specification can be unit tested independently
                5. FLEXIBLE: Easy to add new specifications without changing existing code
                """);
    }

    private static List<Product> createSampleProducts() {
        return List.of(
                Product.of("ELEC-001", "Laptop Pro 15", ELECTRONICS, 999.99, 10, 4.5, true),
                Product.of("ELEC-002", "Wireless Mouse", ELECTRONICS, 29.99, 50, 4.2, false),
                Product.of("ELEC-003", "USB-C Hub", ELECTRONICS, 49.99, 0, 3.8, false),
                Product.of("ELEC-004", "Bluetooth Speaker", ELECTRONICS, 79.99, 3, 4.7, true),
                Product.of("CLTH-001", "Winter Jacket", CLOTHING, 149.99, 20, 4.1, false),
                Product.of("CLTH-002", "Running Shoes Pro", CLOTHING, 89.99, 15, 4.6, true),
                Product.of("CLTH-003", "Cotton T-Shirt", CLOTHING, 19.99, 100, 3.9, false),
                Product.of("BOOK-001", "Design Patterns", BOOKS, 44.99, 25, 4.8, false),
                Product.of("BOOK-002", "Clean Code", BOOKS, 39.99, 30, 4.9, true),
                Product.of("BOOK-003", "The Pragmatic Programmer", BOOKS, 49.99, 0, 4.7, false),
                Product.of("HOME-001", "Coffee Maker Pro", HOME_GARDEN, 129.99, 8, 4.3, false),
                Product.of("HOME-002", "Garden Tool Set", HOME_GARDEN, 34.99, 2, 4.0, false),
                Product.of("SPRT-001", "Yoga Mat", SPORTS, 24.99, 40, 4.4, false),
                Product.of("SPRT-002", "Dumbbells Set", SPORTS, 69.99, 12, 4.5, true),
                Product.of("TOYS-001", "LEGO Star Wars", TOYS, 59.99, 5, 4.8, true),
                Product.of("TOYS-002", "Board Game Classic", TOYS, 29.99, 18, 4.2, false)
        );
    }
}
