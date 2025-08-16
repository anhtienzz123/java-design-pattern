package structural.composite.organizationhierarchy;

// Client code
public class ZMain {

	public static void main(String[] args) {
		// Create the organization structure

		// Top-level director
		Director cto = new Director("John Smith", 200000, "Technology");

		// Engineering department
		Manager engineeringManager = new Manager("Jane Doe", 150000, "Engineering");
		Developer frontendDev = new Developer("Mike Johnson", 100000, "JavaScript");
		Developer backendDev = new Developer("Sarah Williams", 110000, "Java");
		Developer mobileDev = new Developer("Tom Brown", 105000, "Swift");

		engineeringManager.addSubordinate(frontendDev);
		engineeringManager.addSubordinate(backendDev);
		engineeringManager.addSubordinate(mobileDev);

		// Design department
		Manager designManager = new Manager("Emily Davis", 140000, "Design");
		Designer uiDesigner = new Designer("David Wilson", 90000, "UI");
		Designer uxDesigner = new Designer("Lisa Martin", 95000, "UX");

		designManager.addSubordinate(uiDesigner);
		designManager.addSubordinate(uxDesigner);

		// QA department
		Manager qaManager = new Manager("Robert Taylor", 130000, "Quality Assurance");
		Developer qaEngineer1 = new Developer("Patricia Anderson", 85000, "Python");
		Developer qaEngineer2 = new Developer("James Thomas", 80000, "Java");

		qaManager.addSubordinate(qaEngineer1);
		qaManager.addSubordinate(qaEngineer2);

		// Add departments to CTO
		cto.addSubordinate(engineeringManager);
		cto.addSubordinate(designManager);
		cto.addSubordinate(qaManager);

		// Print the entire organization
		System.out.println("Complete Organization Structure:");
		cto.printDetails(0);

		// Calculate and display some statistics
		System.out.println("\nOrganization Statistics:");
		System.out.println("Total number of employees: " + cto.getEmployeeCount());
		System.out.println("Total salary budget: $" + String.format("%.2f", cto.calculateTotalSalary()));

		System.out.println("\nEngineering Department Statistics:");
		System.out.println("Number of employees: " + engineeringManager.getEmployeeCount());
		System.out.println("Department budget: $" + String.format("%.2f", engineeringManager.calculateTotalSalary()));

		// Demonstrate treating leaf and composite objects uniformly
		System.out.println("\nDemonstrating uniform treatment:");
		printEmployeeInfo(frontendDev);
		printEmployeeInfo(designManager);
		printEmployeeInfo(cto);
	}

	// Method demonstrating treating individual employees and groups uniformly
	private static void printEmployeeInfo(Employee employee) {
		System.out.println("Employee: " + employee.getName());
		System.out.println("Position: " + employee.getPosition());
		System.out.println("Total salary impact: $" + String.format("%.2f", employee.calculateTotalSalary()));
		System.out.println("Number of positions: " + employee.getEmployeeCount());
		System.out.println();
	}
}
