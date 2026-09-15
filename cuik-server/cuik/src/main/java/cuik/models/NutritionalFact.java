package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NutritionalFact extends BaseEntity {
    public UUID menuItemId;
    public String description;
    public double value;
    public String unit;

    public NutritionalFact() {
    }

    public NutritionalFact(ResultSet resultSet) throws SQLException {
        super(resultSet);

        menuItemId = UUID.fromString(resultSet.getString("menuItemId"));
        description = resultSet.getString("description");
        value = resultSet.getDouble("value");
        unit = resultSet.getString("unit");
    }
}