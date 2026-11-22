package other.fluentinterface.shoppingcart;

public class ZMain {
	public static void main(String[] args) {
		// Without a fluent interface:
		//   cart.add(...); cart.add(...); cart.remove(...); cart.applyDiscount(...);
		// With it, the calls read like one sentence:
		double total = new ShoppingCart()
				.add("Coffee", 4.50, 2)
				.add("Croissant", 3.00, 3)
				.add("Juice", 5.00, 1)
				.remove("Juice")
				.applyDiscount(10)
				.printReceipt()
				.total();

		System.out.printf("%nAmount to pay: %.2f%n", total);
	}
}
