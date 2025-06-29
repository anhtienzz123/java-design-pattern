package behavioral.visitor.shoppingcart;

/**
 * Visitor interface that declares visit methods for each product type.
 * This allows different operations to be performed on products without 
 * modifying the product classes.
 */
public interface ProductVisitor {
    void visit(Book book);
    void visit(Electronics electronics);
    void visit(Clothing clothing);
    void visit(Grocery grocery);
} 