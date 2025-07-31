package behavioral.nullobject.customersystem;

public class ZMain {

	public static void main(String[] args) {
		Customer customer1 = CustomerFactory.getCustomer("customer1");
		Customer customer2 = CustomerFactory.getCustomer("customer2");
		Customer customer3 = CustomerFactory.getCustomer("customer3");
		
		System.out.println("customer1: " + customer1.getName());
		System.out.println("customer2: " + customer2.getName());
		System.out.println("customer3: " + customer3.getName());
	}
}
