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

//		== Ouput: 
//		Direct Builder: Car [model=Hatchback, color=Red, engine=1.5L, hasSunroof=false]
//		Basic Car (Director): Car [model=Sedan, color=Blue, engine=2.0L, hasSunroof=false]
//		Luxury Car (Director): Car [model=SUV, color=Black, engine=3.5L, hasSunroof=true]
	}
}
