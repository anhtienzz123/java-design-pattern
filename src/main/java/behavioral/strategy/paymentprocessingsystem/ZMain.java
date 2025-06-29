package behavioral.strategy.paymentprocessingsystem;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create context
		ShoppingCart cart = new ShoppingCart();

		// Add items to cart
		cart.addItem(50.0);
		cart.addItem(30.0);

		// Strategy 1: Pay with credit card
		System.out.println("\nCheckout with Credit Card:");
		PaymentStrategy creditCard = new CreditCardPayment("1234567890123456", "John Doe");
		cart.setPaymentStrategy(creditCard);
		cart.checkout();

		// Strategy 2: Pay with PayPal
		System.out.println("\nCheckout with PayPal:");
		PaymentStrategy payPal = new PayPalPayment("john.doe@example.com");
		cart.setPaymentStrategy(payPal);
		cart.checkout();

		// Strategy 3: Pay with cryptocurrency
		System.out.println("\nCheckout with Cryptocurrency:");
		PaymentStrategy crypto = new CryptoPayment("1A2b3C4d5E6f7G8h9J");
		cart.setPaymentStrategy(crypto);
		cart.checkout();

		// Test without strategy
		System.out.println("\nCheckout without strategy:");
		cart.setPaymentStrategy(null);
		cart.checkout();
		
//		== Ouput: 
//		Added item worth 50.0. Total: 50.0
//		Added item worth 30.0. Total: 80.0
//
//		Checkout with Credit Card:
//		Payment strategy set to: CreditCardPayment
//		Paid 80.0 using Credit Card (John Doe, Card: ****3456)
//
//		Checkout with PayPal:
//		Payment strategy set to: PayPalPayment
//		Paid 80.0 using PayPal (john.doe@example.com)
//
//		Checkout with Cryptocurrency:
//		Payment strategy set to: CryptoPayment
//		Paid 80.0 using Cryptocurrency (Wallet: 1A2b3C4d5E6f7G8h9J)
//
//		Checkout without strategy:
//		Exception in thread "main" java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "paymentStrategy" is null
//			at designpattern.behavioral.strategy.paymentprocessingsystem.ShoppingCart.setPaymentStrategy(ShoppingCart.java:14)
//			at designpattern.behavioral.strategy.paymentprocessingsystem.Main.main(Main.java:33)
	}
}
