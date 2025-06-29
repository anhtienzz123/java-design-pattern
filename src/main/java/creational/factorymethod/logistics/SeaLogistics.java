package creational.factorymethod.logistics;

// ConCreteCreator
public class SeaLogistics extends Logistics {

	@Override
	Transport createTransport() {
		return new Ship();
	}
}
