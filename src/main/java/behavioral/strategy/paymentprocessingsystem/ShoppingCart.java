package behavioral.strategy.paymentprocessingsystem;

// Context: The shopping cart that uses a payment strategy
public class ShoppingCart {
	private PaymentStrategy paymentStrategy;
	private double totalAmount;

	public ShoppingCart() {
		this.totalAmount = 0.0;
	}

	public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
		this.paymentStrategy = paymentStrategy;
		System.out.println("Payment strategy set to: " + paymentStrategy.getClass().getSimpleName());
	}

	public void addItem(double price) {
		totalAmount += price;
		System.out.println("Added item worth " + price + ". Total: " + totalAmount);
	}

	public void checkout() {
		if (paymentStrategy == null) {
			System.out.println("No payment strategy selected");
			return;
		}
		paymentStrategy.pay(totalAmount);
	}
}
