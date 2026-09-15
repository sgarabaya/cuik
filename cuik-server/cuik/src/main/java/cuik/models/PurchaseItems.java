package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PurchaseItems extends BaseEntity {
    public UUID menuItemId;
    public double price;
    public double units;

    public PurchaseItems() {
    }

    public PurchaseItems(ResultSet resultSet) throws SQLException {
        super(resultSet);

        menuItemId = UUID.fromString(resultSet.getString("menuItemId"));
        price = resultSet.getDouble("price");
        units = resultSet.getDouble("units");
    }
}