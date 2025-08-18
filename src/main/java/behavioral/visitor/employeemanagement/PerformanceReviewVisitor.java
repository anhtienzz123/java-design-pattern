package behavioral.visitor.employeemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete visitor that generates performance review reports for different employee types.
 * Each employee type has different review criteria and reporting requirements.
 */
public class PerformanceReviewVisitor implements EmployeeVisitor {
    private final List<String> reviewReports = new ArrayList<>();
    private final Map<String, Integer> departmentReviews = new HashMap<>();
    private final Map<String, List<String>> reviewRecommendations = new HashMap<>();
    
    @Override
    public void visit(FullTimeEmployee employee) {
        String reviewLevel = determineFullTimeReviewLevel(employee);
        String recommendations = generateFullTimeRecommendations(employee, reviewLevel);
        
        String report = String.format(
                "FULL-TIME REVIEW - %s (%s):%n" +
                "  Department: %s%n" +
                "  Years of Service: %d%n" +
                "  Vacation Days Used: %d%n" +
                "  Performance Level: %s%n" +
                "  Recommendations: %s%n",
                employee.getName(), employee.getEmployeeId(),
                employee.getDepartment(), employee.getYearsOfService(),
                employee.getVacationDays(), reviewLevel, recommendations);
        
        reviewReports.add(report);
        departmentReviews.merge(employee.getDepartment(), 1, Integer::sum);
        addRecommendation(employee.getDepartment(), recommendations);
    }
    
    @Override
    public void visit(PartTimeEmployee employee) {
        String reviewLevel = determinePartTimeReviewLevel(employee);
        String recommendations = generatePartTimeRecommendations(employee, reviewLevel);
        
        String report = String.format(
                "PART-TIME REVIEW - %s (%s):%n" +
                "  Department: %s%n" +
                "  Hours/Week: %d%n" +
                "  Weeks Worked: %d%n" +
                "  Performance Level: %s%n" +
                "  Recommendations: %s%n",
                employee.getName(), employee.getEmployeeId(),
                employee.getDepartment(), employee.getHoursPerWeek(),
                employee.getWeeksWorked(), reviewLevel, recommendations);
        
        reviewReports.add(report);
        departmentReviews.merge(employee.getDepartment(), 1, Integer::sum);
        addRecommendation(employee.getDepartment(), recommendations);
    }
    
    @Override
    public void visit(Contractor employee) {
        String reviewLevel = determineContractorReviewLevel(employee);
        String recommendations = generateContractorRecommendations(employee, reviewLevel);
        
        String report = String.format(
                "CONTRACTOR REVIEW - %s (%s):%n" +
                "  Department: %s%n" +
                "  Project: %s%n" +
                "  Contract Period: %s to %s%n" +
                "  Completion: %.1f%%%n" +
                "  Performance Level: %s%n" +
                "  Recommendations: %s%n",
                employee.getName(), employee.getEmployeeId(),
                employee.getDepartment(), employee.getProjectName(),
                employee.getContractStartDate(), employee.getContractEndDate(),
                employee.getCompletionPercentage(), reviewLevel, recommendations);
        
        reviewReports.add(report);
        departmentReviews.merge(employee.getDepartment(), 1, Integer::sum);
        addRecommendation(employee.getDepartment(), recommendations);
    }
    
    @Override
    public void visit(Intern employee) {
        String reviewLevel = determineInternReviewLevel(employee);
        String recommendations = generateInternRecommendations(employee, reviewLevel);
        
        String report = String.format(
                "INTERN REVIEW - %s (%s):%n" +
                "  Department: %s%n" +
                "  University: %s%n" +
                "  Program: %s%n" +
                "  Duration: %d months%n" +
                "  Mentor: %s%n" +
                "  Performance Level: %s%n" +
                "  Recommendations: %s%n",
                employee.getName(), employee.getEmployeeId(),
                employee.getDepartment(), employee.getUniversity(),
                employee.getLearningProgram(), employee.getInternshipDurationMonths(),
                employee.getMentorName(), reviewLevel, recommendations);
        
        reviewReports.add(report);
        departmentReviews.merge(employee.getDepartment(), 1, Integer::sum);
        addRecommendation(employee.getDepartment(), recommendations);
    }
    
    private String determineFullTimeReviewLevel(FullTimeEmployee employee) {
        if (employee.getYearsOfService() >= 5 && employee.isEligibleForBonus()) {
            return "Excellent";
        } else if (employee.getYearsOfService() >= 2) {
            return "Good";
        } else {
            return "Developing";
        }
    }
    
    private String generateFullTimeRecommendations(FullTimeEmployee employee, String reviewLevel) {
        return switch (reviewLevel) {
            case "Excellent" -> "Consider for promotion, leadership opportunities";
            case "Good" -> "Continue professional development, consider skill expansion";
            default -> "Focus on core competencies, seek mentorship";
        };
    }
    
    private String determinePartTimeReviewLevel(PartTimeEmployee employee) {
        int totalHours = employee.getHoursPerWeek() * employee.getWeeksWorked();
        if (totalHours >= 800) {
            return "Excellent";
        } else if (totalHours >= 400) {
            return "Good";
        } else {
            return "Developing";
        }
    }
    
    private String generatePartTimeRecommendations(PartTimeEmployee employee, String reviewLevel) {
        return switch (reviewLevel) {
            case "Excellent" -> "Consider full-time position, excellent reliability";
            case "Good" -> "Increase hours if available, consistent performance";
            default -> "Improve consistency, clarify expectations";
        };
    }
    
    private String determineContractorReviewLevel(Contractor employee) {
        if (employee.getCompletionPercentage() >= 90) {
            return "Excellent";
        } else if (employee.getCompletionPercentage() >= 70) {
            return "Good";
        } else {
            return "Needs Improvement";
        }
    }
    
    private String generateContractorRecommendations(Contractor employee, String reviewLevel) {
        return switch (reviewLevel) {
            case "Excellent" -> "Consider for future contracts, exemplary delivery";
            case "Good" -> "Continue current trajectory, minor improvements needed";
            default -> "Requires closer monitoring, delivery concerns";
        };
    }
    
    private String determineInternReviewLevel(Intern employee) {
        if (employee.getInternshipDurationMonths() >= 6) {
            return "Excellent";
        } else if (employee.getInternshipDurationMonths() >= 3) {
            return "Good";
        } else {
            return "Early Stage";
        }
    }
    
    private String generateInternRecommendations(Intern employee, String reviewLevel) {
        return switch (reviewLevel) {
            case "Excellent" -> "Consider full-time offer, strong potential";
            case "Good" -> "Continue mentoring, explore permanent opportunities";
            default -> "Focus on learning objectives, regular check-ins";
        };
    }
    
    private void addRecommendation(String department, String recommendation) {
        reviewRecommendations.computeIfAbsent(department, k -> new ArrayList<>()).add(recommendation);
    }
    
    public List<String> getReviewReports() {
        return new ArrayList<>(reviewReports);
    }
    
    public void printAllReviews() {
        System.out.println("\n=== PERFORMANCE REVIEW REPORTS ===");
        reviewReports.forEach(System.out::print);
        
        System.out.println("\n=== DEPARTMENT REVIEW SUMMARY ===");
        departmentReviews.forEach((dept, count) ->
                System.out.printf("%s: %d reviews completed%n", dept, count));
    }
}