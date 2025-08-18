package behavioral.visitor.employeemanagement;

import java.time.LocalDate;

/**
 * Main class demonstrating the Visitor pattern with an Employee Management System.
 * 
 * This example shows how different HR operations (payroll calculation, performance reviews, 
 * compliance audits) can be performed on various employee types without modifying the 
 * employee classes themselves.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("🏢 Employee Management System - Visitor Pattern Demo");
        System.out.println("=" .repeat(60));
        
        // Create employee database
        EmployeeDatabase company = new EmployeeDatabase("TechCorp Solutions");
        
        // Add various types of employees
        populateEmployeeDatabase(company);
        
        // Display all employees
        company.printEmployeeList();
        
        // Demonstrate different visitor operations
        demonstratePayrollCalculation(company);
        demonstratePerformanceReviews(company);
        demonstrateComplianceAudit(company);
        
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("✅ Visitor Pattern demonstration completed!");
        System.out.println("Key Benefits Demonstrated:");
        System.out.println("  • Operations separated from employee data structures");
        System.out.println("  • New operations added without modifying employee classes");
        System.out.println("  • Type-specific behavior for each employee category");
        System.out.println("  • Easy to extend with new visitor implementations");
    }
    
    private static void populateEmployeeDatabase(EmployeeDatabase company) {
        // Full-time employees
        company.addEmployee(new FullTimeEmployee(
                "FT001", "Alice Johnson", "Engineering",
                85000.0, 20, 1200.0, 5, true));
        
        company.addEmployee(new FullTimeEmployee(
                "FT002", "Bob Smith", "Marketing",
                65000.0, 15, 800.0, 2, false));
        
        company.addEmployee(new FullTimeEmployee(
                "FT003", "Carol Williams", "Engineering",
                95000.0, 25, 1500.0, 8, true));
        
        // Part-time employees
        company.addEmployee(new PartTimeEmployee(
                "PT001", "David Brown", "Support",
                22.0, 20, true, 48));
        
        company.addEmployee(new PartTimeEmployee(
                "PT002", "Eva Davis", "Marketing",
                18.0, 15, false, 24));
        
        // Contractors
        company.addEmployee(new Contractor(
                "CT001", "Frank Miller", "Engineering",
                75000.0,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 12, 31),
                "Mobile App Development", true, 85.0));
        
        company.addEmployee(new Contractor(
                "CT002", "Grace Wilson", "Design",
                45000.0,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2025, 2, 28),
                "UI/UX Redesign", false, 60.0));
        
        // Interns
        company.addEmployee(new Intern(
                "IN001", "Henry Chen", "Engineering",
                1500.0, "Alice Johnson", "Stanford University",
                6, "Software Development", true));
        
        company.addEmployee(new Intern(
                "IN002", "Ivy Rodriguez", "Marketing",
                0.0, "Bob Smith", "UC Berkeley",
                4, "Digital Marketing", false));
        
        company.addEmployee(new Intern(
                "IN003", "Jack Thompson", "Design",
                1200.0, "Grace Wilson", "Art Institute",
                3, "Graphic Design", true));
    }
    
    private static void demonstratePayrollCalculation(EmployeeDatabase company) {
        System.out.println("\n" + "💰".repeat(20));
        System.out.println("PAYROLL CALCULATION DEMONSTRATION");
        System.out.println("💰".repeat(20));
        
        PayrollCalculatorVisitor payrollVisitor = new PayrollCalculatorVisitor();
        company.acceptVisitor(payrollVisitor);
        payrollVisitor.printPayrollSummary();
    }
    
    private static void demonstratePerformanceReviews(EmployeeDatabase company) {
        System.out.println("\n" + "📊".repeat(20));
        System.out.println("PERFORMANCE REVIEW DEMONSTRATION");
        System.out.println("📊".repeat(20));
        
        PerformanceReviewVisitor reviewVisitor = new PerformanceReviewVisitor();
        company.acceptVisitor(reviewVisitor);
        reviewVisitor.printAllReviews();
    }
    
    private static void demonstrateComplianceAudit(EmployeeDatabase company) {
        System.out.println("\n" + "🔍".repeat(20));
        System.out.println("COMPLIANCE AUDIT DEMONSTRATION");
        System.out.println("🔍".repeat(20));
        
        ComplianceAuditVisitor auditVisitor = new ComplianceAuditVisitor();
        company.acceptVisitor(auditVisitor);
        auditVisitor.printComplianceReport();
    }
}