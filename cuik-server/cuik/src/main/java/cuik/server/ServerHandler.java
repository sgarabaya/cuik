package cuik.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import cuik.server.router.Router;

public class ServerHandler extends Handler.Abstract {
    private final Router router;

    public ServerHandler(Router router) {
        this.router = router;
    }

    private Map<String, String> mapParams(String[] keys, List<String> values) {
        var params = new HashMap<String, String>();

        for (int i = 0; i < keys.length; i += 1) {
            var value = i < values.size() ? values.get(i) : null;
            params.put(keys[i], value);
        }

        return params;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) {
        var method = request.getMethod().toUpperCase();
        var path = request.getHttpURI().getPath();

        var matchResult = router.match(method, path);

        if (matchResult == null) {
            return false;
        }

        var route = matchResult.b();
        var params = mapParams(route.params(), matchResult.a());

        var logger = Logger.getLogger(String.format("[%s] %s", method, path));

        var context = new HttpContext(logger, request, response, callback, params);

        route.handler().func(context);

        return true;
    }
}
