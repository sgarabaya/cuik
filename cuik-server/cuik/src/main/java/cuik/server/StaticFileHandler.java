package cuik.server;

import cuik.server.router.RouteHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class StaticFileHandler implements RouteHandler {

    private final Path basePath;
    private final Map<String, String> mimeTypes;

    public StaticFileHandler(Path basePath) {
        this.basePath = basePath;
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

        var filePath = basePath.resolve(requestPath).normalize();

        if (!filePath.startsWith(basePath)) {
            context.logger.warning(
                "Potential directory traversal attempt blocked: " + requestPath
            );
            context.respond(null); //TODO: Send 404
            return;
        }

        var mimeType = determineMimeType(filePath);

        if (mimeType == null) {
            context.logger.warning(
                "Could not determine MIME type for file: " + filePath
            );
            mimeType = "application/octet-stream";
        }

        try {
            var fileContent = Files.readAllBytes(filePath);

            context.respond(fileContent, mimeType);
        } catch (IOException e) {
            context.logger.severe(
                "Error reading static file " + filePath + ": " + e.getMessage()
            );
            context.respond(
                new RuntimeException("File not found or read error")
            );
        }
    }

    private String determineMimeType(Path filePath) {
        var fileName = filePath.getFileName().toString();

        if (fileName.isEmpty()) {
            return "application/octet-stream";
        }

        var extension = fileName
            .substring(fileName.lastIndexOf('.') + 1)
            .toLowerCase();

        return mimeTypes.get(extension);
    }
}
