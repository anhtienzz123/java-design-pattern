package structural.decorator.coffeeshopapplication;

// Concrete Decorator: Adds sugar
public class SugarDecorator extends CoffeeDecorator {
	public SugarDecorator(Coffee coffee) {
		super(coffee);
	}

	@Override
	public String getDescription() {
		return decoratedCoffee.getDescription() + ", Sugar";
	}

	@Override
	public double getCost() {
		return decoratedCoffee.getCost() + 0.20; // Add sugar cost
	}
}