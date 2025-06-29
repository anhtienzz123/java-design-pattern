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
		
//		== Ouput: 
//		Complete Organization Structure:
//			Director: John Smith
//			    Position: Director
//			    Division: Technology
//			    Salary: $200000.00
//			    Division Size: 11
//			    Division Budget: $1285000.00
//			    Departments:
//			        Manager: Jane Doe
//			            Position: Manager
//			            Department: Engineering
//			            Salary: $150000.00
//			            Team Size: 4
//			            Team Budget: $465000.00
//			            Team Members:
//			                Developer: Mike Johnson
//			                    Position: Developer
//			                    Salary: $100000.00
//			                    Language: JavaScript
//			                Developer: Sarah Williams
//			                    Position: Developer
//			                    Salary: $110000.00
//			                    Language: Java
//			                Developer: Tom Brown
//			                    Position: Developer
//			                    Salary: $105000.00
//			                    Language: Swift
//			        Manager: Emily Davis
//			            Position: Manager
//			            Department: Design
//			            Salary: $140000.00
//			            Team Size: 3
//			            Team Budget: $325000.00
//			            Team Members:
//			                Designer: David Wilson
//			                    Position: Designer
//			                    Salary: $90000.00
//			                    Specialty: UI
//			                Designer: Lisa Martin
//			                    Position: Designer
//			                    Salary: $95000.00
//			                    Specialty: UX
//			        Manager: Robert Taylor
//			            Position: Manager
//			            Department: Quality Assurance
//			            Salary: $130000.00
//			            Team Size: 3
//			            Team Budget: $295000.00
//			            Team Members:
//			                Developer: Patricia Anderson
//			                    Position: Developer
//			                    Salary: $85000.00
//			                    Language: Python
//			                Developer: James Thomas
//			                    Position: Developer
//			                    Salary: $80000.00
//			                    Language: Java
//
//			Organization Statistics:
//			Total number of employees: 11
//			Total salary budget: $1285000.00
//
//			Engineering Department Statistics:
//			Number of employees: 4
//			Department budget: $465000.00
//
//			Demonstrating uniform treatment:
//			Employee: Mike Johnson
//			Position: Developer
//			Total salary impact: $100000.00
//			Number of positions: 1
//
//			Employee: Emily Davis
//			Position: Manager
//			Total salary impact: $325000.00
//			Number of positions: 3
//
//			Employee: John Smith
//			Position: Director
//			Total salary impact: $1285000.00
//			Number of positions: 11
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
