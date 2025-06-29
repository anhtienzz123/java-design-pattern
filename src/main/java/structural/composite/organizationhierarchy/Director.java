package structural.composite.organizationhierarchy;

import java.util.ArrayList;
import java.util.List;

// Director - another composite to demonstrate deeper hierarchies
public class Director extends Employee {
	private List<Employee> departments = new ArrayList<>();
	private String division;

	public Director(String name, double salary, String division) {
		super(name, "Director", salary);
		this.division = division;
	}

	public String getDivision() {
		return division;
	}

	@Override
	public void addSubordinate(Employee employee) {
		departments.add(employee);
	}

	@Override
	public void removeSubordinate(Employee employee) {
		departments.remove(employee);
	}

	@Override
	public List<Employee> getSubordinates() {
		return new ArrayList<>(departments);
	}

	@Override
	public double calculateTotalSalary() {
		double total = salary;
		for (Employee department : departments) {
			total += department.calculateTotalSalary();
		}
		return total;
	}

	@Override
	public void printDetails(int indent) {
		System.out.println(getIndent(indent) + "Director: " + name);
		System.out.println(getIndent(indent + 1) + "Position: " + position);
		System.out.println(getIndent(indent + 1) + "Division: " + division);
		System.out.println(getIndent(indent + 1) + "Salary: $" + String.format("%.2f", salary));
		System.out.println(getIndent(indent + 1) + "Division Size: " + getEmployeeCount());
		System.out
				.println(getIndent(indent + 1) + "Division Budget: $" + String.format("%.2f", calculateTotalSalary()));

		if (!departments.isEmpty()) {
			System.out.println(getIndent(indent + 1) + "Departments:");
			for (Employee department : departments) {
				department.printDetails(indent + 2);
			}
		}
	}

	@Override
	public int getEmployeeCount() {
		int count = 1;
		for (Employee department : departments) {
			count += department.getEmployeeCount();
		}
		return count;
	}
}
