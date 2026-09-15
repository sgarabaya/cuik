package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Purchase extends BaseEntity {
    public UUID userId;
    public UUID tableId;
    public UUID approverEmployeeId;
    public LocalDateTime delivered;

    public Purchase() {
    }

    public Purchase(ResultSet resultSet) throws SQLException {
        super(resultSet);
        userId = UUID.fromString(resultSet.getString("userId"));
        tableId = UUID.fromString(resultSet.getString("tableId"));
        approverEmployeeId = UUID.fromString(resultSet.getString("approverEmployeeId"));
        delivered = resultSet.getTimestamp("delivered").toLocalDateTime();
    }
}