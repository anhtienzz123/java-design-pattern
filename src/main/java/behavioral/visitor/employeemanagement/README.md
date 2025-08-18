# Visitor Pattern: Employee Management System

## Overview

This example demonstrates the **Visitor Pattern** using an Employee Management System that performs various HR operations on different types of employees. The Visitor pattern allows you to define new operations on employee objects without changing their classes, making it perfect for HR systems that need to perform multiple different operations on the same employee data.

## Problem Solved

In HR systems, you often need to perform various operations on employees like:
- **Payroll calculation** with different rules for each employee type
- **Performance reviews** with type-specific criteria and reporting
- **Compliance audits** ensuring adherence to labor laws and regulations
- **Benefits administration** with varying eligibility rules
- **Reporting and analytics** across different employee categories

Without the Visitor pattern, you would need to:
- Add methods to each employee class for every new HR operation
- Create bloated employee classes with unrelated functionality
- Violate the Single Responsibility Principle
- Make the codebase tightly coupled and hard to maintain

The Visitor pattern solves this by:
- **Separating HR operations from employee data structures**
- **Allowing new operations without modifying existing employee classes**
- **Grouping related HR operations in visitor classes**
- **Supporting type-specific behavior for different employee types**

## Employee Types

### Full-Time Employee
- Base salary with benefits and vacation days
- Bonus eligibility based on performance and tenure
- Health insurance and comprehensive benefits package
- Performance reviews based on years of service

### Part-Time Employee
- Hourly wages with flexible scheduling
- Limited benefits based on hours worked
- Health insurance eligibility for qualifying hours
- Performance reviews based on consistency and hours

### Contractor
- Project-based contract with completion tracking
- Fixed contract amounts with milestone payments
- Remote work capabilities and project management
- Reviews based on project delivery and completion rates

### Intern
- Educational stipends and learning programs
- Mentorship assignments and university partnerships
- Performance tracking for potential full-time conversion
- Compliance with educational credit requirements

## Visitor Operations

### 1. PayrollCalculatorVisitor
Calculates total payroll costs with different rules for each employee type:

| Employee Type | Calculation Rules |
|---------------|-------------------|
| **Full-Time** | Base salary + benefits cost + bonus (10% if eligible) |
| **Part-Time** | Hourly rate × hours × weeks + health insurance if applicable |
| **Contractor** | Contract amount × completion percentage |
| **Intern** | Monthly stipend × duration (only if paid internship) |

### 2. PerformanceReviewVisitor
Generates performance reviews with type-specific criteria:

- **Full-Time**: Based on years of service, vacation usage, and bonus eligibility
- **Part-Time**: Based on total hours worked and consistency
- **Contractor**: Based on project completion percentage and timeline adherence
- **Intern**: Based on program duration and learning objectives

### 3. ComplianceAuditVisitor
Performs compliance audits ensuring legal and regulatory adherence:

- **Wage compliance**: Minimum wage requirements for all employee types
- **Benefits compliance**: Mandatory benefits for eligible employees
- **Contract compliance**: Maximum contract durations and renewal policies
- **Intern compliance**: Educational credit requirements and mentorship assignments

## Key Benefits

1. **Separation of Concerns**: HR operations are separated from employee data
2. **Easy Extension**: Add new HR operations without modifying employee classes
3. **Type Safety**: Compile-time checking ensures all employee types are handled
4. **Single Responsibility**: Each visitor handles one specific HR operation
5. **Open/Closed Principle**: Open for extension, closed for modification
6. **Regulatory Compliance**: Centralized compliance logic for easier auditing

## Structure

### Employee Hierarchy
```
Employee (interface)
├── FullTimeEmployee
├── PartTimeEmployee  
├── Contractor
└── Intern
```

### Visitor Interface
```java
interface EmployeeVisitor {
    void visit(FullTimeEmployee employee);
    void visit(PartTimeEmployee employee);
    void visit(Contractor employee);
    void visit(Intern employee);
}
```

### Concrete Visitors
- **`PayrollCalculatorVisitor`**: Calculates total company payroll with type-specific rules
- **`PerformanceReviewVisitor`**: Generates comprehensive performance reviews
- **`ComplianceAuditVisitor`**: Ensures regulatory and legal compliance

### Employee Database
- **`EmployeeDatabase`**: Container class that manages employees and accepts visitors

## Usage Example

