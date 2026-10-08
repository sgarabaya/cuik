package cuik.server;

import cuik.exceptions.CuikInternalException;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.server.annotations.View;
import cuik.server.router.RouteHandler;
import cuik.server.router.Router;
import cuik.utilities.Container;
import cuik.utilities.Strings;
import cuik.utilities.Tuple3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerBuilder {

    private final Logger logger;

    private final Router router = new Router();

    private int port = 8080;

    public ServerBuilder() {
        this.logger = Logger.getLogger("cuik:ServerBuilder");
    }

    public ServerBuilder usePort(int port) {
        this.port = port;
        return this;
    }

    public ServerBuilder useController(Class<?> controller)
            throws RuntimeException {
        if (!controller.isAnnotationPresent(Controller.class))
            throw new RuntimeException(
                    controller.getName() + " is not a valid Controller");

        var controllerAnnotation = controller.getAnnotation(Controller.class);
        var pathRoot = controllerAnnotation.value();

        for (var method : controller.getMethods()) {
            var base = pathRoot;
            var handler = createHandler(controller, method);

            var getAnnotation = method.getAnnotation(Get.class);
            if (getAnnotation != null) {
                var path = concatPaths(base, getAnnotation.value());
                router.addRoute("GET", path, handler);
                logger.log(
                        Level.INFO,
                        String.format(
                                "Registered [%s] %s to %s::%s",
                                "GET",
                                path,
                                controller.getName(),
                                method.getName()));
            }

            var postAnnotation = method.getAnnotation(Post.class);
            if (postAnnotation != null) {
                var path = concatPaths(base, postAnnotation.value());
                router.addRoute("POST", path, handler);
                logger.log(
                        Level.INFO,
                        String.format(
                                "Registered [%s] %s to %s::%s",
                                "POST",
                                path,
                                controller.getName(),
                                method.getName()));
            }

            var viewAnnotation = method.getAnnotation(View.class);
            if (viewAnnotation != null) {
                var path = concatPaths(
                        base,
                        Strings.emptyOr(viewAnnotation.value(), "/"));
                router.addRoute("GET", path, handler);
                logger.log(
                        Level.INFO,
                        String.format(
                                "Registered VIEW [%s] %s to %s::%s",
                                "GET",
                                path,
                                controller.getName(),
                                method.getName()));
            }
        }

        return this;
    }

    public ServerBuilder useStaticFiles(String basePath) {
        var staticHandler = new StaticFileHandler(basePath);
        router.setFallback(staticHandler);
        logger.log(
                Level.INFO,
                "Registered Static File Handler for base path: " + basePath);
        return this;
    }

    private static String concatPaths(String base, String path) {
        if (path == null || path.isEmpty())
            return base.trim();

        return Path.of(base.trim(), path.trim()).toString();
    }

    private RouteHandler createHandler(Class<?> controller, Method method) {
        var methodParameters = method.getParameters();
        var parameterAnnotations = new ArrayList<Tuple3<String, Class<?>, Boolean>>();

        for (int i = 0; i < methodParameters.length; i += 1) {
            var param = methodParameters[i];

            if (param.isAnnotationPresent(FromBody.class)) {
                parameterAnnotations.add(
                        new Tuple3<>(param.getName(), param.getType(), true));
            } else {
                String name = param.getName();
                parameterAnnotations.add(
                        new Tuple3<>(name, param.getType(), false));
            }
        }

        // si tenemos esta anotacion, es una vista
        var isView = method.isAnnotationPresent(View.class);

        RouteHandler handler = (HttpContext context) -> {
            try {
                context.logger.log(
                        Level.INFO,
                        String.format(
                                "%s> [%s]%s",
                                LocalDateTime.now().toString(),
                                context.getMethod(),
                                context.getPath()));

                var ctl = Container.build(controller);

                var paramValues = new Object[parameterAnnotations.size()];

                for (int i = 0; i < parameterAnnotations.size(); i += 1) {
                    var param = parameterAnnotations.get(i);

                    if (param.c()) {
                        if (context.isJson())
                            paramValues[i] = context.parseBody(param.b());
                        else
                            throw new CuikInternalException(
                                    "Should have been JSON?");
                    } else {
                        System.out.println("Param: " + param.a());
                        System.out.println(context.getQueryParam(param.a()));
                        paramValues[i] = context.getQueryParam(param.a());
                    }
                }

                if (isView) {
                    context.respondView((String) method.invoke(ctl, paramValues));
                } else {
                    context.respond(method.invoke(ctl, paramValues));
                }

            } catch (InvocationTargetException ex) {
                var inner = ex.getCause();

                if (CuikValidationException.class.isAssignableFrom(inner.getClass())) {
                    var validation = (CuikValidationException) inner;
                    context.logger.log(Level.INFO, String.format("Validation Error: %s", inner.getMessage()));
                    context.respond(validation.getErrorMessage(), validation.getStatus());
                } else {
                    context.logger.log(Level.SEVERE, "Execution failure", inner);
                    context.respondError(inner);
                }
            } catch (Exception ex) {
                context.logger.log(Level.SEVERE, "Execution failure", ex);
                context.respondError(ex);
            }
        };

        return handler;
    }

    public Server build() {
        return new Server(port, router);
    }
}
