package behavioral.state.vendingmachine;

// Concrete State: Item sold (temporary state after dispensing)
public class SoldState implements VendingMachineState {
	@Override
	public void insertCoin(VendingMachine context) {
		System.out.println("Please wait, dispensing item");
	}

	@Override
	public void ejectCoin(VendingMachine context) {
		System.out.println("Cannot eject coin, item already dispensed");
	}

	@Override
	public void pressDispense(VendingMachine context) {
		System.out.println("Item already dispensed");
		if (context.getInventory() > 0) {
			context.setState(context.getNoCoinState());
		} else {
			context.setState(context.getSoldOutState());
		}
	}
}
