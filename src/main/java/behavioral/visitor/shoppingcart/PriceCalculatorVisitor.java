package behavioral.visitor.shoppingcart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Concrete visitor that calculates the total price of products in the cart.
 * Each product type has specific pricing rules and discounts.
 */
public class PriceCalculatorVisitor implements ProductVisitor {
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    @Override
    public void visit(Book book) {
        BigDecimal itemPrice = book.getBasePrice().multiply(BigDecimal.valueOf(book.getQuantity()));
        
        // Educational books get 15% discount
        if (book.isEducational()) {
            BigDecimal discount = itemPrice.multiply(BigDecimal.valueOf(0.15));
            totalDiscount = totalDiscount.add(discount);
            itemPrice = itemPrice.subtract(discount);
            System.out.printf("Book: %s (Educational) - Quantity: %d, Base: $%.2f, Discount: $%.2f, Final: $%.2f%n",
                    book.getName(), book.getQuantity(), book.getBasePrice().multiply(BigDecimal.valueOf(book.getQuantity())),
                    discount, itemPrice);
        } else {
            System.out.printf("Book: %s - Quantity: %d, Price: $%.2f%n",
                    book.getName(), book.getQuantity(), itemPrice);
        }
        
        totalPrice = totalPrice.add(itemPrice);
    }

    @Override
    public void visit(Electronics electronics) {
        BigDecimal itemPrice = electronics.getBasePrice().multiply(BigDecimal.valueOf(electronics.getQuantity()));
        
        // Electronics have 8% tax
        BigDecimal tax = itemPrice.multiply(BigDecimal.valueOf(0.08));
        itemPrice = itemPrice.add(tax);
        
        // Extended warranty discount for items over 24 months warranty
        if (electronics.getWarrantyMonths() > 24) {
            BigDecimal discount = itemPrice.multiply(BigDecimal.valueOf(0.05));
            totalDiscount = totalDiscount.add(discount);
            itemPrice = itemPrice.subtract(discount);
            System.out.printf("Electronics: %s (%s) - Quantity: %d, Base: $%.2f, Tax: $%.2f, Extended Warranty Discount: $%.2f, Final: $%.2f%n",
                    electronics.getName(), electronics.getCategory(), electronics.getQuantity(),
                    electronics.getBasePrice().multiply(BigDecimal.valueOf(electronics.getQuantity())), tax, discount, itemPrice);
        } else {
            System.out.printf("Electronics: %s (%s) - Quantity: %d, Base: $%.2f, Tax: $%.2f, Final: $%.2f%n",
                    electronics.getName(), electronics.getCategory(), electronics.getQuantity(),
                    electronics.getBasePrice().multiply(BigDecimal.valueOf(electronics.getQuantity())), tax, itemPrice);
        }
        
        totalPrice = totalPrice.add(itemPrice);
    }

    @Override
    public void visit(Clothing clothing) {
        BigDecimal itemPrice = clothing.getBasePrice().multiply(BigDecimal.valueOf(clothing.getQuantity()));
        
        // Seasonal discount for out-of-season items
        String currentSeason = getCurrentSeason();
        if (!clothing.getSeason().equalsIgnoreCase(currentSeason)) {
            BigDecimal discount = itemPrice.multiply(BigDecimal.valueOf(0.20));
            totalDiscount = totalDiscount.add(discount);
            itemPrice = itemPrice.subtract(discount);
            System.out.printf("Clothing: %s (%s, %s) - Quantity: %d, Base: $%.2f, Out-of-Season Discount: $%.2f, Final: $%.2f%n",
                    clothing.getName(), clothing.getBrand(), clothing.getSize(), clothing.getQuantity(),
                    clothing.getBasePrice().multiply(BigDecimal.valueOf(clothing.getQuantity())), discount, itemPrice);
        } else {
            System.out.printf("Clothing: %s (%s, %s) - Quantity: %d, Price: $%.2f%n",
                    clothing.getName(), clothing.getBrand(), clothing.getSize(), clothing.getQuantity(), itemPrice);
        }
        
        totalPrice = totalPrice.add(itemPrice);
    }

    @Override
    public void visit(Grocery grocery) {
        BigDecimal itemPrice = grocery.getBasePrice().multiply(BigDecimal.valueOf(grocery.getQuantity()));
        
        // Check for near-expiration discount
        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), grocery.getExpirationDate());
        if (daysUntilExpiration <= 3 && daysUntilExpiration >= 0) {
            BigDecimal discount = itemPrice.multiply(BigDecimal.valueOf(0.30));
            totalDiscount = totalDiscount.add(discount);
            itemPrice = itemPrice.subtract(discount);
            System.out.printf("Grocery: %s (%s) - Quantity: %d, Base: $%.2f, Near-Expiry Discount: $%.2f, Final: $%.2f%n",
                    grocery.getName(), grocery.getCategory(), grocery.getQuantity(),
                    grocery.getBasePrice().multiply(BigDecimal.valueOf(grocery.getQuantity())), discount, itemPrice);
        } else if (daysUntilExpiration < 0) {
            // Expired items are not allowed
            System.out.printf("WARNING: %s has expired! Item excluded from cart.%n", grocery.getName());
            return; // Don't add to total
        } else {
            System.out.printf("Grocery: %s (%s) - Quantity: %d, Price: $%.2f%n",
                    grocery.getName(), grocery.getCategory(), grocery.getQuantity(), itemPrice);
        }
        
        totalPrice = totalPrice.add(itemPrice);
    }

    /**
     * Simple method to determine current season based on month
     */
    private String getCurrentSeason() {
        int month = LocalDate.now().getMonthValue();
        return switch (month) {
            case 12, 1, 2 -> "winter";
            case 3, 4, 5 -> "spring";
            case 6, 7, 8 -> "summer";
            case 9, 10, 11 -> "fall";
            default -> "unknown";
        };
    }

    public BigDecimal getTotalPrice() {
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount.setScale(2, RoundingMode.HALF_UP);
    }

    public void printSummary() {
        System.out.println("\n=== Shopping Cart Summary ===");
        System.out.printf("Total Discount: $%.2f%n", getTotalDiscount());
        System.out.printf("Total Price: $%.2f%n", getTotalPrice());
        System.out.println("=============================");
    }
} 