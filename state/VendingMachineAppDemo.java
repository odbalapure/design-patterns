package state;

public class VendingMachineAppDemo {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();

        System.out.println("\n--- First Transaction ---");

        vendingMachine.insertCoin(1.0); // Invalid in `IdleState`
        vendingMachine.selectItem("A1");
        vendingMachine.insertCoin(1.5);
        vendingMachine.dispenseItem();

        System.out.println("\n--- Second Transaction ---");
        vendingMachine.selectItem("B2");
        vendingMachine.insertCoin(2.0);
        vendingMachine.dispenseItem();
    }
}
