package cuik.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import cuik.utilities.Transform;

public class HttpContext {
    private final Request request;
    private final Response response;
    private final Callback callback;
    private final Map<String, String> params;

    public final Logger logger;

    public HttpContext(Logger logger, Request request, Response response, Callback callback,
            Map<String, String> params) {
        this.logger = logger;
        this.request = request;
        this.response = response;
        this.callback = callback;

        this.params = new HashMap<String, String>(params);
        this.params.putAll(getQueryParams());
    }

    public String getMethod() {
        return request.getMethod();
    }

    public String getFullPath() {
        return request.getHttpURI().toString();
    }

    private Map<String, String> getQueryParams() {
        var queryParams = new HashMap<String, String>();
        var query = request.getHttpURI().getQuery();
        if (query != null) {
            var pairs = query.split("&");
            for (String pair : pairs) {
                var keyValue = pair.split("=");
                if (keyValue.length > 1) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }

    public <T> T getQueryParam(String key, Class<T> classT) {
        return Transform.fromJson(params.getOrDefault(key, ""), classT);
    }

    public String getQueryParam(String key) {
        return params.getOrDefault(key, null);
    }

    public boolean isJson() {
        return request.getHeaders().get("Content-Type") == "application/json";
    }

    public <T> T parseBody(Class<T> classT) throws Exception {
        if (request.getLength() > 10 * 1024 * 1024) // if the content-length is >10M, reject it
            return null;

        var buffer = Content.Source.asByteBuffer(request).array();
        return Transform.fromJson(buffer, classT);
    }

    // Respond with an error
    public void respond(Exception ex) {
        var stream = new ByteArrayOutputStream();
        var pStream = new PrintStream(stream);
        ex.printStackTrace(pStream);

        response.getHeaders().put("Content-Type", "application/json");
        response.setStatus(500);
        response.write(true, ByteBuffer.wrap(stream.toByteArray()), callback);
    }

    // Respond with the object
    // TODO: Consider: Content.Sink.write(response, true, payload, callback);
    public <T> void respond(T obj) {
        if (obj == null) {
            response.setStatus(204);
            response.write(true, ByteBuffer.allocate(0), callback);
        } else {
            response.getHeaders().put("Content-Type", "application/json");
            response.setStatus(200);
            response.write(true, ByteBuffer.wrap(Transform.toJsonBytes(obj)), callback);
        }
    }
}
