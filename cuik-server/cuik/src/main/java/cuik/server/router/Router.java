package cuik.server.router;

import cuik.utilities.Tuple;
import java.util.ArrayList;
import java.util.List;

public class Router {

    private final RouteNode root = new RouteNode();
    private RouteHandler fallback;

    public Router() {
        this.fallback = null;
    }

    public void setFallback(RouteHandler fallback) {
        this.fallback = fallback;
    }

    public void addRoute(String method, String path, RouteHandler handler) {
        var route = Route.from(method, trimSlash(path), handler);

        var node = root;
        for (var part : route.parts()) {
            if (!node.children.containsKey(part))
                node.children.put(
                        part,
                        new RouteNode());
            node = node.children.get(part);
        }
        node.route = route;
    }

    public Tuple<List<String>, Route> match(String method, String url) {
        var params = new ArrayList<String>();
        var node = root.children.getOrDefault(new RoutePart(method), null);

        if (node == null) {
            return null;
        }

        var fragments = trimSlash(url).trim().split("/");
        for (var fragment : fragments) {
            var part = new RoutePart(fragment);
            if (node.children.containsKey(part)) {
                node = node.children.get(part);
            } else if (node.children.containsKey(RoutePart.wildcard)) {
                params.add(fragment);
                node = node.children.get(RoutePart.wildcard);
            } else {
                break;
            }
        }

        if (node == null || node.route == null) {
            if (fallback != null)
                return new Tuple<>(
                        params,
                        new Route(null, null, fallback));
            return null;
        }

        return new Tuple<>(params, node.route);
    }

    private static String trimSlash(String path) {
        if (path.startsWith("/"))
            path = path.substring(1);

        if (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);

        return path;
    }
}
