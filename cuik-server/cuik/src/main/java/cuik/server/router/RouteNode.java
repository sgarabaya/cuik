package cuik.server.router;

import java.util.HashMap;

public class RouteNode {
    public final HashMap<RoutePart, RouteNode> children = new HashMap<>();
    public Route route;
}
