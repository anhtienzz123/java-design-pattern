package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Concrete product representing grocery items with expiration and perishable properties.
 */
public class Grocery implements Product {
    private final String name;
    private final BigDecimal basePrice;
    private final int quantity;
    private final LocalDate expirationDate;
    private final boolean isPerishable;
    private final String category; // dairy, meat, vegetables, etc.

    public Grocery(String name, BigDecimal basePrice, int quantity, LocalDate expirationDate, 
                   boolean isPerishable, String category) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.isPerishable = isPerishable;
        this.category = category;
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public String getCategory() {
        return category;
    }
} 