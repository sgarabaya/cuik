package cuik.services;

import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import cuik.adapters.UserAdapter;
import cuik.models.User;
import cuik.utilities.Configuration;
import cuik.utilities.Crypto;
import cuik.utilities.Tuple;

public class AuthService {
    private final UserAdapter userAdapter;

    public AuthService(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public String authenticateUser(String name, String password) {
        var user = userAdapter.findByUsername(name);

        if (user == null)
            return null; // El usuario no existe

        if (Crypto.verify(user.passwordHash, password)) {
            try {
                var algorithm = Algorithm.HMAC512(Configuration.getJwtSecret());
                return JWT.create().sign(algorithm);
            } catch (JWTCreationException ex) {
                System.out.println(ex.getMessage());
                return null;
            }
        }

        // Si el usuario no existe
        return null;
    }

    public Tuple<User, String> registerUser(String name, String email, String password) {
        try {
            var user = userAdapter.findByUsername(name);

            if (user != null)
                return null; // El usuario ya existe

            user = new User();

            user.id = UUID.randomUUID();
            user.name = name;
            user.email = email;
            user.passwordHash = Crypto.hash(password);

            userAdapter.create(user);

            var token = authenticateUser(name, password);

            return new Tuple<>(user, token);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
