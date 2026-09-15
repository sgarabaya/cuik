package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MenuCategory extends BaseEntity {
    public UUID menuId;
    public String title;

    public MenuCategory() {
    }

    public MenuCategory(ResultSet resultSet) throws SQLException {
        super(resultSet);
        menuId = UUID.fromString(resultSet.getString("menuId"));
        title = resultSet.getString("title");
    }
}