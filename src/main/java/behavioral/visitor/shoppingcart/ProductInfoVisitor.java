package behavioral.visitor.shoppingcart;

/**
 * Another concrete visitor that demonstrates the extensibility of the Visitor pattern.
 * This visitor collects product information and statistics without modifying product classes.
 */
public class ProductInfoVisitor implements ProductVisitor {
    private int totalBooks = 0;
    private int totalElectronics = 0;
    private int totalClothing = 0;
    private int totalGroceries = 0;
    private int totalItems = 0;

    @Override
    public void visit(Book book) {
        totalBooks += book.getQuantity();
        totalItems += book.getQuantity();
        System.out.printf("Book: %s (ISBN: %s) %s%n", 
                book.getName(), book.getIsbn(), 
                book.isEducational() ? "[Educational]" : "");
    }

    @Override
    public void visit(Electronics electronics) {
        totalElectronics += electronics.getQuantity();
        totalItems += electronics.getQuantity();
        System.out.printf("Electronics: %s - %s (%d months warranty)%n", 
                electronics.getName(), electronics.getCategory(), electronics.getWarrantyMonths());
    }

    @Override
    public void visit(Clothing clothing) {
        totalClothing += clothing.getQuantity();
        totalItems += clothing.getQuantity();
        System.out.printf("Clothing: %s - %s (Size: %s, Season: %s)%n", 
                clothing.getName(), clothing.getBrand(), clothing.getSize(), clothing.getSeason());
    }

    @Override
    public void visit(Grocery grocery) {
        totalGroceries += grocery.getQuantity();
        totalItems += grocery.getQuantity();
        System.out.printf("Grocery: %s - %s (Expires: %s) %s%n", 
                grocery.getName(), grocery.getCategory(), grocery.getExpirationDate(),
                grocery.isPerishable() ? "[Perishable]" : "");
    }

    public void printSummary() {
        System.out.println("\n=== Cart Statistics ===");
        System.out.printf("Books: %d items%n", totalBooks);
        System.out.printf("Electronics: %d items%n", totalElectronics);
        System.out.printf("Clothing: %d items%n", totalClothing);
        System.out.printf("Groceries: %d items%n", totalGroceries);
        System.out.printf("Total Items: %d%n", totalItems);
        System.out.println("=======================");
    }
} 