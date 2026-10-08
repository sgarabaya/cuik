package cuik.data;

import cuik.data.models.BaseEntity;
import cuik.utilities.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    }

    protected Connection connect() throws Exception {
        return DriverManager.getConnection(
                Configuration.getConnectionString(),
                Configuration.getDatabaseUser(),
                Configuration.getDatabasePassword());
    }

    protected <R> List<R> queryRaw(Class<R> classT, String query, Object... params) throws Exception {
        var conn = connect();
        try {
            var stmt = preparedStatement(conn, query, params);

            var result = new ArrayList<R>();

            var rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(AutoMapper.map(classT, rs));
            }

            return result;
        } finally {
            conn.close();
        }
    }

    protected List<T> query(String query, Object... params) throws Exception {
        return queryRaw(getModelClass(), query, params);
    }

    protected T querySingle(String query, Object... params) throws Exception {
        var conn = connect();
        try {
            var stmt = preparedStatement(conn, query, params);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return AutoMapper.map(getModelClass(), rs);
            }
            return null;
        } finally {
            conn.close();
        }
    }

    protected int execute(String query, Object... params) throws Exception {
        var conn = connect();
        try {
            var stmt = preparedStatement(conn, query, params);
            return stmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public T findById(UUID id) throws Exception {
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

    public boolean create(T obj) throws Exception {
        var classColumns = AutoMapper.getColumns(getModelClass());

        var columns = new HashMap<String, Object>();

        for (var column : classColumns) {
            var field = column.field();
            var value = field.getType().cast(field.get(obj));
            if (value != null)
                columns.put(column.name(), value);
        }

        var columnNames = String.join(",", columns.keySet().stream().map(k -> String.format("`%s`", k)).toList());
        var valuePlaceholders = String.join(",", columns.keySet().stream().map(c -> "?").toList());
        var query = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                getTableName(), columnNames, valuePlaceholders);

        return execute(query, columns.values().toArray()) == 1;
    }
}
