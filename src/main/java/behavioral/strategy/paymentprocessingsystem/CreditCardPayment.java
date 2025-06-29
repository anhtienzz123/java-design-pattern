package behavioral.strategy.paymentprocessingsystem;

// Concrete Strategy: Credit card payment
public class CreditCardPayment implements PaymentStrategy {
	private String cardNumber;
	private String cardHolder;

	public CreditCardPayment(String cardNumber, String cardHolder) {
		this.cardNumber = cardNumber;
		this.cardHolder = cardHolder;
	}

	@Override
	public void pay(double amount) {
		System.out.println("Paid " + amount + " using Credit Card (" + cardHolder + ", Card: ****"
				+ cardNumber.substring(cardNumber.length() - 4) + ")");
	}
}
