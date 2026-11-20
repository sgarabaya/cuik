package cuik.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import cuik.exceptions.CuikInternalException;
import cuik.server.router.RouteHandler;
import cuik.utilities.Configuration;
import cuik.utilities.Messages;
import cuik.utilities.Strings;

public class StaticFileHandler implements RouteHandler {

    private final String basePath;
    private final Map<String, String> mimeTypes;

    public StaticFileHandler() throws CuikInternalException {
        var staticDir = Configuration.getStaticDir();

        if (Strings.isEmpty(staticDir))
            throw new CuikInternalException(Messages.MissingStaticPathConfig);

        this.basePath = staticDir;

        this.mimeTypes = new HashMap<String, String>();
        mimeTypes.put("html", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("svg", "image/svg+xml");
        mimeTypes.put("ico", "image/x-icon");
    }

    private byte[] loadFile(String basePath, String resourcePath) throws IOException {
        var relativePath = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
        var fullPath = Paths.get(basePath).resolve(relativePath);

        if (!Files.exists(fullPath) || Files.isDirectory(fullPath))
            return null;

        return Files.readAllBytes(fullPath);
    }

    @Override
    public void func(HttpContext context) {
        var requestPath = context.getPath();

        if (requestPath.contains("..")) {
            context.logger.warning("Potential directory traversal attempt blocked: " + requestPath);
            context.respondNotFound();
            return;
        }

        if (!requestPath.startsWith("/"))
            requestPath = "/" + requestPath;

        if (requestPath.equals("/"))
            requestPath = "/index.html";

        var mimeType = determineMimeType(requestPath);

        if (mimeType == null) {
            context.logger.warning("Could not determine MIME type for file: " + requestPath);
            mimeType = "application/octet-stream";
        }

        try {
            var fileContent = loadFile(basePath, requestPath);

            if (fileContent == null) {
                context.logger.severe("Error reading static content: " + requestPath);
                context.respondNotFound();
            } else
                context.respond(fileContent, mimeType);
        } catch (IOException e) {
            context.logger.severe("Error reading static content: " + e.getMessage());
            context.respondNotFound();
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