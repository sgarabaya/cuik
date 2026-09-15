package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends BaseEntity {
    public String username;
    public String passwordHash;
    public String email;

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
        super(resultSet);
        username = resultSet.getString("username");
        passwordHash = resultSet.getString("passwordHash");
        email = resultSet.getString("email");
    }
}
