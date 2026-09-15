package cuik.server.router;

import java.util.ArrayList;

public record Route(RoutePart[] parts, String[] params, RouteHandler handler) {
    public static Route from(String method, String path, RouteHandler handler) {
        if (path == null)
            throw new IllegalArgumentException("Argument path cannot be null");

        if (handler == null)
            throw new IllegalArgumentException("Argument handler cannot be null");

        var fragments = path.trim().split("/");

        var parts = new ArrayList<RoutePart>();
        var params = new ArrayList<String>();

        // Method is always part of the route
        parts.add(new RoutePart(method));

        for (String fragment : fragments) {
            if (fragment.startsWith("{") && fragment.endsWith("}")) {
                parts.add(RoutePart.wildcard);
                params.add(fragment.substring(1, fragment.length() - 1));
            } else
                parts.add(new RoutePart(fragment));
        }
        return new Route(parts.toArray(new RoutePart[0]), params.toArray(new String[0]), handler);
    }
}
