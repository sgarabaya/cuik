package cuik;

import cuik.adapters.UserAdapter;
import cuik.controllers.UserController;
import cuik.server.ServerBuilder;
import cuik.utilities.Container;

public class App {
    public static void main(String[] args) throws Exception {
        initializeDependencies();

        var server = new ServerBuilder()
                .usePort(8080)
                .useController(UserController.class)
                .build();

        server.start();
    }

    private static void initializeDependencies() {
        Container.register(UserAdapter.class);
        Container.register(UserController.class);
    }
}
