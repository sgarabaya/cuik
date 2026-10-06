package cuik;

import cuik.controllers.AuthController;
import cuik.controllers.UserController;
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
            .useController(UserController.class)
            .useController(AuthController.class)
            .useStaticFiles(Configuration.getStaticDir())
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

        //repos
        Container.register(UserRepository.class);

        //controllers
        Container.register(AuthController.class);
        Container.register(UserController.class);
    }
}
