package behavioral.visitor.employeemanagement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete visitor that performs compliance audits for different employee types.
 * Each employee type has different compliance requirements and regulations.
 */
public class ComplianceAuditVisitor implements EmployeeVisitor {
    private final List<String> complianceIssues = new ArrayList<>();
    private final List<String> complianceWarnings = new ArrayList<>();
    private final Map<String, Integer> departmentIssueCount = new HashMap<>();
    private int totalEmployeesAudited = 0;
    
    @Override
    public void visit(FullTimeEmployee employee) {
        totalEmployeesAudited++;
        auditFullTimeCompliance(employee);
    }
    
    @Override
    public void visit(PartTimeEmployee employee) {
        totalEmployeesAudited++;
        auditPartTimeCompliance(employee);
    }
    
    @Override
    public void visit(Contractor employee) {
        totalEmployeesAudited++;
        auditContractorCompliance(employee);
    }
    
    @Override
    public void visit(Intern employee) {
        totalEmployeesAudited++;
        auditInternCompliance(employee);
    }
    
    private void auditFullTimeCompliance(FullTimeEmployee employee) {
        String employeeInfo = String.format("%s (%s)", employee.getName(), employee.getEmployeeId());
        
        // Check minimum wage compliance
        double minimumAnnualSalary = 35000.0;
        if (employee.getBaseSalary() < minimumAnnualSalary) {
            addIssue(employee.getDepartment(),
                    String.format("CRITICAL: %s salary ($%.2f) below minimum wage requirement ($%.2f)",
                            employeeInfo, employee.getBaseSalary(), minimumAnnualSalary));
        }
        
        // Check vacation days compliance (minimum 10 days for full-time)
        if (employee.getVacationDays() < 10) {
            addIssue(employee.getDepartment(),
                    String.format("WARNING: %s has insufficient vacation days (%d, minimum 10)",
                            employeeInfo, employee.getVacationDays()));
        }
        
        // Check benefits compliance
        if (employee.getBenefitsCost() < 500.0) {
            addWarning(String.format("%s has low benefits cost ($%.2f), review benefits package",
                    employeeInfo, employee.getBenefitsCost()));
        }
        
        // Check long-term employee review compliance
        if (employee.getYearsOfService() > 5 && !employee.isEligibleForBonus()) {
            addWarning(String.format("%s has %d years service but not eligible for bonus - review compensation",
                    employeeInfo, employee.getYearsOfService()));
        }
    }
    
    private void auditPartTimeCompliance(PartTimeEmployee employee) {
        String employeeInfo = String.format("%s (%s)", employee.getName(), employee.getEmployeeId());
        
        // Check minimum wage compliance
        double minimumHourlyRate = 15.0;
        if (employee.getHourlyRate() < minimumHourlyRate) {
            addIssue(employee.getDepartment(),
                    String.format("CRITICAL: %s hourly rate ($%.2f) below minimum wage ($%.2f)",
                            employeeInfo, employee.getHourlyRate(), minimumHourlyRate));
        }
        
        // Check maximum hours compliance (part-time should not exceed 30 hours/week)
        if (employee.getHoursPerWeek() > 30) {
            addIssue(employee.getDepartment(),
                    String.format("WARNING: %s working %d hours/week (exceeds part-time limit of 30)",
                            employeeInfo, employee.getHoursPerWeek()));
        }
        
        // Check health insurance eligibility (20+ hours/week for 6+ months)
        int totalHours = employee.getHoursPerWeek() * employee.getWeeksWorked();
        boolean eligibleForInsurance = employee.getHoursPerWeek() >= 20 && employee.getWeeksWorked() >= 24;
        
        if (eligibleForInsurance && !employee.hasHealthInsurance()) {
            addWarning(String.format("%s eligible for health insurance but not enrolled (%d hrs/week, %d weeks)",
                    employeeInfo, employee.getHoursPerWeek(), employee.getWeeksWorked()));
        }
    }
    
