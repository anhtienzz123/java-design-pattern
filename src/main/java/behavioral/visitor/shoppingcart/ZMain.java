package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Demonstration class showing the Visitor pattern implementation for
 * calculating total price of products in a shopping cart.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("Shopping Cart - Visitor Pattern Demo");
        System.out.println("====================================");

        // Create shopping cart
        ShoppingCart cart = new ShoppingCart();

        // Create different types of products
        System.out.println("\nAdding Books...");
        Book javaBook = new Book("Effective Java", new BigDecimal("45.99"), 1, 
                                "978-0134685991", true);
        Book novel = new Book("The Great Gatsby", new BigDecimal("12.99"), 2, 
                             "978-0743273565", false);
        
        cart.addProduct(javaBook);
        cart.addProduct(novel);

        System.out.println("\nAdding Electronics...");
        Electronics laptop = new Electronics("MacBook Pro", new BigDecimal("1299.99"), 1, 
                                            36, "laptop");
        Electronics phone = new Electronics("iPhone 15", new BigDecimal("799.99"), 1, 
                                           12, "phone");
        
        cart.addProduct(laptop);
        cart.addProduct(phone);

        System.out.println("\nAdding Clothing...");
        Clothing winterCoat = new Clothing("Winter Coat", new BigDecimal("89.99"), 1, 
                                          "L", "winter", "North Face");
        Clothing summerShirt = new Clothing("Summer T-Shirt", new BigDecimal("19.99"), 3, 
                                           "M", "summer", "Nike");
        
        cart.addProduct(winterCoat);
        cart.addProduct(summerShirt);

        System.out.println("\nAdding Groceries...");
        Grocery milk = new Grocery("Organic Milk", new BigDecimal("4.99"), 2, 
                                  LocalDate.now().plusDays(5), true, "dairy");
        Grocery bread = new Grocery("Whole Wheat Bread", new BigDecimal("3.49"), 1, 
                                   LocalDate.now().plusDays(2), true, "bakery");
        
        cart.addProduct(milk);
        cart.addProduct(bread);

        // Use visitor to calculate total price
        PriceCalculatorVisitor priceCalculator = new PriceCalculatorVisitor();
        cart.acceptVisitor(priceCalculator);
        
        // Print summary
        priceCalculator.printSummary();

        // Demonstrate extensibility: Create another visitor for different operation
        System.out.println("\nProduct Information Summary:");
        ProductInfoVisitor infoVisitor = new ProductInfoVisitor();
        cart.acceptVisitor(infoVisitor);
        infoVisitor.printSummary();
    }
} 