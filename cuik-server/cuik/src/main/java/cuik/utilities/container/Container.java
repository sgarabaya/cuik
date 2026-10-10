package cuik.utilities.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import cuik.exceptions.ContainerException;

public abstract class Container {

    private static final Map<Class<?>, Callable<?>> registry = new ConcurrentHashMap<>();

    public static void register(Class<?> classT) throws ContainerException {
        registry.put(classT, createBuildFn(classT));
    }

    public static void register(
            Class<?> interfaceClassT,
            Class<?> implementationClassT) throws ContainerException {
        registry.put(interfaceClassT, createBuildFn(implementationClassT));
    }

    public static void registerSingleton(Class<?> classT, Object instance) {
        registry.put(classT, () -> instance);
    }

    public static void registerCustom(Class<?> classT, Callable<?> factory) {
        registry.put(classT, factory);
    }

    private static Callable<?> createBuildFn(Class<?> classT) throws ContainerException {
        var constructor = findConstructor(classT);
        var injectableFields = findInjectables(classT);

        return () -> {
            try {
                var params = constructor.getParameterTypes();
                var args = new Object[params.length];

                for (int i = 0; i < args.length; i += 1)
                    args[i] = build(params[i]);

                var instance = constructor.newInstance(args);
                for (var field : injectableFields)
                    field.set(instance, build(field.getClass()));

                return instance;
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

    private static List<Field> findInjectables(Class<?> classT) throws ContainerException {
        try {
            var fields = new ArrayList<Field>();

            var currentClass = classT;
            while (currentClass != null && currentClass != Object.class) {
                for (var field : currentClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        field.setAccessible(true);
                        fields.add(field);
                    }
                }
                currentClass = currentClass.getSuperclass();
            }

            return fields;
        } catch (Exception ex) {
            throw new ContainerException(ex);
        }
    }

    private static Constructor<?> findConstructor(Class<?> classT) throws ContainerException {
        Constructor<?> bestConstructor = null;
        for (var constructor : classT.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(PrimaryConstructor.class)) {
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
