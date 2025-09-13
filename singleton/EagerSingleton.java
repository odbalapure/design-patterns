package singleton;

public class EagerSingleton {
    // We rely on JVM to create the singleton instance when the class is loaded. The
    // JVM guarantees that the instance will be created before any thread access the
    // instance variable.
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return instance;
    }
}
