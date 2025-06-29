package behavioral.state.vendingmachine;

// State: Defines methods for state-specific behavior
public interface VendingMachineState {
	void insertCoin(VendingMachine context);

	void ejectCoin(VendingMachine context);

	void pressDispense(VendingMachine context);
}
