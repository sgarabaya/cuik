package cuik.controllers;

import cuik.data.UserRepository;
import cuik.data.models.User;
import cuik.server.annotations.*;
import java.util.List;
import java.util.UUID;

@Controller("/api/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Get
    public List<User> getUsers() throws Exception {
        return repository.fetch();
    }

    @Get("{id}")
    public User getUserById(String id) throws Exception {
        return repository.findById(UUID.fromString(id));
    }

    @Post
    public User createUser(@FromBody User user) {
        System.out.println(user);
        return user;
    }
}
