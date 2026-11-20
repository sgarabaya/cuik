package cuik.data;

import cuik.exceptions.CuikInternalException;
import cuik.utilities.Configuration;
import cuik.utilities.Messages;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class SqlClient {

    private static final ConcurrentHashMap<Class<?>, Constructor<?>> cache = new ConcurrentHashMap<>();

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(
                Configuration.getConnectionString(),
                Configuration.getDatabaseUser(),
                Configuration.getDatabasePassword());
    }

    private Constructor<?> getConstructor(Class<?> classT) throws CuikInternalException {
        var constructor = cache.get(classT);

        if (constructor != null)
            return constructor;

        try {
            constructor = classT.getConstructor(ResultSet.class);
            cache.putIfAbsent(classT, constructor);
            return cache.get(classT);
        } catch (NoSuchMethodException ex) {
            throw new CuikInternalException(
                    Messages.MappableModelNeedsConstructor,
                    ex);
        }
    }

    private PreparedStatement prepareStatement(
            Connection connection,
            String query,
            Object... parameters) throws SQLException {
        var statement = connection.prepareStatement(query);
        try {
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
        } catch (SQLException ex) {
            statement.close();
            throw ex;
        }
    }

    public <T> T querySingle(
            Class<T> classT,
            String query,
            Object... parameters) throws CuikInternalException {
        try (var connection = connect();
                var statement = prepareStatement(connection, query, parameters);
                var resultSet = statement.executeQuery()) {
            var constructor = getConstructor(classT);

            T result = null;

            if (resultSet.next())
                result = classT.cast(constructor.newInstance(resultSet));

            return result;
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format(Messages.SqlException, ex.getMessage()),
                    ex);
        } catch (Exception ex) {
            throw new CuikInternalException(
                    String.format(Messages.UnexpectedException, ex.getMessage()),
                    ex);
        }
    }

    public <T> List<T> query(
            Class<T> classT,
            String query,
            Object... parameters) throws CuikInternalException {
        try (var connection = connect();
                var statement = prepareStatement(connection, query, parameters);
                var resultSet = statement.executeQuery()) {
            var constructor = getConstructor(classT);

            var results = new ArrayList<T>();

            while (resultSet.next())
                results.add(classT.cast(constructor.newInstance(resultSet)));

            return results;
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format(Messages.SqlException, ex.getMessage()),
                    ex);
        } catch (Exception ex) {
            throw new CuikInternalException(
                    String.format(Messages.UnexpectedException, ex.getMessage()),
                    ex);
        }
    }

    public void exec(String query, Object... parameters)
            throws CuikInternalException {
        try (var connection = connect();
                var statement = prepareStatement(connection, query, parameters)) {
            statement.execute();
        } catch (SQLException ex) {
            throw new CuikInternalException(
                    String.format(Messages.SqlException, ex.getMessage()),
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
