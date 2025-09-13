## Singleton Design Pattern

We often require classes that can only have one object, eg: thread pools, caches, loggers etc.

Creating more than one object of these could lead to issues such as incorrect program behavior, overuse of resources, or inconsistent results.

> Sinlgeton pattern guarantees a class has only one instance and provides a global point of access to it.

- Involves only one class that instantiates itself, making sure it creates not more than one instance. 
- Prevent external objects from creating instances of the singletong class.
- Only the singleton class should be permitted to create its own objects.

## How to do it

Make the constructor private and provide a static method for external objects to access it.

There are several ways to implement it, each with is own pros and cons.

## Lazy initialization

This creates an instance only when its needed, saving resources if the singleton is never used in the application.

```java
public class LazySingleton {
    // Single instances, initially null
    private static LazySingleton instance;

    // Private constructor to prevent instantiation
    private LazySingleton() {}

    // Public method to get the instance
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}
```

> This implementation is not thread safe. If multiple threads call `getInstance()` simultaneously when `instance` is *null*, it's possible to create multiple instances.

## Thread safe singleton

Make the `getInstance()` method **synchronized** ensuring only one thread can execute this method at a time. When a thread enters the synchronized method, it acquires a lock on the class object. Other threads must wait until the method is executed.

```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {}

    public static synchronized ThreadSafeSingleton getInstace() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }

        return instance;
    }
}
```

> This apporach is straightforward but using `synchronized` can cause substantial overhead and reduce performance, which can be a bottleneck if called frequently.

## Double checked locking

This approach minimizes performance overhead from `synchronization` by only synchronizing when the object is first created.

It uses the `volatile` keyword to ensure that the changes to the instance variable are immediately visible to other threads.

```java
public class DoubleCheckSingleton {
    private static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }

        return instance;
    }
}
```

- If the first check (`instance == null`) passes, we synchronize on the class object.
- We check the same condition one more time because multiple threads may have passed the first check.
- The instance is created only if both checks pass.

> Although a bit complex, this can drastically reduce the performance overhead.

## Eager initialization

We rely on JVM to create the singleton instance when the class is loaded. The JVM guarantees that the instance will be created before any thread access the instance variable.

This is simple and inherently thread-safe w/o needing explicit synchronization.

```java
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
```

- `static` variable ensures there is only one instance shared across all instances of the class.
- `final` prevents the instance from being reassigned from initialization.

NOTE: This approach is suitable if your application always creates and uses the singleton instance, or the overhead of creating it is minimal.

> While this is inherently thread-safe, it could potentially waste resources if the singleton instance is nvever used by the client application.

## Bill Pugh singleton

Uses a static helper inner class to hold the singleton instance. The inner class is not loaded into memory, until it is referenced for the first time in the `getInstance()` method.

```java
class BillPughSingleton {
    private BillPughSingleton() {}

    // Static inner class that holds the instance
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

- When the `getInstance()` method is called for the first time, it triggers the loading of the SingletonHelper class.
- When the inner class is loaded, it creates the **INSTANCE** of `BillPughSingleton`.
- The final keyword ensures that the INSTANCE cannot be reassigned.

> While more complex than Eager; provides a perfect balance of lazy initialization, thread safety and performance, w/o complexities of other patterns like double checked locking.


## Static block initialization

This is similar to Eager initialization, but the instance is created in static block. It provides the ability to handle exceptions during instance creation, which is not possible with simple Eager initialization.

```java
class StaticBlockSingleton {
    private static StaticBlockSingleton instance;

    private StaticBlockSingleton() {}

    // Static block for initialization
    // Ability to handle exceptions during instance creation 
    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    // Public method to get the instance
    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}
```

- The static block is executed when the class is loaded by the JVM.
- If an exception occurs, it's wrapped in a `RunTimeException`.

> This is thread safe but not lazy loaded, which might be a drawback if the initialization is resource intensive or time consuming.

## Enum singleton

Java ensures only instance of an enum value is created, even in multithreaded environment. This is the most robust and concise way to implement singleton.

```java
enum EnumSingleton {
    INSTANCE;

    public void doSomething() {}
}
```

Here, `doSomething()` could be `getConnection()` -- singleton manages the shared DB connection:

```java
enum EnumSingleton {
    INSTANCE;

    private Connection connection;

    EnumSingleton() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
```

**NOTE**: This is not always suitable especially if we need to extend a class or if lazy initialization is a strict requirement.

## Real world examples

### Use cases

Singleton is useful in scenarios like:
- Managing shared resources (database connections, thread pools, caches, configuration settings).
- Coordinating system wide actions (logging, file managers, print Spooler).
- Managing state (user session, application state).

### Speicifc Examples

- **Logger classes**: Many loggign framework use the Singleton pattern to provide a global logging object. This ensures that log messages are consistently handled and written to the same output stream.
- **Database connection pools**: Connection pools help manage and reuse database connections efficiently. A singleton can ensure that only one pool is created and used throughout the application.
- **Cache objects**: In-memory caches are often implemented as Singletons to provide a single point of access for cached data across the application.
- **File system**: Often use Singleton to represent the file system and provide a unified interface for all operations.
- **Print Spooler**: In an OS, spooler manage printing tasks. A single instance coordinates all print jobs to prevent conflicts.
