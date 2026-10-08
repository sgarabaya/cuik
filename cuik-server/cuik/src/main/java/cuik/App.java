package cuik;

import cuik.adapters.MenuAdapter;
import cuik.controllers.AuthController;
import cuik.controllers.MenuController;
import cuik.controllers.UserController;
import cuik.controllers.ViewsController;
import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.SqlClient;
import cuik.data.UserRepository;
import cuik.server.ServerBuilder;
import cuik.utilities.Configuration;
import cuik.utilities.Container;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws Exception {
        Configuration.loadConfig();

        initializeDependencies();
        setupCleanupThread();

        var server = new ServerBuilder()
                .usePort(8080)
                .useController(ViewsController.class)
                .useController(UserController.class)
                .useController(AuthController.class)
                .useController(MenuController.class)
                .useStaticFiles("/static")
                .build();

        server.start();
        System.out.println(
                "\n**************************\n" +
                        "Server started!\n" +
                        "Listening @ localhost:8080" +
                        "\n**************************\n");
        server.await();
    }

    public static void setupCleanupThread() {
        var executor = Executors.newSingleThreadScheduledExecutor();

        var periodicTask = new Runnable() {
            public void run() {
                // Invoke method(s) to do the work
            }
        };

        executor.scheduleAtFixedRate(periodicTask, 0, 30, TimeUnit.SECONDS);
    }

    private static void initializeDependencies() {
        Container.register(SqlClient.class);

        // repos
        Container.register(UserRepository.class);
        Container.register(MenuRepository.class);
        Container.register(MenuItemRepository.class);

        // adapters
        Container.register(MenuAdapter.class);

        // controllers
        Container.register(ViewsController.class);
        Container.register(AuthController.class);
        Container.register(UserController.class);
        Container.register(MenuController.class);
    }
}
