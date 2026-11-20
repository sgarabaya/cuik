package cuik.data;

import cuik.data.models.BaseEntity;
import cuik.exceptions.CuikInternalException;
import cuik.utilities.Configuration;
import cuik.utilities.Messages;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public abstract class Repository<T extends BaseEntity> {

    protected SqlClient client;

    public Repository(SqlClient client) {
        this.client = client;
    }

    protected abstract Class<T> getModelClass();

    protected abstract String getTableName();

    protected PreparedStatement preparedStatement(
            Connection conn,
            String query,
            Object... args) throws Exception {
        var statement = conn.prepareStatement(query);
        try {
            for (var i = 0; i < args.length; i += 1) {
                switch (args[i].getClass().getSimpleName()) {
                    case "Boolean":
                        statement.setBoolean(i + 1, (boolean) args[i]);
                        break;
                    case "Integer":
                        statement.setInt(i + 1, (int) args[i]);
                        break;
                    case "Long":
                        statement.setLong(i + 1, (long) args[i]);
                        break;
                    case "Double":
                        statement.setDouble(i + 1, (double) args[i]);
                        break;
                    case "Float":
                        statement.setFloat(i + 1, (float) args[i]);
                        break;
                    case "String":
                        statement.setString(i + 1, (String) args[i]);
                        break;
                    case "UUID":
                        statement.setString(i + 1, args[i].toString());
                        break;
                }
            }

            return statement;
        } catch (Exception ex) {
            statement.close();
            throw ex;
        }
    }

    protected Connection connect() throws Exception {
        return DriverManager.getConnection(
                Configuration.getConnectionString(),
                Configuration.getDatabaseUser(),
                Configuration.getDatabasePassword());
    }

    protected List<T> queryMultiple(Function<Statement, List<T>> fn, String query, Object... params) throws Exception {
        try (var conn = connect();
                var stmt = preparedStatement(conn, query, params)) {
            if (stmt.execute())
                return fn.apply(stmt);
            return new ArrayList<T>();
        }
    }

    protected <A, B, C> List<A> queryMultiple(Class<A> classA, Class<B> classB, Class<C> classC, String query,
            Object... params) {
        return null;
    }

    protected <R> List<R> queryRaw(Class<R> classT, String query, Object... params) throws Exception {
        try (var conn = connect();
                var stmt = preparedStatement(conn, query, params);
                var rs = stmt.executeQuery()) {
            var result = new ArrayList<R>();

            while (rs.next())
                result.add(AutoMapper.map(classT, rs));

            return result;
        }
    }

    protected List<T> query(String query, Object... params) throws Exception {
        return queryRaw(getModelClass(), query, params);
    }

    protected T querySingle(String query, Object... params) throws Exception {
        try (var conn = connect();
                var stmt = preparedStatement(conn, query, params);
                var rs = stmt.executeQuery()) {
            if (rs.next())
                return AutoMapper.map(getModelClass(), rs);
            return null;
        }
    }

    protected int execute(String query, Object... params) throws Exception {
        try (var conn = connect();
                var stmt = preparedStatement(conn, query, params)) {
            return stmt.executeUpdate();
        }
    }

    public T fetchById(UUID id) throws Exception {
        var query = String.format(
                "SELECT * FROM %s WHERE id = ?;",
                getTableName());

        return querySingle(query, id.toString());
    }

    public List<T> fetch() throws Exception {
        return query(String.format("SELECT * FROM %s;", getTableName()));
    }

    public boolean delete(UUID id) throws Exception {
        return (execute(
                String.format("DELETE FROM %s WHERE id = ?;", getTableName()),
                id.toString()) == 1);
    }

    public UUID create(T obj) throws Exception {
        var columns = new HashMap<String, Object>();

        for (var column : AutoMapper.getColumns(getModelClass())) {
            var value = column.field().get(obj);
            if (value != null)
                columns.put(column.name(), value);
        }

        try (var conn = connect()) {
            var id = UUID.randomUUID();

            obj.setId(id);
            columns.put("id", id);

            var columnNames = String.join(",", columns.keySet().stream().map(k -> String.format("`%s`", k)).toList());
            var valuePlaceholders = String.join(",", columns.keySet().stream().map(c -> "?").toList());
            var query = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)",
                    getTableName(), columnNames, valuePlaceholders);

            try (var stmt = preparedStatement(conn, query, columns.values().toArray())) {
                if (stmt.executeUpdate() != 1)
                    throw new CuikInternalException(
                            String.format(Messages.CouldNotCreateEntity, getModelClass().getSimpleName()));
            }
        }

        return obj.getId();
    }

    private static final Set<String> READ_ONLY_COLUMNS = Set.of("id", "created", "updated");

    public boolean update(T obj) throws Exception {
        if (obj.getId() == null)
            throw new IllegalArgumentException(Messages.CannotUpdateWithoutId);

        var columns = new HashMap<String, Object>();

        for (var column : AutoMapper.getColumns(getModelClass())) {
            if (READ_ONLY_COLUMNS.contains(column.name()))
                continue;

            var value = column.field().get(obj);
            if (value != null)
                columns.put(column.name(), value);
        }

        if (columns.isEmpty())
            return false;

        var assignments = String.join(",", columns.keySet().stream()
                .map(c -> String.format("`%s` = ?", c))
                .toList());

        var query = String.format("UPDATE %s SET %s WHERE id = ?;", getTableName(), assignments);

        var params = new ArrayList<>(columns.values());
        params.add(obj.getId());

        return execute(query, params.toArray()) == 1;
    }
}
