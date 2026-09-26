package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends BaseEntity {

    public String name;
    public String email;
    public String passwordHash;

    public User() {}

    public User(ResultSet resultSet) throws SQLException {
        super(resultSet);
        name = resultSet.getString("name");
        email = resultSet.getString("email");
        passwordHash = resultSet.getString("passwordHash");
    }
}
