package cuik.server;

import org.eclipse.jetty.session.SessionHandler;
import cuik.server.router.Router;

public class Server {
    private final org.eclipse.jetty.server.Server server;

    public Server(int port, Router router) {
        server = new org.eclipse.jetty.server.Server(port);

        var coreHandler = new ServerHandler(router);
        var sessionHandler = new SessionHandler();
        sessionHandler.setHandler(coreHandler);

        server.setHandler(sessionHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    public void await() throws Exception {
        server.join();
    }
}
