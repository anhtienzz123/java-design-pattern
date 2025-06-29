package behavioral.state.vendingmachine;

// Concrete State: No items left
public class SoldOutState implements VendingMachineState {
	@Override
	public void insertCoin(VendingMachine context) {
		System.out.println("Cannot insert coin, machine is sold out");
	}

	@Override
	public void ejectCoin(VendingMachine context) {
		System.out.println("No coin to eject");
	}

	@Override
	public void pressDispense(VendingMachine context) {
		System.out.println("Machine is sold out");
	}
}
