package respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class JdbcUserRepository implements UserRepository {
    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public JdbcUserRepository(String url, String user, String password) {
        this.url = url;
        this.dbUser = user;
        this.dbPassword = password;
        // In a real app, you would initialize the database table here if it doesn't
        // exist.
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }

    @Override
    public Optional<User> findById(long id) {
        // Implementation for finding a user via JDBC
        // try-with-resources ensures the connection is closed
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(rs.getLong("id"), rs.getString("username"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper logging should be used here
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        // Implementation for saving a user via JDBC (handling both INSERT and UPDATE)
        // ...
        System.out.println("Saved user to database (JDBC): " + user);
    }

    @Override
    public void deleteById(long id) {
        // Implementation for deleting a user via JDBC
        // ...
        System.out.println("Deleted user with id " + id + " from database (JDBC).");
    }
}