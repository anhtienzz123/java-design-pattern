package creational.simplefactory.vehicle;

// SimpleFactory
public class VehicleFactory {

	public Vehicle createVehicle(VehicleType vehicleType) {
		return switch (vehicleType) {
		case VehicleType.CAR -> new Car();
		case VehicleType.BIKE -> new Bike();
		};
	}
}
