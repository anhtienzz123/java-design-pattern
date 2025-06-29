package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;

/**
 * Concrete product representing clothing with size and seasonal properties.
 */
public class Clothing implements Product {
    private final String name;
    private final BigDecimal basePrice;
    private final int quantity;
    private final String size;
    private final String season; // spring, summer, fall, winter
    private final String brand;

    public Clothing(String name, BigDecimal basePrice, int quantity, String size, String season, String brand) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
        this.size = size;
        this.season = season;
        this.brand = brand;
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

    public String getSize() {
        return size;
    }

    public String getSeason() {
        return season;
    }

    public String getBrand() {
        return brand;
    }
} 