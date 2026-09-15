package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import cuik.adapters.sql.Mappable;

public abstract class BaseEntity extends Mappable {
    public UUID id;
    public LocalDateTime created;
    public LocalDateTime updated;

    public BaseEntity() {
    }

    public BaseEntity(ResultSet resultSet) throws SQLException {
        super(resultSet);

        id = UUID.fromString(resultSet.getString("id"));
        created = resultSet.getTimestamp("created").toLocalDateTime();
        updated = resultSet.getTimestamp("updated").toLocalDateTime();
    }
}
