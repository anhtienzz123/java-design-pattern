package creational.factorymethod.logistics;

// Creator
public abstract class Logistics {

	// Factory method
	abstract Transport createTransport();

	// Business logic using the Product
	public void planDelivery() {
		Transport transport = this.createTransport();
		transport.deliver();
	}
}
