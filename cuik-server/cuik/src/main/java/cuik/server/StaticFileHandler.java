package cuik.server;

import cuik.server.router.RouteHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StaticFileHandler implements RouteHandler {

    private final String baseResourcePath;
    private final Map<String, String> mimeTypes;

    public StaticFileHandler(String baseResourcePath) {
        if (!baseResourcePath.startsWith("/")) {
            baseResourcePath = "/" + baseResourcePath;
        }
        if (baseResourcePath.endsWith("/")) {
            baseResourcePath = baseResourcePath.substring(0, baseResourcePath.length() - 1);
        }
        this.baseResourcePath = baseResourcePath;

        this.mimeTypes = new HashMap<String, String>();
        mimeTypes.put("html", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("svg", "image/svg+xml");
    }

    @Override
    public void func(HttpContext context) {
        var requestPath = context.getPath();

        if (requestPath.contains("..")) {
            context.logger.warning(
                    "Potential directory traversal attempt blocked: " + requestPath);
            context.respondNotFound();
            return;
        }

        if (!requestPath.startsWith("/")) {
            requestPath = "/" + requestPath;
        }

        var fullResourcePath = baseResourcePath + requestPath;
        var mimeType = determineMimeType(requestPath);

        if (mimeType == null) {
            context.logger.warning(
                    "Could not determine MIME type for file: " + requestPath);
            mimeType = "application/octet-stream";
        }

        try (InputStream is = getClass().getResourceAsStream(fullResourcePath)) {
            if (is == null) {
                context.logger.warning("Resource not found: " + fullResourcePath);
                context.respondNotFound();
                return;
            }

            var fileContent = is.readAllBytes();
            context.respond(fileContent, mimeType);

        } catch (IOException e) {
            context.logger.severe(
                    "Error reading static resource " + fullResourcePath + ": " + e.getMessage());
            context.respond(
                    new RuntimeException("Resource not found or read error"));
        }
    }

    private String determineMimeType(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "application/octet-stream";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "application/octet-stream"; // No extension found
        }

        var extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        return mimeTypes.get(extension);
    }
}