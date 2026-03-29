package other.specification;

import java.math.BigDecimal;

/**
 * Factory class providing common Product specifications.
 *
 * This class contains static factory methods that create reusable,
 * composable specifications for filtering products.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
        // Utility class - prevent instantiation
    }

    // ==================== PRICE SPECIFICATIONS ====================

    /**
     * Products with price less than or equal to the maximum.
     */
    public static Specification<Product> priceLessThanOrEqual(BigDecimal maxPrice) {
        return product -> product.price().compareTo(maxPrice) <= 0;
    }

    public static Specification<Product> priceLessThanOrEqual(double maxPrice) {
        return priceLessThanOrEqual(BigDecimal.valueOf(maxPrice));
    }

    /**
     * Products with price greater than or equal to the minimum.
     */
    public static Specification<Product> priceGreaterThanOrEqual(BigDecimal minPrice) {
        return product -> product.price().compareTo(minPrice) >= 0;
    }

    public static Specification<Product> priceGreaterThanOrEqual(double minPrice) {
        return priceGreaterThanOrEqual(BigDecimal.valueOf(minPrice));
    }

    /**
     * Products with price within the specified range (inclusive).
     */
    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return priceGreaterThanOrEqual(min).and(priceLessThanOrEqual(max));
    }

    public static Specification<Product> priceBetween(double min, double max) {
        return priceBetween(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
    }

    // ==================== CATEGORY SPECIFICATIONS ====================

    /**
     * Products belonging to the specified category.
     */
    public static Specification<Product> inCategory(Product.Category category) {
        return product -> product.category() == category;
    }

    /**
     * Products belonging to any of the specified categories.
     */
    public static Specification<Product> inCategories(Product.Category... categories) {
        Specification<Product> result = product -> false;
        for (Product.Category category : categories) {
            result = result.or(inCategory(category));
        }
        return result;
    }

    // ==================== STOCK SPECIFICATIONS ====================

    /**
     * Products that are in stock (quantity > 0).
     */
    public static Specification<Product> inStock() {
        return Product::isInStock;
    }

    /**
     * Products that are out of stock.
     */
    public static Specification<Product> outOfStock() {
        return inStock().not();
    }

    /**
     * Products with at least the specified quantity in stock.
     */
    public static Specification<Product> stockAtLeast(int minQuantity) {
        return product -> product.stockQuantity() >= minQuantity;
    }

    /**
     * Products with low stock (less than threshold).
     */
    public static Specification<Product> lowStock(int threshold) {
        return product -> product.stockQuantity() > 0 && product.stockQuantity() < threshold;
    }

    // ==================== RATING SPECIFICATIONS ====================

    /**
     * Products with rating greater than or equal to the minimum.
     */
    public static Specification<Product> ratingAtLeast(double minRating) {
        return product -> product.rating() >= minRating;
    }

    /**
     * Highly rated products (4.0 or above).
     */
    public static Specification<Product> highlyRated() {
        return ratingAtLeast(4.0);
    }

    // ==================== FEATURED SPECIFICATIONS ====================

    /**
     * Products that are featured.
     */
    public static Specification<Product> featured() {
        return Product::featured;
    }

    /**
     * Products that are not featured.
     */
    public static Specification<Product> notFeatured() {
        return featured().not();
    }

    // ==================== NAME SPECIFICATIONS ====================

    /**
     * Products whose name contains the search term (case-insensitive).
     */
    public static Specification<Product> nameContains(String searchTerm) {
        String lowerSearch = searchTerm.toLowerCase();
        return product -> product.name().toLowerCase().contains(lowerSearch);
    }

    // ==================== COMPOSITE SPECIFICATIONS ====================

    /**
     * Products that are good deals: in stock, reasonably priced, and highly rated.
     */
    public static Specification<Product> goodDeal(double maxPrice) {
        return inStock()
                .and(priceLessThanOrEqual(maxPrice))
                .and(highlyRated());
    }

    /**
     * Products eligible for promotion: featured OR (highly rated AND in stock).
     */
    public static Specification<Product> eligibleForPromotion() {
        return featured().or(highlyRated().and(inStock()));
    }

    /**
     * Specification that matches all products.
     */
    public static Specification<Product> all() {
        return product -> true;
    }

    /**
     * Specification that matches no products.
     */
    public static Specification<Product> none() {
        return product -> false;
    }
}