    private void auditContractorCompliance(Contractor employee) {
        String employeeInfo = String.format("%s (%s)", employee.getName(), employee.getEmployeeId());
        
        // Check contract date validity
        LocalDate today = LocalDate.now();
        if (employee.getContractEndDate().isBefore(today)) {
            addIssue(employee.getDepartment(),
                    String.format("CRITICAL: %s contract expired on %s",
                            employeeInfo, employee.getContractEndDate()));
        }
        
        // Check contract duration compliance (max 2 years for contractors)
        long contractDurationMonths = ChronoUnit.MONTHS.between(
                employee.getContractStartDate(), employee.getContractEndDate());
        if (contractDurationMonths > 24) {
            addIssue(employee.getDepartment(),
                    String.format("WARNING: %s contract duration (%d months) exceeds recommended limit (24 months)",
                            employeeInfo, contractDurationMonths));
        }
        
        // Check minimum contract amount
        double minimumContractAmount = 10000.0;
        if (employee.getContractAmount() < minimumContractAmount) {
            addWarning(String.format("%s contract amount ($%.2f) below typical minimum ($%.2f)",
                    employeeInfo, employee.getContractAmount(), minimumContractAmount));
        }
        
        // Check project completion status
        if (employee.getCompletionPercentage() < 50.0 && 
            ChronoUnit.DAYS.between(employee.getContractStartDate(), today) > 180) {
            addWarning(String.format("%s project completion (%.1f%%) low for contract age (%d days)",
                    employeeInfo, employee.getCompletionPercentage(),
                    ChronoUnit.DAYS.between(employee.getContractStartDate(), today)));
        }
    }
    
    private void auditInternCompliance(Intern employee) {
        String employeeInfo = String.format("%s (%s)", employee.getName(), employee.getEmployeeId());
        
        // Check intern duration compliance (max 12 months)
        if (employee.getInternshipDurationMonths() > 12) {
            addIssue(employee.getDepartment(),
                    String.format("WARNING: %s internship duration (%d months) exceeds recommended limit (12 months)",
                            employeeInfo, employee.getInternshipDurationMonths()));
        }
        
        // Check mentor assignment compliance
        if (employee.getMentorName() == null || employee.getMentorName().trim().isEmpty()) {
            addIssue(employee.getDepartment(),
                    String.format("CRITICAL: %s has no assigned mentor",
                            employeeInfo));
        }
        
        // Check learning program assignment
        if (employee.getLearningProgram() == null || employee.getLearningProgram().trim().isEmpty()) {
            addWarning(String.format("%s has no assigned learning program",
                    employeeInfo));
        }
        
        // Check stipend compliance for paid interns
        if (employee.isPaid()) {
            double minimumStipend = 1000.0;
            if (employee.getMonthlyStipend() < minimumStipend) {
                addWarning(String.format("%s stipend ($%.2f) below recommended minimum ($%.2f)",
                        employeeInfo, employee.getMonthlyStipend(), minimumStipend));
            }
        }
        
        // Check university affiliation for academic credit
        if (employee.getUniversity() == null || employee.getUniversity().trim().isEmpty()) {
            addWarning(String.format("%s has no university affiliation listed",
                    employeeInfo));
        }
    }
    
    private void addIssue(String department, String issue) {
        complianceIssues.add(issue);
        departmentIssueCount.merge(department, 1, Integer::sum);
    }
    
    private void addWarning(String warning) {
        complianceWarnings.add(warning);
    }
    
    public List<String> getComplianceIssues() {
        return new ArrayList<>(complianceIssues);
    }
    
    public List<String> getComplianceWarnings() {
        return new ArrayList<>(complianceWarnings);
    }
    
    public boolean hasComplianceIssues() {
        return !complianceIssues.isEmpty();
    }
    
    public void printComplianceReport() {
        System.out.println("\n=== COMPLIANCE AUDIT REPORT ===");
        System.out.printf("Total Employees Audited: %d%n", totalEmployeesAudited);
        System.out.printf("Critical Issues Found: %d%n", complianceIssues.size());
        System.out.printf("Warnings Found: %d%n", complianceWarnings.size());
        
        if (!complianceIssues.isEmpty()) {
            System.out.println("\n🚨 CRITICAL COMPLIANCE ISSUES:");
            complianceIssues.forEach(issue -> System.out.println("  • " + issue));
        }
        
        if (!complianceWarnings.isEmpty()) {
            System.out.println("\n⚠️  COMPLIANCE WARNINGS:");
            complianceWarnings.forEach(warning -> System.out.println("  • " + warning));
        }
        
        if (complianceIssues.isEmpty() && complianceWarnings.isEmpty()) {
            System.out.println("\n✅ No compliance issues found. All employees meet requirements.");
        }
        
        System.out.println("\nIssues by Department:");
        departmentIssueCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("  %s: %d issues%n",
                        entry.getKey(), entry.getValue()));
    }
}