package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TableWaiter extends BaseEntity {
    public UUID employeeId;
    public UUID tableId;

    public TableWaiter() {
    }

    public TableWaiter(ResultSet resultSet) throws SQLException {
        employeeId = UUID.fromString(resultSet.getString("employeeId"));
        tableId = UUID.fromString(resultSet.getString("tableId"));
    }
}
