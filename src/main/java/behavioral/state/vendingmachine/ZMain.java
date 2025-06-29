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

//		== Output: 
//		Vending machine initialized with 2 items
//
//		Test 1: No coin inserted
//		Insert a coin first
//		Coin inserted
//		State changed to: HasCoinState
//
//		Test 2: Coin inserted
//		Coin already inserted
//		Item dispensed. Inventory remaining: 1
//		State changed to: SoldState
//
//		Test 3: After dispensing
//		Please wait, dispensing item
//		Item already dispensed
//		State changed to: NoCoinState
//
//		Test 4: Dispense last item
//		Coin inserted
//		State changed to: HasCoinState
//		Item dispensed. Inventory remaining: 0
//		State changed to: SoldState
//		Item already dispensed
//		State changed to: SoldOutState
//
//		Test 5: Sold out
//		Cannot insert coin, machine is sold out
//		Machine is sold out
	}
}
