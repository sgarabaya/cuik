package cuik.controllers;

import cuik.data.UserRepository;
import cuik.data.models.User;
import cuik.data.pojo.UserPayload;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.*;
import cuik.utilities.Crypto;
import java.util.List;
import java.util.UUID;

@Controller("/api/users")
public class UserController extends BaseController {

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
    public UUID createUser(@FromBody UserPayload payload) throws Exception {
        if (payload.password() == null || payload.password().isBlank())
            throw new CuikValidationException("La contrasena es requerida");

        var user = new User();
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPasswordHash(Crypto.hash(payload.password()));

        return repository.create(user);
    }

    @Put
    public boolean updateUser(@FromBody UserPayload payload) throws Exception {
        var user = new User();
        user.setId(payload.id());
        user.setName(payload.name());
        user.setEmail(payload.email());

        if (payload.password() != null && !payload.password().isBlank())
            user.setPasswordHash(Crypto.hash(payload.password()));

        return repository.update(user);
    }

    @Delete("{id}")
    public void deleteUser(String id) throws Exception {
        var userId = UUID.fromString(id);
        repository.delete(userId);
    }
}
