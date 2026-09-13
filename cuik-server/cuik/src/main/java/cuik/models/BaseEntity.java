package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import cuik.adapters.sql.Mappable;
import cuik.utilities.Transform;

public abstract class BaseEntity extends Mappable {
    public UUID id;
    public LocalDateTime created;
    public LocalDateTime updated;

    public BaseEntity() {
    }

    public BaseEntity(ResultSet resultSet) throws SQLException {
        super(resultSet);
        id = Transform.bytesToUUID(resultSet.getBytes("id"));
        created = resultSet.getTimestamp("created").toLocalDateTime();
        updated = resultSet.getTimestamp("updated").toLocalDateTime();
    }
}

// class MenuItemImage extends BaseEntity {
// public UUID menuItemId;
// public String url;
// }

// class NutritionalFact extends BaseEntity {
// public UUID menuItemId;
// public String description;
// public double value;
// public String unit;
// }

// class TableWaiter {
// public UUID employeeId;
// public UUID tableId;
// }

// class Table extends BaseEntity {
// public boolean available;
// public int capacity;
// public boolean enabled;
// }

// class PurchaseItems extends BaseEntity {
// public UUID menuItemId;
// public double price;
// public double units;
// }

// class Purchase extends BaseEntity {
// public UUID userId;
// public UUID mesaId;
// public UUID approverEmployeeId;
// public LocalDateTime delivered;
// }
