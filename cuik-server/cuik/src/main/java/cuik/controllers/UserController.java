package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.Role;
import cuik.data.UserRepository;
import cuik.data.models.User;
import cuik.data.pojo.payloads.UserPayload;
import cuik.server.annotations.*;
import cuik.utilities.Crypto;
import cuik.utilities.Transform;
import cuik.utilities.validation.Validator;

@Authenticate(Role.Employee)
@Controller("/api/users")
public class UserController extends CrudController<User, UserRepository> {
    public UserController(UserRepository repository) {
        super(repository);
    }

    @Override
    protected User _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, UserPayload.class);

        Validator.of(payload.name(), "nombre").notNull().notBlank().lengthBetween(2, 256).check();
        Validator.of(payload.email(), "email").notNull().notBlank().email().maxLength(256).check();
        Validator.of(payload.password(), "contraseña").notNull().notBlank().lengthBetween(8, 128).check();

        var user = new User();
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPasswordHash(Crypto.hash(payload.password()));

        return user;
    }

    @Override
    protected User _update(User object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, UserPayload.class);

        if (payload.name() != null) {
            Validator.of(payload.name(), "nombre").notBlank().lengthBetween(2, 256).check();
            object.setName(payload.name());
        }

        if (payload.email() != null) {
            Validator.of(payload.email(), "email").notBlank().email().maxLength(256).check();
            object.setEmail(payload.email());
        }

        if (payload.password() != null) {
            Validator.of(payload.password(), "contraseña").notBlank().lengthBetween(8, 128).check();
            object.setPasswordHash(Crypto.hash(payload.password()));
        }

        return object;
    }
}
