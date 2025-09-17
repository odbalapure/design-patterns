package respository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    /**
     * Finds a user by their unique ID.
     * 
     * @param id The ID of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findById(long id);

    /**
     * Retrieves all users from the data source.
     * 
     * @return A list of all users.
     */
    List<User> findAll();

    /**
     * Saves a user entity. If the user is new, it's created.
     * If the user exists, it's updated.
     * 
     * @param user The user object to save.
     */
    void save(User user);

    /**
     * Deletes a user by their unique ID.
     * 
     * @param id The ID of the user to delete.
     */
    void deleteById(long id);
}
