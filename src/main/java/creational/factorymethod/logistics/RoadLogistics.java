package creational.factorymethod.logistics;

// ConcreteCreator
public class RoadLogistics extends Logistics {

	@Override
	Transport createTransport() {
		return new Truck();
	}
}
