package cuik.server.router;

import cuik.server.HttpContext;

public interface RouteHandler {
    public void func(HttpContext context);
}
