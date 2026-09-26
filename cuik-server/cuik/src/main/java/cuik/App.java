package cuik;

import cuik.adapters.EmployeeAdapter;
import cuik.adapters.UserAdapter;
import cuik.adapters.sql.SqlClient;
import cuik.controllers.AuthController;
import cuik.controllers.UserController;
import cuik.server.ServerBuilder;
import cuik.services.AuthService;
import cuik.utilities.Configuration;
import cuik.utilities.Container;

public class App {

    public static void main(String[] args) throws Exception {
        Configuration.loadConfig();

        initializeDependencies();

        var server = new ServerBuilder()
            .usePort(8080)
            .useController(UserController.class)
            .useController(AuthController.class)
            .build();

        server.start();
        System.out.println(
            "\n**************************\n" +
                "Server started!\n" +
                "Listening @ localhost:8080" +
                "\n**************************\n"
        );
        server.await();
    }

    private static void initializeDependencies() {
        Container.register(SqlClient.class);
        Container.register(AuthService.class);
        Container.register(AuthController.class);
        Container.register(UserAdapter.class);
        Container.register(EmployeeAdapter.class);
        Container.register(UserController.class);
    }
}
