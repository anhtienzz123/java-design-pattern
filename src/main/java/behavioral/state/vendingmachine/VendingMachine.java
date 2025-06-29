package behavioral.state.vendingmachine;

//Context: The vending machine that delegates behavior to the current state
public class VendingMachine {
	private VendingMachineState state;
	private int inventory;

	// States
	private final VendingMachineState noCoinState = new NoCoinState();
	private final VendingMachineState hasCoinState = new HasCoinState();
	private final VendingMachineState soldState = new SoldState();
	private final VendingMachineState soldOutState = new SoldOutState();

	public VendingMachine(int inventory) {
		this.inventory = inventory;
		this.state = inventory > 0 ? noCoinState : soldOutState; // Initial state
		System.out.println("Vending machine initialized with " + inventory + " items");
	}

	// Delegate actions to the current state
	public void insertCoin() {
		state.insertCoin(this);
	}

	public void ejectCoin() {
		state.ejectCoin(this);
	}

	public void pressDispense() {
		state.pressDispense(this);
	}

	// State transition method
	public void setState(VendingMachineState state) {
		this.state = state;
		System.out.println("State changed to: " + state.getClass().getSimpleName());
	}

	// Inventory management
	public int getInventory() {
		return inventory;
	}

	public void dispenseItem() {
		if (inventory > 0) {
			inventory--;
			System.out.println("Item dispensed. Inventory remaining: " + inventory);
		}
	}

	// Access to states for transitions
	public VendingMachineState getNoCoinState() {
		return noCoinState;
	}

	public VendingMachineState getHasCoinState() {
		return hasCoinState;
	}

	public VendingMachineState getSoldState() {
		return soldState;
	}

	public VendingMachineState getSoldOutState() {
		return soldOutState;
	}
}
