package creational.simplefactory.vehicle;

public class ZMain {

	public static void main(String[] args) {
		VehicleFactory vehicleFactory = new VehicleFactory();

		Vehicle car = vehicleFactory.createVehicle(VehicleType.CAR);
		car.drive(); // output: Driving a car

		Vehicle bike = vehicleFactory.createVehicle(VehicleType.BIKE);
		bike.drive(); // output: Riding a bike

	}
}
