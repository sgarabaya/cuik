package cuik.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu extends BaseEntity {
    public String title;
    public boolean published;

    public Menu() {
    }

    public Menu(ResultSet resultSet) throws SQLException {
        super(resultSet);
        title = resultSet.getString("title");
        published = resultSet.getBoolean("published");
    }
}