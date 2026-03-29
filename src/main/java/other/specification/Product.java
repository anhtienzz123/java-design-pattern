package other.specification;

import java.math.BigDecimal;

/**
 * Product domain model for demonstrating the Specification pattern.
 */
public record Product(
        String id,
        String name,
        Category category,
        BigDecimal price,
        int stockQuantity,
        double rating,
        boolean featured
) {
    public enum Category {
        ELECTRONICS,
        CLOTHING,
        BOOKS,
        HOME_GARDEN,
        SPORTS,
        TOYS
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public static Product of(String id, String name, Category category,
                             double price, int stock, double rating, boolean featured) {
        return new Product(id, name, category, BigDecimal.valueOf(price), stock, rating, featured);
    }

    @Override
    public String toString() {
        return "%-12s | %-25s | %-12s | $%7.2f | Stock: %3d | Rating: %.1f | %s".formatted(
                id, name, category, price, stockQuantity, rating, featured ? "FEATURED" : ""
        );
    }
}
