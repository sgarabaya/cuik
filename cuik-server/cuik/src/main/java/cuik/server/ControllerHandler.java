package cuik.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpMethod;

import cuik.exceptions.CuikInternalException;
import cuik.exceptions.CuikValidationException;
import cuik.server.annotations.FromBody;
import cuik.server.annotations.View;
import cuik.server.router.RouteHandler;
import cuik.server.router.Router;
import cuik.utilities.CuikLogger;
import cuik.utilities.Tuple3;
import cuik.utilities.container.Container;

public class ControllerHandler implements RouteHandler {
    private final Class<?> controller;
    private final Method method;
    private final HttpMethod httpMethod;
    private final String path;

    private final boolean isView;
    private final List<Tuple3<String, Class<?>, Boolean>> params;

    protected ControllerHandler(Class<?> controller, Method method, HttpMethod httpMethod, String path) {
        this.controller = controller;
        this.method = method;
        this.httpMethod = httpMethod;
        this.path = path;

        this.params = new ArrayList<Tuple3<String, Class<?>, Boolean>>();

        for (var param : method.getParameters()) {
            if (param.isAnnotationPresent(FromBody.class)) {
                params.add(new Tuple3<>(param.getName(), param.getType(), true));
            } else {
                String name = param.getName();
                params.add(new Tuple3<>(name, param.getType(), false));
            }
        }

        // si tenemos esta anotacion, es una vista
        this.isView = method.isAnnotationPresent(View.class);
    }

    public void register(Router router) {
        router.addRoute(httpMethod.asString(), path, this);
    }

    @Override
    public void func(HttpContext context) {
        try {
            CuikLogger.info("%s> [%s]%s",
                    LocalDateTime.now().toString(),
                    context.getMethod(),
                    context.getPath());

            var ctl = Container.build(controller);

            var paramValues = new Object[params.size()];

            for (int i = 0; i < params.size(); i += 1) {
                var param = params.get(i);

                if (param.c()) {
                    if (context.isJson())
                        paramValues[i] = context.parseBody(param.b());
                    else
                        throw new CuikInternalException("Should have been JSON?");
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
                CuikLogger.error("Validation Error: %s", inner.getMessage());
                context.respond(validation.getErrorMessage(), validation.getStatus());
            } else {
                CuikLogger.error(inner);
                context.respondError(inner);
            }
        } catch (Exception ex) {
            CuikLogger.error(ex);
            context.respondError(ex);
        }
    }
}
