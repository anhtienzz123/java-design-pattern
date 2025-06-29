package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;

/**
 * Concrete product representing electronics with warranty and tax considerations.
 */
public class Electronics implements Product {
    private final String name;
    private final BigDecimal basePrice;
    private final int quantity;
    private final int warrantyMonths;
    private final String category; // phone, laptop, etc.

    public Electronics(String name, BigDecimal basePrice, int quantity, int warrantyMonths, String category) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
        this.warrantyMonths = warrantyMonths;
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

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public String getCategory() {
        return category;
    }
} 