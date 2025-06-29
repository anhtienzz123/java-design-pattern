package behavioral.visitor.shoppingcart;

import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart that contains products and can accept visitors to perform operations.
 * This demonstrates the context in which the visitor pattern is applied.
 */
public class ShoppingCart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        System.out.printf("Added to cart: %s (Quantity: %d)%n", product.getName(), product.getQuantity());
    }

    public void removeProduct(Product product) {
        products.remove(product);
        System.out.printf("Removed from cart: %s%n", product.getName());
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Return defensive copy
    }

    public void acceptVisitor(ProductVisitor visitor) {
        System.out.println("\n=== Processing Cart Items ===");
        for (Product product : products) {
            product.accept(visitor);
        }
    }

    public int getItemCount() {
        return products.size();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void clear() {
        products.clear();
        System.out.println("Shopping cart cleared.");
    }
} 