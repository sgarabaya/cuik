package cuik.server;

import org.eclipse.jetty.http.HttpMethod;

import cuik.data.Role;
import cuik.exceptions.CuikValidationException;
import cuik.server.router.RouteHandler;
import cuik.server.router.Router;

public class AuthenticatedHandler implements RouteHandler {

    private final RouteHandler delegate;
    private final Role requiredRole;
    private final HttpMethod httpMethod;
    private final String path;

    public AuthenticatedHandler(RouteHandler delegate, Role requiredRole,
            HttpMethod httpMethod, String path) {
        this.delegate = delegate;
        this.requiredRole = requiredRole;
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public void register(Router router) {
        router.addRoute(httpMethod.asString(), path, this);
    }

    @Override
    public void func(HttpContext context) {
        if (requiredRole != null) {
            try {
                JwtAuth.authorize(context, requiredRole);
            } catch (CuikValidationException ex) {
                context.respondError(ex.getStatus(), ex.getMessage());
                return;
            }
        }

        delegate.func(context);
    }
}
