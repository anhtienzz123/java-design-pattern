package other.fluentinterface.shoppingcart;

import java.util.ArrayList;
import java.util.List;

// Fluent Interface on a live object: every method changes the cart and returns "this"
public class ShoppingCart {
	private final List<Item> items = new ArrayList<>();
	private double discountPercent;

	public ShoppingCart add(String name, double price, int quantity) {
		items.add(new Item(name, price, quantity));
		System.out.println("Added   " + quantity + " x " + name);
		return this;
	}

	public ShoppingCart remove(String name) {
		items.removeIf(item -> item.name().equals(name));
		System.out.println("Removed " + name);
		return this;
	}

	public ShoppingCart applyDiscount(double percent) {
		this.discountPercent = percent;
		System.out.println("Discount " + percent + "% applied");
		return this;
	}

	public ShoppingCart printReceipt() {
		System.out.println("--- Receipt ---");
		items.forEach(item -> System.out.printf("%-10s %d x %6.2f = %7.2f%n",
				item.name(), item.quantity(), item.price(), item.total()));
		System.out.printf("Total: %.2f%n", total());
		return this;
	}

	// Ends the chain by returning a value instead of "this"
	public double total() {
		double sum = items.stream().mapToDouble(Item::total).sum();
		return sum * (1 - discountPercent / 100);
	}
}
