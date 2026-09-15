package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table extends BaseEntity {
    public boolean available;
    public int capacity;
    public boolean enabled;

    public Table() {
    }

    public Table(ResultSet resultSet) throws SQLException {
        super(resultSet);
        available = resultSet.getBoolean("available");
        capacity = resultSet.getInt("capacity");
        enabled = resultSet.getBoolean("enabled");
    }

}