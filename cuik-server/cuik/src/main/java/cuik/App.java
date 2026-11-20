package cuik;

import cuik.adapters.MenuAdapter;
import cuik.controllers.AllergenController;
import cuik.controllers.AuthController;
import cuik.controllers.EmployeeController;
import cuik.controllers.MenuController;
import cuik.controllers.MenuItemController;
import cuik.controllers.PurchaseController;
import cuik.controllers.UserController;
import cuik.data.AllergenRepository;
import cuik.data.EmployeeRepository;
import cuik.data.MenuItemRepository;
import cuik.data.MenuRepository;
import cuik.data.PurchaseRepository;
import cuik.data.SqlClient;
import cuik.data.UserRepository;
import cuik.server.ServerBuilder;
import cuik.utilities.Configuration;
import cuik.utilities.CuikLogger;
import cuik.utilities.container.Container;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        Configuration.loadConfig();

        initializeDependencies();
        setupCleanupThread();

        var server = new ServerBuilder()
                .usePort(8080)
                .useController(AllergenController.class)
                .useController(AuthController.class)
                .useController(UserController.class)
                .useController(EmployeeController.class)
                .useController(MenuController.class)
                .useController(MenuItemController.class)
                .useController(PurchaseController.class)
                .useStaticFiles()
                .build();

        server.start();
        System.out.println("\n**************************\n"
                + "Server started!\n"
                + "Listening @ localhost:8080"
                + "\n**************************\n");

        server.await();
    }

    public static void setupCleanupThread() {
        var executor = Executors.newSingleThreadScheduledExecutor();

        var periodicTask = new Runnable() {
            public void run() {
                CuikLogger.flush();
            }
        };

        executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
    }

    private static void initializeDependencies() {
        Container.register(SqlClient.class);

        // repos
        Container.register(AllergenRepository.class);
        Container.register(UserRepository.class);
        Container.register(EmployeeRepository.class);
        Container.register(MenuRepository.class);
        Container.register(MenuItemRepository.class);
        Container.register(PurchaseRepository.class);

        // adapters
        Container.register(MenuAdapter.class);

        // controllers
        Container.register(AuthController.class);
        Container.register(AllergenController.class);
        Container.register(UserController.class);
        Container.register(EmployeeController.class);
        Container.register(MenuController.class);
        Container.register(MenuItemController.class);
        Container.register(PurchaseController.class);
    }
}
