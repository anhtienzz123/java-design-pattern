package creational.builder.car;

public class ZMain {
	public static void main(String[] args) {
		// Using Builder directly
		Car car1 = new Car.Builder("Hatchback", "Red").engine("1.5L").hasSunroof(false).build();
		System.out.println("Direct Builder: " + car1);

		// Using Director for predefined configurations
		CarDirector director = new CarDirector();

		Car car2 = director.constructBasicCar();
		System.out.println("Basic Car (Director): " + car2);

		Car car3 = director.constructLuxuryCar();
		System.out.println("Luxury Car (Director): " + car3);
	}
}
