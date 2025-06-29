package structural.decorator.coffeeshopapplication;

// Concrete Decorator: Adds milk
public class MilkDecorator extends CoffeeDecorator {
	public MilkDecorator(Coffee coffee) {
		super(coffee);
	}

	@Override
	public String getDescription() {
		return decoratedCoffee.getDescription() + ", Milk";
	}

	@Override
	public double getCost() {
		return decoratedCoffee.getCost() + 0.50; // Add milk cost
	}
}
