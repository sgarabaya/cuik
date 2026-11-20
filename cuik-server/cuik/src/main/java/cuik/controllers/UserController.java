package cuik.controllers;

import com.google.gson.JsonElement;

import cuik.data.UserRepository;
import cuik.data.models.User;
import cuik.data.pojo.payloads.UserPayload;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.*;
import cuik.utilities.Crypto;
import cuik.utilities.Messages;
import cuik.utilities.Strings;
import cuik.utilities.Transform;

@Controller("/api/users")
public class UserController extends CrudController<User, UserRepository> {
    public UserController(UserRepository repository) {
        super(repository);
    }

    @Override
    protected User _create(JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, UserPayload.class);

        if (payload.password() == null || payload.password().isBlank())
            throw new CuikValidationException(Messages.PasswordIsRequired);

        var user = new User();
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setPasswordHash(Crypto.hash(payload.password()));

        return user;
    }

    @Override
    protected User _update(User object, JsonElement element) throws Exception {
        var payload = Transform.fromJsonElement(element, UserPayload.class);

        if (!Strings.isEmpty(payload.name()))
            object.setName(payload.name());

        if (!Strings.isEmpty(payload.email()))
            object.setEmail(payload.email());

        if (!Strings.isEmpty(payload.password()))
            object.setPasswordHash(Crypto.hash(payload.password()));

        return object;
    }
}
