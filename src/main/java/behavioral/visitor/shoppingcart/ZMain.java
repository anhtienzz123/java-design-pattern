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
        
//        == Output:
//        Shopping Cart - Visitor Pattern Demo
//        ====================================
//
//        Adding Books...
//        Added to cart: Effective Java (Quantity: 1)
//        Added to cart: The Great Gatsby (Quantity: 2)
//
//        Adding Electronics...
//        Added to cart: MacBook Pro (Quantity: 1)
//        Added to cart: iPhone 15 (Quantity: 1)
//
//        Adding Clothing...
//        Added to cart: Winter Coat (Quantity: 1)
//        Added to cart: Summer T-Shirt (Quantity: 3)
//
//        Adding Groceries...
//        Added to cart: Organic Milk (Quantity: 2)
//        Added to cart: Whole Wheat Bread (Quantity: 1)
//
//        === Processing Cart Items ===
//        Book: Effective Java (Educational) - Quantity: 1, Base: $45.99, Discount: $6.90, Final: $39.09
//        Book: The Great Gatsby - Quantity: 2, Price: $25.98
//        Electronics: MacBook Pro (laptop) - Quantity: 1, Base: $1299.99, Tax: $104.00, Extended Warranty Discount: $70.20, Final: $1333.79
//        Electronics: iPhone 15 (phone) - Quantity: 1, Base: $799.99, Tax: $64.00, Final: $863.99
//        Clothing: Winter Coat (North Face, L) - Quantity: 1, Base: $89.99, Out-of-Season Discount: $18.00, Final: $71.99
//        Clothing: Summer T-Shirt (Nike, M) - Quantity: 3, Price: $59.97
//        Grocery: Organic Milk (dairy) - Quantity: 2, Price: $9.98
//        Grocery: Whole Wheat Bread (bakery) - Quantity: 1, Base: $3.49, Near-Expiry Discount: $1.05, Final: $2.44
//
//        === Shopping Cart Summary ===
//        Total Discount: $96.14
//        Total Price: $2407.24
//        =============================
//
//        Product Information Summary:
//
//        === Processing Cart Items ===
//        Book: Effective Java (ISBN: 978-0134685991) [Educational]
//        Book: The Great Gatsby (ISBN: 978-0743273565) 
//        Electronics: MacBook Pro - laptop (36 months warranty)
//        Electronics: iPhone 15 - phone (12 months warranty)
//        Clothing: Winter Coat - North Face (Size: L, Season: winter)
//        Clothing: Summer T-Shirt - Nike (Size: M, Season: summer)
//        Grocery: Organic Milk - dairy (Expires: 2025-06-17) [Perishable]
//        Grocery: Whole Wheat Bread - bakery (Expires: 2025-06-14) [Perishable]
//
//        === Cart Statistics ===
//        Books: 3 items
//        Electronics: 2 items
//        Clothing: 4 items
//        Groceries: 3 items
//        Total Items: 12
//        =======================
    }
} 