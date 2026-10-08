package cuik.data;

import cuik.exceptions.CuikInternalException;
import cuik.utilities.Configuration;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SqlClient {

    private static HashMap<Class<?>, Constructor<?>> cache = new HashMap<>();

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(
                Configuration.getConnectionString(),
                Configuration.getDatabaseUser(),
                Configuration.getDatabasePassword());
    }

    private Constructor<?> getConstructor(Class<?> classT)
            throws CuikInternalException {
        try {
            if (!cache.containsKey(classT))
                cache.put(
                        classT,
                        classT.getConstructor(ResultSet.class));

            return cache.get(classT);
        } catch (NoSuchMethodException ex) {
            throw new CuikInternalException(
                    "Mappable model needs an empty constructor",
                    ex);
        }
    }

    private PreparedStatement prepareStatement(
            Connection connection,
            String query,
            Object... parameters) throws SQLException {
        var statement = connection.prepareStatement(query);
        for (var i = 0; i < parameters.length; i += 1) {
            switch (parameters[i].getClass().getSimpleName()) {
                case "Boolean":
                    statement.setBoolean(i + 1, (boolean) parameters[i]);
                    break;
                case "Integer":
                    statement.setInt(i + 1, (int) parameters[i]);
                    break;
                case "Long":
                    statement.setLong(i + 1, (long) parameters[i]);
                    break;
                case "Double":
                    statement.setDouble(i + 1, (double) parameters[i]);
                    break;
                case "Float":
                    statement.setFloat(i + 1, (float) parameters[i]);
                    break;
                case "String":
                    statement.setString(i + 1, (String) parameters[i]);
                    break;
            }
        }

        return statement;
    }

    public <T> T querySingle(
            Class<T> classT,
            String query,
            Object... parameters) throws CuikInternalException {
        try {
            var connection = connect();
            var constructor = getConstructor(classT);
            var statement = prepareStatement(connection, query, parameters);
            var resultSet = statement.executeQuery();

            T result = null;

            if (resultSet.next()) {
                result = classT.cast(constructor.newInstance(resultSet));
            }

            connection.close();

            return result;
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format("SQL Exception: %s", ex.getMessage()),
                    ex);
        } catch (Exception ex) {
            throw new CuikInternalException(
                    String.format("Unexpected exception: %s", ex.getMessage()),
                    ex);
        }
    }

    public <T> List<T> query(
            Class<T> classT,
            String query,
            Object... parameters) throws CuikInternalException {
        try {
            var connection = connect();
            var constructor = getConstructor(classT);
            var statement = prepareStatement(connection, query, parameters);
            var resultSet = statement.executeQuery();

            var results = new ArrayList<T>();

            while (resultSet.next())
                results.add(classT.cast(constructor.newInstance(resultSet)));

            connection.close();

            return results;
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format("SQL Exception: %s", ex.getMessage()),
                    ex);
        } catch (Exception ex) {
            throw new CuikInternalException(
                    String.format("Unexpected exception: %s", ex.getMessage()),
                    ex);
        }
    }

    public void exec(String query, Object... parameters)
            throws CuikInternalException {
        try {
            prepareStatement(connect(), query, parameters).execute();
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format("SQL Exception: %s", ex.getMessage()),
                    ex);
        }
    }

    public <T> T read(Class<T> classT, String table, UUID id)
            throws CuikInternalException, SQLException {
        return querySingle(
                classT,
                String.format("SELECT * FROM %s WHERE id = ?", table),
                id.toString());
    }

    public <T> T readBy(Class<T> classT, String table, String key, String value)
            throws CuikInternalException, SQLException {
        return querySingle(
                classT,
                String.format("SELECT * FROM %s WHERE %s = ?", table, key),
                value);
    }
}
