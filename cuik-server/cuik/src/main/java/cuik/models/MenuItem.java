package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import cuik.utilities.Transform;

public class MenuItem extends BaseEntity {
    public UUID menuCategoryId;
    public String description;
    public String[] allergens; // TODO: Mapear manualmente
    public String[] ingredients; // TODO: Mapear manualmente
    public double price;
    public double unit;
    public boolean available;

    public MenuItem() {
    }

    public MenuItem(ResultSet resultSet) throws SQLException {
        super(resultSet);
        menuCategoryId = Transform.bytesToUUID(resultSet.getBytes("menuCategoryId"));
        description = resultSet.getString("description");

        price = resultSet.getDouble("price");
        unit = resultSet.getDouble("unit");
        available = resultSet.getBoolean("available");
    }
}