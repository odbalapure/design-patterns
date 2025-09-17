## Repository

Imagine application's business logic is busy, high end kitchen. We have chefs (services and use case) who need specific ingredients (data) to create their culinary masterpieces (features).

Imagine if chef had to run to a chaotic, disorganized farm (the database) everytime they needed a single ingredient. They'd have to know exactly where to find it, how to harvest, how to prepare it. The kitchen would grind to a halt. It would be inefficient, messy, incredibly difficult to change anything.

What if we wanted to switch from that farm to local market?

## What is it

This pattern mediates b/w domain and data mapping layers, acting like an in-memory collection of domain objects. It decouples application's business logic from the underlying data source, making code cleaner, more maintanable, much easier to test.

It acts as kitchen well organized pantry. The pantry staff (repository) knows exactly where every ingredient comes from, whether its a farm, local market, hydroponic garden. The chef simply asks the pantry for "3 red tomatoes" and pantry delivers them. The chef don't need to know or care where tomatoes came from; they just trust the pantry to provide them.

## Why use it

The primary goal is to create separation of concerns. By abstracting data access logic, we have several advantages:

- **Decoupling**: Business logic (the "domain") is completely isolated from the data access technology (the "data mapping layer"). Our services don't need to know if we are using JPA, JDBC, NoSQL or even memory list. This means the database/ORM framework can be switched with minimal changes to core application logic.
- **Centralized data access logic**: All the logic for querying and persisting data is located in one place -- the repository. This avoids scattering database queries all over the application, making it easier to manage, optimize and debug. Eg: If we wish to change how data is fetched, we only have to do in it one class.
- **Improved testability**: This is one of the biggest wins. Since business logic depends on a repository interface, we can easily create a "mock" or "fake" repository for unit tests. This mock repository can simulate the database using simple in-memory collection, making tests incredibly fast, reliable, free from external dependencies like a running database.
- **Enhacned readability**: Our business logic becomes much cleaner. Instead of being cluttered with SQL queries or `EntityManager` calls, it reads more like a clear set of instructions, eg: `userRepository.findById(1)` or `productRepository.save(newProduct)`.

## Core components

- **Domain model (entity)**: This is the object the data we working with (eg: `User`, `Product`, `Order` class). Its a POJO that holds state of our business entity.
- **Repository interface**: This is the contract. It defines the set of operations that can be performed on the domain model -- such as `findById`, `save`, `findAll`. The business logic will code against the interface, not concrete implementations.
- **Concrete repository**: This is the actual implementation of the repository interface. This contains specific data access logic. We might have a `JdbcUserRepository` for SQL and `MongoUserRepository` for MongoDB or an `InMemoryUserRepository` for testing.
- **The Client (Business logic)**: This the part of your application that needs the data, such as `UserService`. It holds a reference to the repository interface and uses it to fetch and store domain objects.

## Example

Building a system to manage user entities. We'll create a respository to handle the persistence and retrieval of `User` objects.

```java
public class User {
    private long id;
    private String username;
    private String email;

    public User(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // ... Getters and Setters
}
```

Repository Interface is the contract that our business logic will depend on. It defines standard CRUD operations.

```java
public interface UserRepository {
    /**
     * Finds a user by their unique ID.
     * @param id The ID of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findById(long id);

    /**
     * Retrieves all users from the data source.
     * @return A list of all users.
     */
    List<User> findAll();

    /**
     * Saves a user entity. If the user is new, it's created.
     * If the user exists, it's updated.
     * @param user The user object to save.
     */
    void save(User user);

    /**
     * Deletes a user by their unique ID.
     * @param id The ID of the user to delete.
     */
    void deleteById(long id);
}
```

Now, create classes that will implement the interface. Eg: In-memory repository for testing that uses a HashMap to store user entities. It is perfect for testing as its fast and has no external dependencies.

```java
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> userStore = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(userStore.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            // New user, generate an ID
            long newId = sequence.incrementAndGet();
            user.setId(newId);
        }
        userStore.put(user.getId(), user);
        System.out.println("Saved user to in-memory store: " + user);
    }

    @Override
    public void deleteById(long id) {
        userStore.remove(id);
        System.out.println("Deleted user with id " + id + " from in-memory store.");
    }
}
```

The JDBC repository will contain the logic to interact with the SQL database using JDBC.
