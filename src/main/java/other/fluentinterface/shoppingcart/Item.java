package other.fluentinterface.shoppingcart;

public record Item(String name, double price, int quantity) {

	double total() {
		return price * quantity;
	}
}
