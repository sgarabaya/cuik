package cuik.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import cuik.models.pojo.AuthRequest;
import cuik.models.pojo.AuthResponse;
import cuik.models.pojo.RegisterRequest;
import cuik.models.pojo.RegisterResponse;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Post;
import cuik.services.AuthService;

@Controller("/api/auth")
public class AuthController {
    private final Logger logger = Logger.getLogger("AuthController");
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Post("login")
    public AuthResponse login(@FromBody AuthRequest request) {
        try {
            var token = service.authenticateUser(request.name(), request.password());
            return new AuthResponse(token);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "", ex);
            return null;
        }
    }

    @Post("register")
    public RegisterResponse register(@FromBody RegisterRequest request) {
        var registered = service.registerUser(request.name(), request.email(), request.password());

        return new RegisterResponse(registered.a().id.toString(), registered.b());
    }
}
