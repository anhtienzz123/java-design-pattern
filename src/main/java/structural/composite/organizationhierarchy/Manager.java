package structural.composite.organizationhierarchy;

import java.util.ArrayList;
import java.util.List;

// Composite
public class Manager extends Employee {
	private List<Employee> subordinates = new ArrayList<>();
	private String department;

	public Manager(String name, double salary, String department) {
		super(name, "Manager", salary);
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	@Override
	public void addSubordinate(Employee employee) {
		subordinates.add(employee);
	}

	@Override
	public void removeSubordinate(Employee employee) {
		subordinates.remove(employee);
	}

	@Override
	public List<Employee> getSubordinates() {
		return new ArrayList<>(subordinates);
	}

	@Override
	public double calculateTotalSalary() {
		double total = salary;
		for (Employee employee : subordinates) {
			total += employee.calculateTotalSalary();
		}
		return total;
	}

	@Override
	public void printDetails(int indent) {
		System.out.println(getIndent(indent) + "Manager: " + name);
		System.out.println(getIndent(indent + 1) + "Position: " + position);
		System.out.println(getIndent(indent + 1) + "Department: " + department);
		System.out.println(getIndent(indent + 1) + "Salary: $" + String.format("%.2f", salary));
		System.out.println(getIndent(indent + 1) + "Team Size: " + getEmployeeCount());
		System.out.println(getIndent(indent + 1) + "Team Budget: $" + String.format("%.2f", calculateTotalSalary()));

		if (!subordinates.isEmpty()) {
			System.out.println(getIndent(indent + 1) + "Team Members:");
			for (Employee employee : subordinates) {
				employee.printDetails(indent + 2);
			}
		}
	}

	@Override
	public int getEmployeeCount() {
		// Count this manager plus all subordinates (recursive)
		int count = 1;
		for (Employee employee : subordinates) {
			count += employee.getEmployeeCount();
		}
		return count;
	}
}