package behavioral.visitor.employeemanagement;

/**
 * Concrete element representing a full-time employee.
 * Full-time employees have benefits, vacation days, and potential for bonuses.
 */
public class FullTimeEmployee implements Employee {
    private final String employeeId;
    private final String name;
    private final String department;
    private final double baseSalary;
    private final int vacationDays;
    private final double benefitsCost;
    private final int yearsOfService;
    private final boolean eligibleForBonus;
    
    public FullTimeEmployee(String employeeId, String name, String department, 
                           double baseSalary, int vacationDays, double benefitsCost,
                           int yearsOfService, boolean eligibleForBonus) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.vacationDays = vacationDays;
        this.benefitsCost = benefitsCost;
        this.yearsOfService = yearsOfService;
        this.eligibleForBonus = eligibleForBonus;
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
        return baseSalary;
    }
    
    public int getVacationDays() {
        return vacationDays;
    }
    
    public double getBenefitsCost() {
        return benefitsCost;
    }
    
    public int getYearsOfService() {
        return yearsOfService;
    }
    
    public boolean isEligibleForBonus() {
        return eligibleForBonus;
    }
    
    @Override
    public String toString() {
        return String.format("FullTimeEmployee{id='%s', name='%s', dept='%s', salary=%.2f, vacation=%d days, years=%d}",
                employeeId, name, department, baseSalary, vacationDays, yearsOfService);
    }
}