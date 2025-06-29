package creational.builder.car;

//Director: CarDirector
public class CarDirector {
	// Construct a basic car
	public Car constructBasicCar() {
		return new Car.Builder("Sedan", "Blue").engine("2.0L").build();
	}

	// Construct a luxury car
	public Car constructLuxuryCar() {
		return new Car.Builder("SUV", "Black").engine("3.5L").hasSunroof(true).build();
	}
}