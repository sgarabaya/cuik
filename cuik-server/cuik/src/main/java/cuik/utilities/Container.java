package cuik.utilities;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Container {
    private static final Map<Class<?>, Callable<?>> registry = new ConcurrentHashMap<>();

    public static void register(Class<?> classT) throws ContainerException {
        registry.put(classT, createBuildFn(classT));
    }

    public static void register(Class<?> interfaceClassT, Class<?> implementationClassT) throws ContainerException {
        registry.put(interfaceClassT, createBuildFn(implementationClassT));
    }

    public static void registerSingleton(Class<?> classT, Object instance) {
        registry.put(classT, () -> instance);
    }

    public static void registerCustom(Class<?> classT, Callable<?> factory) {
        registry.put(classT, factory);
    }

    private static Callable<?> createBuildFn(Class<?> classT) throws ContainerException {
        Constructor<?> constructor = findConstructor(classT);

        return () -> {
            try {
                Class<?>[] params = constructor.getParameterTypes();
                Object[] args = new Object[params.length];

                for (int i = 0; i < args.length; i += 1)
                    args[i] = build(params[i]);

                return constructor.newInstance(args);
            } catch (Exception e) {
                throw new ContainerException("Failed to instantiate " + classT.getName(), e);
            }
        };
    }

    public static <T> T build(Class<T> classT) throws ContainerException {
        try {
            var buildFn = registry.get(classT);

            if (buildFn == null)
                throw new ContainerException(String.format("Failed to instantiate type %s", classT.getName()));

            return classT.cast(buildFn.call());
        } catch (Exception ex) {
            throw new ContainerException(String.format("Failed to build %s", classT.getName()), ex);
        }
    }

    private static Constructor<?> findConstructor(Class<?> classT) throws ContainerException {
        Constructor<?> bestConstructor = null;
        for (var constructor : classT.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                constructor.setAccessible(true);
                return constructor; // if it's annotated just exit
            }

            if (bestConstructor == null)
                bestConstructor = constructor;
            else if (bestConstructor.getParameterCount() < constructor.getParameterCount())
                bestConstructor = constructor;
        }

        if (bestConstructor == null)
            throw new ContainerException("Dependency injection needs a constructor");

        bestConstructor.setAccessible(true);
        return bestConstructor;
    }
}
