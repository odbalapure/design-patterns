package singleton;

public class LazySingleton {
    // Single instances, initially null
    private static LazySingleton instance;

    // Private constructor to prevent instantiation
    private LazySingleton() {
    }

    // Public method to get the instance
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}
