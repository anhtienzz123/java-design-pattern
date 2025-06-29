package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;

/**
 * Element interface that defines the accept method for visitors.
 * All product types must implement this interface.
 */
public interface Product {
    void accept(ProductVisitor visitor);
    String getName();
    BigDecimal getBasePrice();
    int getQuantity();
} 