package behavioral.visitor.employeemanagement;

/**
 * Concrete element representing a part-time employee.
 * Part-time employees work a specific number of hours per week.
 */
public class PartTimeEmployee implements Employee {
    private final String employeeId;
    private final String name;
    private final String department;
    private final double hourlyRate;
    private final int hoursPerWeek;
    private final boolean hasHealthInsurance;
    private final int weeksWorked;
    
    public PartTimeEmployee(String employeeId, String name, String department,
                           double hourlyRate, int hoursPerWeek, boolean hasHealthInsurance,
                           int weeksWorked) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hourlyRate = hourlyRate;
        this.hoursPerWeek = hoursPerWeek;
        this.hasHealthInsurance = hasHealthInsurance;
        this.weeksWorked = weeksWorked;
    }
    
    @Override
    public void accept(EmployeeVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String getEmployeeId() {
        return employeeId;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDepartment() {
        return department;
    }
    
    @Override
    public double getBaseSalary() {
        return hourlyRate * hoursPerWeek * weeksWorked;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    public int getHoursPerWeek() {
        return hoursPerWeek;
    }
    
    public boolean hasHealthInsurance() {
        return hasHealthInsurance;
    }
    
    public int getWeeksWorked() {
        return weeksWorked;
    }
    
    @Override
    public String toString() {
        return String.format("PartTimeEmployee{id='%s', name='%s', dept='%s', rate=%.2f/hr, hours=%d/week, weeks=%d}",
                employeeId, name, department, hourlyRate, hoursPerWeek, weeksWorked);
    }
}