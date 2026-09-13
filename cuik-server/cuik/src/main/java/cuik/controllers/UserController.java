package cuik.controllers;

import java.util.List;

import cuik.adapters.UserAdapter;
import cuik.models.User;
import cuik.server.annotations.*;

@Controller("/api/users")
public class UserController {
    private final UserAdapter adapter;

    public UserController(UserAdapter adapter) {
        this.adapter = adapter;
    }

    @Get
    public List<User> getUsers() {
        return adapter.getAll();
    }

    @Get("{id}")
    public User getUserById(String id) {
        return adapter.getById(id);
    }

    @Post
    public User createUser(@FromBody User user) {
        System.out.println(user);
        return user;
    }
}
