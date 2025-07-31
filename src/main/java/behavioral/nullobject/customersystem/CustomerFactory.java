package behavioral.nullobject.customersystem;

import java.util.Set;

public final class CustomerFactory {

	private CustomerFactory() {
	}

	private static final Set<String> CUSTOMERS = Set.of("customer1", "customer2");
	private static final Customer NULL_CUSTOMER = new NullCustomer();

	public static Customer getCustomer(String name) {
		if (name == null || name.isBlank()) {
			return NULL_CUSTOMER;
		}

		return CUSTOMERS.contains(name) ? new RealCustomer(name) : NULL_CUSTOMER;
	}
}
