package behavioral.visitor.employeemanagement;

/**
 * Visitor interface that defines operations that can be performed on different employee types.
 * This is the Visitor interface in the pattern.
 */
public interface EmployeeVisitor {
    /**
     * Visit method for full-time employees.
     * 
     * @param employee The full-time employee to visit
     */
    void visit(FullTimeEmployee employee);
    
    /**
     * Visit method for part-time employees.
     * 
     * @param employee The part-time employee to visit
     */
    void visit(PartTimeEmployee employee);
    
    /**
     * Visit method for contractor employees.
     * 
     * @param employee The contractor employee to visit
     */
    void visit(Contractor employee);
    
    /**
     * Visit method for intern employees.
     * 
     * @param employee The intern employee to visit
     */
    void visit(Intern employee);
}