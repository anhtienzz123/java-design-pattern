package behavioral.visitor.employeemanagement;

/**
 * Base interface for all employee types in the Visitor pattern.
 * This represents the Element interface in the pattern.
 */
public interface Employee {
    /**
     * Accept method that allows visitors to operate on this employee.
     * This is the core method that enables the Visitor pattern.
     *
     * @param visitor The visitor that will perform operations on this employee
     */
    void accept(EmployeeVisitor visitor);
    
    /**
     * Gets the employee's unique identifier.
     *
     * @return Employee ID
     */
    String getEmployeeId();
    
    /**
     * Gets the employee's full name.
     *
     * @return Full name
     */
    String getName();
    
    /**
     * Gets the employee's department.
     *
     * @return Department name
     */
    String getDepartment();
    
    /**
     * Gets the employee's base salary.
     *
     * @return Base salary amount
     */
    double getBaseSalary();
}