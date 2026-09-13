package cuik.utilities;

import java.sql.ResultSet;
import java.util.Arrays;

public abstract class Mapper {
    public <T> T mapFromResultSet(ResultSet resultSet, Class<T> classT) {
        var constructors = classT.getConstructors();
        Arrays.sort(constructors, (a, b) -> a.getParameterCount() - b.getParameterCount());

        return null;
    }
}