```java
// Create employee database
EmployeeDatabase company = new EmployeeDatabase("TechCorp Solutions");

// Add different types of employees
company.addEmployee(new FullTimeEmployee("FT001", "Alice Johnson", "Engineering",
    85000.0, 20, 1200.0, 5, true));
company.addEmployee(new PartTimeEmployee("PT001", "Bob Smith", "Support",
    22.0, 20, true, 48));
company.addEmployee(new Contractor("CT001", "Carol Davis", "Design",
    50000.0, startDate, endDate, "UI Redesign", true, 75.0));

// Calculate payroll
PayrollCalculatorVisitor payrollCalculator = new PayrollCalculatorVisitor();
company.acceptVisitor(payrollCalculator);
System.out.println("Total Payroll: " + payrollCalculator.getTotalPayroll());

// Generate performance reviews
PerformanceReviewVisitor reviewer = new PerformanceReviewVisitor();
company.acceptVisitor(reviewer);
reviewer.printAllReviews();

// Perform compliance audit
ComplianceAuditVisitor auditor = new ComplianceAuditVisitor();
company.acceptVisitor(auditor);
auditor.printComplianceReport();
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.visitor.employeemanagement.ZMain"

# Or using java directly
java -cp target/classes behavioral.visitor.employeemanagement.ZMain
```

## Expected Output

### 1. Employee Directory
- Complete listing of all employees with their basic information
- Employee count and department distribution

### 2. Payroll Calculation
- **Individual Calculations**: Detailed breakdown for each employee
  - Full-time: Base salary + benefits + bonus calculations
  - Part-time: Hourly calculations + insurance costs
  - Contractor: Contract amount × completion percentage
  - Intern: Stipend calculations for paid positions
- **Department Summary**: Total payroll costs by department
- **Employee Type Summary**: Count and costs by employee type

### 3. Performance Reviews
- **Individual Reviews**: Type-specific performance evaluations
  - Performance levels: Excellent, Good, Developing, Needs Improvement
  - Tailored recommendations for each employee type
  - Department-specific insights and suggestions
- **Department Summary**: Review completion statistics

### 4. Compliance Audit
- **Critical Issues**: Must-fix compliance violations
  - Wage and hour law violations
  - Missing required benefits or documentation
  - Contract duration or terms violations
- **Warnings**: Items requiring attention
  - Below-average compensation packages
  - Missing program assignments for interns
  - Potential compliance risks
- **Department Analysis**: Issue distribution by department

## Advanced Features

### Payroll Features
- **Multi-tier Calculations**: Different rules for each employee type
- **Benefits Integration**: Automatic benefits cost calculation
- **Bonus Calculations**: Performance-based bonus determination
- **Department Analytics**: Payroll distribution analysis

### Performance Review Features
- **Dynamic Criteria**: Adaptive review criteria based on employee type
- **Recommendation Engine**: Intelligent suggestions for career development
- **Department Insights**: Cross-departmental performance comparisons
- **Historical Tracking**: Support for multi-period review cycles

### Compliance Features
- **Regulatory Compliance**: Wage and hour law enforcement
- **Benefits Compliance**: Mandatory benefits verification
- **Contract Compliance**: Legal contract term validation
- **Audit Trails**: Comprehensive compliance reporting

## When to Use This Pattern

- You have a **stable employee structure** but need to add new HR operations frequently
- You want to **avoid polluting** employee classes with unrelated HR operations
- You need to **perform multiple different operations** on the same employee data
- You want to **group related HR operations** together for better organization
- You're building an **HR system** where different departments need custom operations

## Real-World Applications

- **Human Resources**: Payroll processing, performance management, compliance auditing
- **Accounting Systems**: Financial reporting, tax calculation, budget analysis
- **Project Management**: Resource allocation, time tracking, billing calculations
- **Healthcare Systems**: Patient billing, insurance processing, compliance reporting
- **Educational Systems**: Grade calculation, transcript generation, compliance tracking

## Extension Example

To add a new HR operation (e.g., Training Requirements Analysis):

```java
public class TrainingRequirementsVisitor implements EmployeeVisitor {
    @Override
    public void visit(FullTimeEmployee employee) {
        // Analyze mandatory training requirements for full-time staff
    }
    
    @Override
    public void visit(PartTimeEmployee employee) {
        // Determine applicable training for part-time employees
    }
    
    @Override
    public void visit(Contractor employee) {
        // Check project-specific training needs
    }
    
    @Override
    public void visit(Intern employee) {
        // Plan educational and skills training programs
    }
}
```

**No changes needed** to existing classes:
- Employee classes remain unchanged
- Existing visitors continue to work
- Database structure stays the same

This demonstrates the **Open/Closed Principle**: the system is open for extension (new HR operations) but closed for modification (existing employee classes).

## Key Design Principles Applied

- **SOLID Principles**: Single responsibility, Open/closed principle, Interface segregation
- **DRY**: No repetition of employee data or HR operation logic  
- **KISS**: Simple, clear interface design with focused responsibilities
- **YAGNI**: Only implemented necessary HR operations and employee attributes
- **Java 21 Features**: Modern switch expressions, records-style immutability patterns
- **OWASP Security**: Input validation and safe data handling practices