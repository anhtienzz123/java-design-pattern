package behavioral.visitor.employeemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Context class that holds a collection of employees and allows visitors to operate on them.
 * This represents the Object Structure in the Visitor pattern.
 */
public class EmployeeDatabase {
    private final List<Employee> employees = new ArrayList<>();
    private final String companyName;
    
    public EmployeeDatabase(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * Adds an employee to the database.
     *
     * @param employee The employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    /**
     * Removes an employee from the database.
     *
     * @param employeeId The ID of the employee to remove
     * @return true if the employee was found and removed, false otherwise
     */
    public boolean removeEmployee(String employeeId) {
        return employees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
    }
    
    /**
     * Accepts a visitor and allows it to visit all employees in the database.
     * This is the key method that enables the Visitor pattern.
     *
     * @param visitor The visitor to accept
     */
    public void acceptVisitor(EmployeeVisitor visitor) {
        System.out.printf("Processing %s employees with %s...%n", 
                companyName, visitor.getClass().getSimpleName());
        
        for (Employee employee : employees) {
            employee.accept(visitor);
        }
    }
    
    /**
     * Gets all employees in the database.
     *
     * @return A copy of the employee list
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    
    /**
     * Gets employees from a specific department.
     *
     * @param department The department name
     * @return List of employees in that department
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        return employees.stream()
                .filter(emp -> emp.getDepartment().equals(department))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the total number of employees.
     *
     * @return Total employee count
     */
    public int getEmployeeCount() {
        return employees.size();
    }
    
    /**
     * Gets the company name.
     *
     * @return Company name
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * Prints basic information about all employees.
     */
    public void printEmployeeList() {
        System.out.printf("\n=== %s EMPLOYEE DIRECTORY ===\n", companyName.toUpperCase());
        System.out.printf("Total Employees: %d%n%n", employees.size());
        
        employees.forEach(System.out::println);
    }
}