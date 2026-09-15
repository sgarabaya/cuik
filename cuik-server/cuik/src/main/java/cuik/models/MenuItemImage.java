package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MenuItemImage extends BaseEntity {
    public UUID menuItemId;
    public String url;

    public MenuItemImage() {
    }

    public MenuItemImage(ResultSet resultSet) throws SQLException {
        super(resultSet);

        menuItemId = UUID.fromString(resultSet.getString("menuItemId"));
        url = resultSet.getString("url");
    }
}