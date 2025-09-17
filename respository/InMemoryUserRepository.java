package respository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
