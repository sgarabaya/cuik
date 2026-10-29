package cuik.server;

import cuik.exceptions.CuikInternalException;
import cuik.server.router.Router;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerBuilder {

    private final Logger logger;

    private final Router router = new Router();

    private int port = 8080;

    public ServerBuilder() {
        this.logger = Logger.getLogger("cuik:ServerBuilder");
    }

    public ServerBuilder usePort(int port) {
        this.port = port;
        return this;
    }

    public ServerBuilder useController(Class<?> controller) throws CuikInternalException {
        for (var handler : ControllerHandlerBuilder.from(controller))
            handler.register(router);
        return this;
    }

    public ServerBuilder useStaticFiles() {
        var staticHandler = new StaticFileHandler();
        router.setFallback(staticHandler);
        logger.log(
                Level.INFO,
                "Registered Static File Handler");
        return this;
    }

    public Server build() {
        return new Server(port, router);
    }
}
