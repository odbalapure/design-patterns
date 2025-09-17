package respository;

public class RepositoryAppDemo {
    public static void main(String[] args) {
        System.out.println("--- Using In-Memory Repository (for testing/dev) ---");
        // Create an instance of the in-memory repository
        UserRepository inMemoryRepo = new InMemoryUserRepository();

        // Inject it into our service
        UserService memoryUserService = new UserService(inMemoryRepo);

        // Use the service
        memoryUserService.registerNewUser("alice", "alice@example.com");
        memoryUserService.registerNewUser("bob", "bob@example.com");

        System.out.println("All users: " + memoryUserService.getAllUsers());
        System.out.println("Found user 1: " + memoryUserService.findUser(1));

        System.out.println("\n--- Using JDBC Repository (for production) ---");
        // To run this, you'd need a database like H2 and its driver in your classpath.
        // UserRepository jdbcRepo = new JdbcUserRepository("jdbc:h2:mem:testdb", "sa",
        // "");
        // UserService prodUserService = new UserService(jdbcRepo);
        // prodUserService.registerNewUser("charlie", "charlie@example.com");
        // System.out.println("All users from DB: " + prodUserService.getAllUsers());
    }
}
