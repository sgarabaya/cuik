package cuik.server;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpMethod;

import cuik.controllers.BaseController;
import cuik.exceptions.CuikInternalException;
import cuik.server.annotations.Controller;
import cuik.server.annotations.Delete;
import cuik.server.annotations.Get;
import cuik.server.annotations.Post;
import cuik.server.annotations.Put;
import cuik.server.annotations.View;

public abstract class ControllerHandlerBuilder {

    public static List<ControllerHandler> from(Class<?> controller) throws CuikInternalException {
        if (!controller.isAnnotationPresent(Controller.class) || !BaseController.class.isAssignableFrom(controller))
            throw new CuikInternalException(controller.getName() + " is not a valid Controller");

        var handlers = new ArrayList<ControllerHandler>();

        var controllerAnnotation = controller.getAnnotation(Controller.class);
        var basePath = controllerAnnotation.value();

        for (var method : controller.getMethods()) {
            var viewAnnotation = method.getAnnotation(View.class);
            if (viewAnnotation != null)
                handlers.add(createHandler(controller, method, HttpMethod.GET, basePath, viewAnnotation.value()));
            var getAnnotation = method.getAnnotation(Get.class);
            if (getAnnotation != null)
                handlers.add(createHandler(controller, method, HttpMethod.GET, basePath, getAnnotation.value()));
            var postAnnotation = method.getAnnotation(Post.class);
            if (postAnnotation != null)
                handlers.add(createHandler(controller, method, HttpMethod.POST, basePath, postAnnotation.value()));
            var putAnnotation = method.getAnnotation(Put.class);
            if (putAnnotation != null)
                handlers.add(createHandler(controller, method, HttpMethod.PUT, basePath, putAnnotation.value()));
            var deleteAnnotation = method.getAnnotation(Delete.class);
            if (deleteAnnotation != null)
                handlers.add(createHandler(controller, method, HttpMethod.DELETE, basePath, deleteAnnotation.value()));
        }

        return handlers;
    }

    private static String combinePaths(String base, String path) {
        if (path == null || path.isEmpty())
            return base.trim();

        return Path.of(base.trim(), path.trim()).toString();
    }

    private static ControllerHandler createHandler(
            Class<?> controller, Method method,
            HttpMethod http, String basePath, String path) {
        return new ControllerHandler(controller, method, http, combinePaths(basePath, path));
    }
}
