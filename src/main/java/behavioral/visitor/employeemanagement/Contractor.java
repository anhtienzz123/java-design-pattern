package behavioral.visitor.employeemanagement;

import java.time.LocalDate;

/**
 * Concrete element representing a contractor.
 * Contractors work on project basis with specific contract terms.
 */
public class Contractor implements Employee {
    private final String employeeId;
    private final String name;
    private final String department;
    private final double contractAmount;
    private final LocalDate contractStartDate;
    private final LocalDate contractEndDate;
    private final String projectName;
    private final boolean isRemote;
    private final double completionPercentage;
    
    public Contractor(String employeeId, String name, String department,
                     double contractAmount, LocalDate contractStartDate, LocalDate contractEndDate,
                     String projectName, boolean isRemote, double completionPercentage) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.contractAmount = contractAmount;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.projectName = projectName;
        this.isRemote = isRemote;
        this.completionPercentage = completionPercentage;
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
        return contractAmount * (completionPercentage / 100.0);
    }
    
    public double getContractAmount() {
        return contractAmount;
    }
    
    public LocalDate getContractStartDate() {
        return contractStartDate;
    }
    
    public LocalDate getContractEndDate() {
        return contractEndDate;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public boolean isRemote() {
        return isRemote;
    }
    
    public double getCompletionPercentage() {
        return completionPercentage;
    }
    
    @Override
    public String toString() {
        return String.format("Contractor{id='%s', name='%s', dept='%s', contract=%.2f, project='%s', completion=%.1f%%}",
                employeeId, name, department, contractAmount, projectName, completionPercentage);
    }
}