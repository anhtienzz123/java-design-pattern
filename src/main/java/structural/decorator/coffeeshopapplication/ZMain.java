package structural.decorator.coffeeshopapplication;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create a basic coffee
		Coffee basicCoffee = new BasicCoffee();
		System.out.println("Basic Coffee: " + basicCoffee.getDescription() + ", Cost: $" + basicCoffee.getCost());

		// Add milk
		Coffee milkCoffee = new MilkDecorator(basicCoffee);
		System.out.println("Milk Coffee: " + milkCoffee.getDescription() + ", Cost: $" + milkCoffee.getCost());

		// Add milk and sugar
		Coffee milkSugarCoffee = new SugarDecorator(new MilkDecorator(basicCoffee));
		System.out.println(
				"Milk + Sugar Coffee: " + milkSugarCoffee.getDescription() + ", Cost: $" + milkSugarCoffee.getCost());

		// Add milk, sugar, and whipped cream
		Coffee deluxeCoffee = new WhippedCreamDecorator(new SugarDecorator(new MilkDecorator(basicCoffee)));
		System.out.println("Deluxe Coffee: " + deluxeCoffee.getDescription() + ", Cost: $" + deluxeCoffee.getCost());
		
//		== Output: 
//		Basic Coffee: Basic Coffee, Cost: $2.0
//		Milk Coffee: Basic Coffee, Milk, Cost: $2.5
//		Milk + Sugar Coffee: Basic Coffee, Milk, Sugar, Cost: $2.7
//		Deluxe Coffee: Basic Coffee, Milk, Sugar, Whipped Cream, Cost: $3.4000000000000004
	}
}
