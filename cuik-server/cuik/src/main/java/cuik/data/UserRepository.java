package cuik.data;

import cuik.data.models.User;

public class UserRepository extends Repository<User> {

    public UserRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }

    @Override
    protected String getTableName() {
        return "Users";
    }

    public User findByEmail(String email) throws Exception {
        return querySingle("SELECT * FROM Users WHERE email = ?", email);
    }

    public User findByName(String name) throws Exception {
        return querySingle("SELECT * FROM Users WHERE name = ?", name);
    }
}
