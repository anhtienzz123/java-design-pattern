package behavioral.state.vendingmachine;

// Concrete State: Coin inserted
public class HasCoinState implements VendingMachineState {
	@Override
	public void insertCoin(VendingMachine context) {
		System.out.println("Coin already inserted");
	}

	@Override
	public void ejectCoin(VendingMachine context) {
		System.out.println("Coin ejected");
		context.setState(context.getNoCoinState());
	}

	@Override
	public void pressDispense(VendingMachine context) {
		if (context.getInventory() > 0) {
			context.dispenseItem();
			context.setState(context.getSoldState());
		} else {
			System.out.println("Out of items");
			context.setState(context.getSoldOutState());
		}
	}
}
