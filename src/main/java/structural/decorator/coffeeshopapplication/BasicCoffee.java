package structural.decorator.coffeeshopapplication;

// Concrete Component: Basic coffee
public class BasicCoffee implements Coffee {
	@Override
	public String getDescription() {
		return "Basic Coffee";
	}

	@Override
	public double getCost() {
		return 2.00; // Base cost
	}
}