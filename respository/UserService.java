package respository;

import java.util.List;

public class UserService {
    // The service depends on the abstraction (interface), not the concrete
    // implementation.
    private final UserRepository userRepository;

    // The concrete repository is "injected" via the constructor.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerNewUser(String username, String email) {
        System.out.println("Attempting to register user: " + username);
        // Business logic here (e.g., check if username is taken, validate email)
        User newUser = new User(0, username, email); // ID is 0 for a new user
        userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
