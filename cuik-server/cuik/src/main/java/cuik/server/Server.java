package cuik.server;

import cuik.server.router.Router;

public class Server {
    private final org.eclipse.jetty.server.Server server;

    public Server(int port, Router router) {
        server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(new ServerHandler(router));
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }
}
