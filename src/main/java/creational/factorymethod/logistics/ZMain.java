package creational.factorymethod.logistics;

public class ZMain {

	public static void main(String[] args) {
		// Client chooses RoadLogistics
		Logistics roadLogistics = new RoadLogistics();
		roadLogistics.planDelivery(); // output: Delivering by land in a box

		// Client chooses SeaLogistics
		Logistics seaLogistics = new SeaLogistics();
		seaLogistics.planDelivery(); // output: Delivering by sea in a container
	}
}
