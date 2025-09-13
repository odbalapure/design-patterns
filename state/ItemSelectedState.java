package state;

public class ItemSelectedState implements MachineState {
    @Override
    public void selectItem(VendingMachine context, String itemCode) {
        System.out.println("Item already selected: " + itemCode);
    }

    @Override
    public void insertCoin(VendingMachine context, double amount) {
        System.out.println("Insert $" + amount + " for item: " + context.getSelectedItem());
        context.setInsertedAmount(amount);
        context.setState(new HasMoneyState());
    }

    @Override
    public void dispenseItem(VendingMachine context) {
        System.out.println("Insert coin before dispensing");
    }
}
