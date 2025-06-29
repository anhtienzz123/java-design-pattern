package structural.composite.organizationhierarchy;

// Leaf
public class Developer extends Employee {
	private String programmingLanguage;

	public Developer(String name, double salary, String programmingLanguage) {
		super(name, "Developer", salary);
		this.programmingLanguage = programmingLanguage;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	@Override
	public double calculateTotalSalary() {
		return getSalary();
	}

	@Override
	public void printDetails(int indent) {
		System.out.println(getIndent(indent) + "Developer: " + name);
		System.out.println(getIndent(indent + 1) + "Position: " + position);
		System.out.println(getIndent(indent + 1) + "Salary: $" + String.format("%.2f", salary));
		System.out.println(getIndent(indent + 1) + "Language: " + programmingLanguage);
	}

	@Override
	public int getEmployeeCount() {
		return 1;
	}
}
