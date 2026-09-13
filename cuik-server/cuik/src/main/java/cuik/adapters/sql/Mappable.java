package cuik.adapters.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Mappable {
    public Mappable() {
    }

    public Mappable(ResultSet resultSet) throws SQLException {
    }
}
