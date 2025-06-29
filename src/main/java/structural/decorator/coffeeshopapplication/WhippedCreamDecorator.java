package structural.decorator.coffeeshopapplication;

// Concrete Decorator: Adds whipped cream
public class WhippedCreamDecorator extends CoffeeDecorator {
	public WhippedCreamDecorator(Coffee coffee) {
		super(coffee);
	}

	@Override
	public String getDescription() {
		return decoratedCoffee.getDescription() + ", Whipped Cream";
	}

	@Override
	public double getCost() {
		return decoratedCoffee.getCost() + 0.70; // Add whipped cream cost
	}
}
