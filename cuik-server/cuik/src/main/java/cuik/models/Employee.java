package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee extends User {
    public boolean isAdmin;

    public Employee() {
    }

    public Employee(ResultSet resultSet) throws SQLException {
        super(resultSet);
        isAdmin = resultSet.getBoolean("isAdmin");
    }
}
