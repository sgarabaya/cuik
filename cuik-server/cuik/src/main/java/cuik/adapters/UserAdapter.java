package cuik.adapters;

import java.util.ArrayList;
import java.util.List;

import cuik.adapters.sql.SqlClient;
import cuik.models.User;

public class UserAdapter {
    public User getById(String id) {
        try {
            var client = new SqlClient();

            client.connect();
            var users = client.query(User.class, "SELECT * FROM Users WHERE id = ?", id);

            return users.getFirst();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
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
