package singleton;

public class DoubleCheckSingleton {
    // Single instance, marked as volatile to ensure visibility across threads
    private static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        // First check; not synchronized for performance
        if (instance == null) {
            // Synchronize on the class to ensure only one thread can enter this block at a
            // time; multiple threads may have passed the first check
            synchronized (DoubleCheckSingleton.class) {
                // Second check; ensures instance is still null before creating
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }

        return instance;
    }
}
