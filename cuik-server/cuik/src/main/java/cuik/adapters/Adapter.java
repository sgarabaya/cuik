package cuik.adapters;

import cuik.adapters.sql.SqlClient;

public abstract class Adapter {

    protected final SqlClient client;

    public Adapter(SqlClient client) {
        this.client = client;
    }

    protected abstract String getTableName();
}
