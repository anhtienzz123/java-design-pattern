package behavioral.visitor.employeemanagement;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete visitor that calculates total payroll costs for different employee types.
 * Each employee type has different payroll calculation rules.
 */
public class PayrollCalculatorVisitor implements EmployeeVisitor {
    private double totalPayroll = 0.0;
    private final Map<String, Double> departmentPayroll = new HashMap<>();
    private final Map<String, Integer> employeeTypeCount = new HashMap<>();
    private final DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
    
    @Override
    public void visit(FullTimeEmployee employee) {
        // Full-time: Base salary + benefits cost + potential bonus
        double basePay = employee.getBaseSalary();
        double benefitsCost = employee.getBenefitsCost();
        double bonus = employee.isEligibleForBonus() ? basePay * 0.10 : 0.0; // 10% bonus
        
        double totalCost = basePay + benefitsCost + bonus;
        
        addToPayroll(employee.getDepartment(), totalCost, "Full-Time");
        
        System.out.printf("  Full-Time: %s - Base: %s, Benefits: %s, Bonus: %s = Total: %s%n",
                employee.getName(),
                currencyFormat.format(basePay),
                currencyFormat.format(benefitsCost),
                currencyFormat.format(bonus),
                currencyFormat.format(totalCost));
    }
    
    @Override
    public void visit(PartTimeEmployee employee) {
        // Part-time: Hourly rate * hours * weeks + health insurance if applicable
        double basePay = employee.getBaseSalary();
        double healthInsuranceCost = employee.hasHealthInsurance() ? 200.0 : 0.0; // Monthly cost
        
        double totalCost = basePay + healthInsuranceCost;
        
        addToPayroll(employee.getDepartment(), totalCost, "Part-Time");
        
        System.out.printf("  Part-Time: %s - Pay: %s, Insurance: %s = Total: %s%n",
                employee.getName(),
                currencyFormat.format(basePay),
                currencyFormat.format(healthInsuranceCost),
                currencyFormat.format(totalCost));
    }
    
    @Override
    public void visit(Contractor employee) {
        // Contractor: Contract amount * completion percentage
        double contractPay = employee.getBaseSalary(); // Already calculated with completion
        
        addToPayroll(employee.getDepartment(), contractPay, "Contractor");
        
        System.out.printf("  Contractor: %s - Contract: %s (%.1f%% complete) = Total: %s%n",
                employee.getName(),
                currencyFormat.format(employee.getContractAmount()),
                employee.getCompletionPercentage(),
                currencyFormat.format(contractPay));
    }
    
    @Override
    public void visit(Intern employee) {
        // Intern: Monthly stipend * duration (only if paid)
        double stipendCost = employee.getBaseSalary();
        
        if (employee.isPaid()) {
            addToPayroll(employee.getDepartment(), stipendCost, "Intern");
            System.out.printf("  Intern: %s - Stipend: %s/month × %d months = Total: %s%n",
                    employee.getName(),
                    currencyFormat.format(employee.getMonthlyStipend()),
                    employee.getInternshipDurationMonths(),
                    currencyFormat.format(stipendCost));
        } else {
            addToPayroll(employee.getDepartment(), 0.0, "Intern");
            System.out.printf("  Intern: %s - Unpaid internship = Total: $0.00%n",
                    employee.getName());
        }
    }
    
    private void addToPayroll(String department, double amount, String employeeType) {
        totalPayroll += amount;
        departmentPayroll.merge(department, amount, Double::sum);
        employeeTypeCount.merge(employeeType, 1, Integer::sum);
    }
    
    public double getTotalPayroll() {
        return totalPayroll;
    }
    
    public Map<String, Double> getDepartmentPayroll() {
        return new HashMap<>(departmentPayroll);
    }
    
    public void printPayrollSummary() {
        System.out.println("\n=== PAYROLL SUMMARY ===");
        System.out.printf("Total Company Payroll: %s%n", currencyFormat.format(totalPayroll));
        
        System.out.println("\nPayroll by Department:");
        departmentPayroll.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("  %s: %s%n",
                        entry.getKey(), currencyFormat.format(entry.getValue())));
        
        System.out.println("\nEmployee Count by Type:");
        employeeTypeCount.forEach((type, count) ->
                System.out.printf("  %s: %d employees%n", type, count));
    }
}