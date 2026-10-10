package cuik.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Session;
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

    public String getPath() {
        return request.getHttpURI().getPath().toString();
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
        if (request.getLength() > 10 * 1024 * 1024)
            return null; // if the content-length is >10M, reject it

        var json = Content.Source.asString(request, StandardCharsets.UTF_8);
        return Transform.fromJson(json, classT);
    }

    public boolean hasSession() {
        return this.request.getSession(false) != null;
    }

    public Session getSession() {
        return this.request.getSession(true);
    }

    public void setContentType(String contentType) {
        response.getHeaders().put("Content-Type", contentType);
    }

    // Respond with an error
    public void respondError(Throwable ex) {
        var stream = new ByteArrayOutputStream();
        var pStream = new PrintStream(stream);
        ex.printStackTrace(pStream);
        response.setStatus(500);
        respond(stream.toByteArray());
    }

    public void respondView(String view) {
        setContentType("text/html");
        response.setStatus(200);
        respond(Transform.toBytes(view));
    }

    public <T> void respond(T obj) {
        respond(obj, 200);
    }

    public <T> void respond(T obj, int status) {
        if (obj == null) {
            response.setStatus(204);
            respond(ByteBuffer.allocate(0));
        } else {
            setContentType("application/json");
            response.setStatus(status);
            respond(Transform.toJsonBytes(obj));
        }
    }

    public void respondNotFound() {
        response.setStatus(404);
        setContentType("text/html");
        respond(Transform.toBytes("Not found"));
    }

    public void respond(byte[] bytes, String mimeType) {
        setContentType(mimeType);
        response.setStatus(200);
        respond(bytes);
    }

    private void respond(byte[] bytes) {
        response.write(true, ByteBuffer.wrap(bytes), callback);
    }
}
