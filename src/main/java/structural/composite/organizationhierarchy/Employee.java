package structural.composite.organizationhierarchy;

import java.util.List;

// Component
public abstract class Employee {
	protected String name;
	protected String position;
	protected double salary;

	public Employee(String name, String position, double salary) {
		this.name = name;
		this.position = position;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public double getSalary() {
		return salary;
	}

	// Common operations for all employees
	public abstract double calculateTotalSalary();

	public abstract void printDetails(int indent);

	public abstract int getEmployeeCount(); // Get total number of employees

	// Default methods for composite operations
	public void addSubordinate(Employee employee) {
		throw new UnsupportedOperationException("Cannot add subordinate to " + getClass().getSimpleName());
	}

	public void removeSubordinate(Employee employee) {
		throw new UnsupportedOperationException("Cannot remove subordinate from " + getClass().getSimpleName());
	}

	public List<Employee> getSubordinates() {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " doesn't have subordinates");
	}

	// Helper method for indentation
	protected String getIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append("    ");
		}
		return sb.toString();
	}
}
