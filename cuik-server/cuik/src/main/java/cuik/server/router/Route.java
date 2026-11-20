package cuik.server.router;

import java.util.ArrayList;

import cuik.utilities.Messages;

public record Route(RoutePart[] parts, String[] params, RouteHandler handler) {
    public static Route from(String method, String path, RouteHandler handler) {
        if (path == null) throw new IllegalArgumentException(Messages.PathCannotBeNull);

        if (handler == null) throw new IllegalArgumentException(Messages.HandlerCannotBeNull);

        var fragments = path.trim().split("/");

        var parts = new ArrayList<RoutePart>();
        var params = new ArrayList<String>();

        // Method is always part of the route
        parts.add(new RoutePart(method));

        for (String fragment : fragments) {
            if (fragment.startsWith("{") && fragment.endsWith("}")) {
                parts.add(RoutePart.wildcard);
                params.add(fragment.substring(1, fragment.length() - 1));
            } else parts.add(new RoutePart(fragment));
        }
        return new Route(
            parts.toArray(new RoutePart[0]),
            params.toArray(new String[0]),
            handler
        );
    }
}
