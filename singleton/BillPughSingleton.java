package singleton;

public class BillPughSingleton {
    private BillPughSingleton() {
    }

    // Static inner class that holds the reference
    // Inner class is not loaded into the memory until it's referenced for the first
    // time in the `getInstance()` method
    public static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
