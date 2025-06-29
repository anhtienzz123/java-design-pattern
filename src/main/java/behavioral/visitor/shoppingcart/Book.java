package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;

/**
 * Concrete product representing a book with specific properties like ISBN and discount eligibility.
 */
public class Book implements Product {
    private final String name;
    private final BigDecimal basePrice;
    private final int quantity;
    private final String isbn;
    private final boolean isEducational;

    public Book(String name, BigDecimal basePrice, int quantity, String isbn, boolean isEducational) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
        this.isbn = isbn;
        this.isEducational = isEducational;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isEducational() {
        return isEducational;
    }
} 