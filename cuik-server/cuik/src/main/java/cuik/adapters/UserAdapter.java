package cuik.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cuik.adapters.sql.SqlClient;
import cuik.models.User;

public class UserAdapter {
    protected String table = "Users";
    private SqlClient client;

    public UserAdapter(SqlClient client) {
        this.client = client;
    }

    public void create(User user) {
        try {
            if (user.id == null)
                user.id = UUID.randomUUID();

            client.exec("INSERT INTO Users (id, name, email, passwordHash) VALUES (?, ?, ?, ?);",
                    user.id.toString(),
                    user.name,
                    user.email,
                    user.passwordHash);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User getById(String id) {
        try {
            return client.read(User.class, "Users", UUID.fromString(id));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public User findByUsername(String username) {
        try {
            return client.readBy(User.class, "Users", "name", username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<User> getAll() {
        try {
            var client = new SqlClient();

            client.connect();
            var users = client.query(User.class, "SELECT * FROM Users");

            return users;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return new ArrayList<>();
    }
}
