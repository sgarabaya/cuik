package cuik.server;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import cuik.controllers.UserController;
import cuik.server.annotations.Controller;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.server.router.RouteHandler;
import cuik.server.router.Router;
import cuik.utilities.Container;
import cuik.utilities.Tuple;

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

    public ServerBuilder useController(Class<UserController> controller) throws RuntimeException {
        if (!controller.isAnnotationPresent(Controller.class))
            throw new RuntimeException(controller.getName() + " is not a valid Controller");

        var controllerAnnotation = controller.getAnnotation(Controller.class);
        var pathRoot = controllerAnnotation.value();

        for (var method : controller.getMethods()) {
            var base = pathRoot;
            var handler = createHandler(controller, method);

            var getAnnotation = method.getAnnotation(Get.class);
            if (getAnnotation != null) {
                var path = concatPaths(base, getAnnotation.value());
                router.addRoute("GET", path, handler);
                logger.log(Level.INFO,
                        String.format("Registered [%s] %s to %s::%s", "GET", path, controller.getName(),
                                method.getName()));
            }

            var postAnnotation = method.getAnnotation(Post.class);
            if (method.isAnnotationPresent(Post.class)) {
                var path = concatPaths(base, postAnnotation.value());
                router.addRoute("POST", path, handler);
                logger.log(Level.INFO,
                        String.format("Registered [%s] %s to %s::%s", "POST", path, controller.getName(),
                                method.getName()));
            }
        }

        return this;
    }

    private static String concatPaths(String base, String path) {
        if (path == null || path.isEmpty())
            return base.trim();

        return Path.of(base.trim(), path.trim()).toString();
    }

    private RouteHandler createHandler(Class<?> controller, Method method) {
        var methodParameters = method.getParameters();
        var parameterAnnotations = new ArrayList<Tuple<String, Boolean>>();

        for (int i = 0; i < methodParameters.length; i += 1) {
            var param = methodParameters[i];

            if (param.isAnnotationPresent(FromBody.class)) {
                parameterAnnotations.add(new Tuple<>(param.getName(), true));
            } else {
                String name = param.getName();
                parameterAnnotations.add(new Tuple<>(name, false));
            }
        }

        RouteHandler handler = (HttpContext context) -> {
            try {
                context.logger.log(Level.INFO,
                        String.format("%s> [%s]%s", LocalDateTime.now().toString(), context.getMethod(),
                                context.getFullPath()));

                var ctl = Container.build(controller);

                var paramValues = new Object[parameterAnnotations.size()];

                for (int i = 0; i < parameterAnnotations.size(); i += 1) {
                    var param = parameterAnnotations.get(i);

                    if (param.b()) {
                        if (context.isJson())
                            paramValues[i] = context.parseBody(param.getClass());
                    } else {
                        System.out.println("Param: " + param.a());
                        System.out.println(context.getQueryParam(param.a()));
                        paramValues[i] = context.getQueryParam(param.a());
                    }
                }

                context.respond(method.invoke(ctl, paramValues));
            } catch (Exception ex) {
                context.logger.log(Level.SEVERE, "Execution failure", ex);
                context.respond(ex);
            }
        };

        return handler;
    }

    public Server build() {
        return new Server(port, router);
    }
}
