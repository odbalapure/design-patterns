package state;

// Each state will implement this interface, defining how the machine should behave when its state changes.
public interface MachineState {
    void selectItem(VendingMachine context, String itemCode);

    void insertCoin(VendingMachine context, double amount);

    void dispenseItem(VendingMachine context);
}
