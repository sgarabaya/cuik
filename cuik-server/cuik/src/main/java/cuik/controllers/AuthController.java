package cuik.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cuik.data.UserRepository;
import cuik.data.models.User;
import cuik.data.pojo.AuthRequest;
import cuik.data.pojo.RegisterRequest;
import cuik.data.pojo.RegisterResponse;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Post;
import cuik.utilities.Configuration;
import cuik.utilities.Crypto;
import java.util.UUID;

@Controller("/api/auth")
public class AuthController {

    private final UserRepository repository;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    private String authenticate(User user, String password) throws Exception {
        if (Crypto.verify(user.getPasswordHash(), password)) {
            var algorithm = Algorithm.HMAC512(Configuration.getJwtSecret());
            return JWT.create().sign(algorithm);
        }

        return null;
    }

    @Post("login")
    public String login(@FromBody AuthRequest request) throws Exception {
        var name = request.name();
        var password = request.password();

        var user = repository.findByName(name);

        if (user == null) return null; // El usuario no existe

        return authenticate(user, password);
    }

    @Post("register")
    public RegisterResponse register(@FromBody RegisterRequest request)
        throws Exception {
        var user = repository.findByEmail(request.email());

        if (user != null) return null; // El usuario ya existe

        user = new User();
        user.setId(UUID.randomUUID());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(Crypto.hash(request.password()));

        repository.create(user);

        var token = authenticate(user, request.password());

        return new RegisterResponse(user.getId(), token);
    }
}
