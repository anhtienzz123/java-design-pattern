package behavioral.visitor.employeemanagement;

/**
 * Concrete element representing an intern.
 * Interns have stipends, mentors, and specific learning programs.
 */
public class Intern implements Employee {
    private final String employeeId;
    private final String name;
    private final String department;
    private final double monthlyStipend;
    private final String mentorName;
    private final String university;
    private final int internshipDurationMonths;
    private final String learningProgram;
    private final boolean isPaid;
    
    public Intern(String employeeId, String name, String department,
                 double monthlyStipend, String mentorName, String university,
                 int internshipDurationMonths, String learningProgram, boolean isPaid) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.monthlyStipend = monthlyStipend;
        this.mentorName = mentorName;
        this.university = university;
        this.internshipDurationMonths = internshipDurationMonths;
        this.learningProgram = learningProgram;
        this.isPaid = isPaid;
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
        return isPaid ? monthlyStipend * internshipDurationMonths : 0.0;
    }
    
    public double getMonthlyStipend() {
        return monthlyStipend;
    }
    
    public String getMentorName() {
        return mentorName;
    }
    
    public String getUniversity() {
        return university;
    }
    
    public int getInternshipDurationMonths() {
        return internshipDurationMonths;
    }
    
    public String getLearningProgram() {
        return learningProgram;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    @Override
    public String toString() {
        return String.format("Intern{id='%s', name='%s', dept='%s', stipend=%.2f/month, duration=%d months, university='%s'}",
                employeeId, name, department, monthlyStipend, internshipDurationMonths, university);
    }
}