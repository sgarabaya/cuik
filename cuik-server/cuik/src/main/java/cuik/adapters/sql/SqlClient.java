package cuik.adapters.sql;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cuik.utilities.Configuration;
import cuik.utilities.CuikInternalException;

public class SqlClient {
    private static HashMap<Class<?>, Constructor<?>> cache = new HashMap<>();

    private Connection connection;

    public void connect() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(Configuration.getConnectionString());
    }

    private Constructor<?> getConstructor(Class<?> classT) throws CuikInternalException {
        try {
            if (!cache.containsKey(classT))
                cache.put(classT, classT.getConstructor(ResultSet.class));

            return cache.get(classT);
        } catch (NoSuchMethodException ex) {
            throw new CuikInternalException("Mappable model needs an empty constructor", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> query(Class<T> classT, String query, Object... parameters) throws CuikInternalException {
        try {
            var constructor = getConstructor(classT);

            var statement = connection.prepareStatement(query);
            for (var i = 0; i < parameters.length; i += 1) {
                switch (parameters[i].getClass().getTypeName()) {
                    case "String":
                        break;
                }

                statement.setString(0, (String) parameters[0]);
            }

            var resultSet = statement.executeQuery();

            var results = new ArrayList<T>();

            while (resultSet.next())
                results.add((T) constructor.newInstance(resultSet));

            return results;
        } catch (SQLException ex) {
            throw new CuikInternalException(String.format("SQL Exception: %s", ex.getMessage()), ex);
        } catch (Exception ex) {
            throw new CuikInternalException(String.format("Unexpected exception: %s", ex.getMessage()), ex);
        }
    }
}
