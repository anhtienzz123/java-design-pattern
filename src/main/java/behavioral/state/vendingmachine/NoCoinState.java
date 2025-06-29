package behavioral.state.vendingmachine;

// Concrete State: No coin inserted
public class NoCoinState implements VendingMachineState {
	@Override
	public void insertCoin(VendingMachine context) {
		System.out.println("Coin inserted");
		context.setState(context.getHasCoinState());
	}

	@Override
	public void ejectCoin(VendingMachine context) {
		System.out.println("No coin to eject");
	}

	@Override
	public void pressDispense(VendingMachine context) {
		System.out.println("Insert a coin first");
	}
}
