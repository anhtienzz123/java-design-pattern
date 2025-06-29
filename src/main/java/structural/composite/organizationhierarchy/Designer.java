package structural.composite.organizationhierarchy;

// Another Leaf
public class Designer extends Employee {
	private String designSpecialty;

	public Designer(String name, double salary, String designSpecialty) {
		super(name, "Designer", salary);
		this.designSpecialty = designSpecialty;
	}

	public String getDesignSpecialty() {
		return designSpecialty;
	}

	@Override
	public double calculateTotalSalary() {
		return getSalary();
	}

	@Override
	public void printDetails(int indent) {
		System.out.println(getIndent(indent) + "Designer: " + name);
		System.out.println(getIndent(indent + 1) + "Position: " + position);
		System.out.println(getIndent(indent + 1) + "Salary: $" + String.format("%.2f", salary));
		System.out.println(getIndent(indent + 1) + "Specialty: " + designSpecialty);
	}

	@Override
	public int getEmployeeCount() {
		return 1;
	}
}
