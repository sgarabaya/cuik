package cuik.controllers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import cuik.data.EmployeeRepository;
import cuik.data.UserRepository;
import cuik.data.models.Employee;
import cuik.data.models.User;
import cuik.data.pojo.AuthRequest;
import cuik.data.pojo.RegisterRequest;
import cuik.data.pojo.RegisterResponse;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Post;
import cuik.utilities.Configuration;
import cuik.utilities.Crypto;
import cuik.utilities.Messages;
import cuik.utilities.validation.Validator;

@Controller("/api/auth")
public class AuthController extends BaseController {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public AuthController(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    private <T extends User> String authenticate(T entity, String password) throws Exception {
        if (Crypto.verify(entity.getPasswordHash(), password)) {
            var algorithm = Algorithm.HMAC512(Configuration.getJwtSecret());

            var jwt = JWT.create();
            jwt.withExpiresAt(LocalDateTime.now().plusDays(5).toInstant(ZoneOffset.UTC));

            jwt.withClaim("id", entity.getId().toString());
            jwt.withClaim("email", entity.getEmail());
            jwt.withClaim("name", entity.getName());

            if (entity.getClass() == Employee.class) {
                jwt.withClaim("role", ((Employee) entity).getIsAdmin() ? "admin" : "employee");
            } else
                jwt.withClaim("role", "user");

            return jwt.sign(algorithm);
        }

        throw new CuikValidationException(Messages.InvalidLogin);
    }

    @Post("login")
    public String login(@FromBody AuthRequest request) throws Exception {
        Validator.of(request, "solicitud").notNull().check();

        var email = request.email();
        var password = request.password();

        Validator.of(email, "email").notNull().notBlank().email().check();
        Validator.of(password, "contraseña").notNull().notBlank().check();

        // si el email tiene cuik.food, es un empleado
        if (email.endsWith("@cuik.food")) {
            var employee = employeeRepository.findByEmail(email);

            if (employee == null) // el empleado no existe
                throw new CuikValidationException(Messages.UserDoesntExist);

            return authenticate(employee, password);
        } else {
            var user = userRepository.findByEmail(email);

            if (user == null) // el usuario no existe
                throw new CuikValidationException(Messages.UserDoesntExist);

            return authenticate(user, password);
        }
    }

    @Post("register")
    public RegisterResponse register(@FromBody RegisterRequest request)
            throws Exception {
        Validator.of(request, "solicitud").notNull().check();

        Validator.of(request.name(), "nombre").notNull().notBlank().lengthBetween(2, 256).check();
        Validator.of(request.email(), "email").notNull().notBlank().email().maxLength(256).check();
        Validator.of(request.password(), "contraseña").notNull().notBlank().lengthBetween(8, 128).check();

        var user = userRepository.findByEmail(request.email());

        if (user != null) // el usuario ya esta registrado
            throw new CuikValidationException(Messages.UserAlreadyExists);

        user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(Crypto.hash(request.password()));

        var id = userRepository.create(user);

        var token = authenticate(user, request.password());

        return new RegisterResponse(id, token);
    }
}
