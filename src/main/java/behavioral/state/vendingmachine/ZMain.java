package behavioral.state.vendingmachine;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Create vending machine with 2 items
		VendingMachine machine = new VendingMachine(2);

		// Test NoCoinState
		System.out.println("\nTest 1: No coin inserted");
		machine.pressDispense(); // Should fail
		machine.insertCoin(); // Transition to HasCoinState

		// Test HasCoinState
		System.out.println("\nTest 2: Coin inserted");
		machine.insertCoin(); // Should warn
		machine.pressDispense(); // Dispense item, transition to SoldState

		// Test SoldState
		System.out.println("\nTest 3: After dispensing");
		machine.insertCoin(); // Should warn
		machine.pressDispense(); // Transition to NoCoinState

		// Test dispensing last item
		System.out.println("\nTest 4: Dispense last item");
		machine.insertCoin();
		machine.pressDispense(); // Dispense item, transition to SoldState
		machine.pressDispense(); // Transition to SoldOutState

		// Test SoldOutState
		System.out.println("\nTest 5: Sold out");
		machine.insertCoin(); // Should fail
		machine.pressDispense(); // Should fail
	}
}
