package cuik.data;

import cuik.utilities.CuikInternalException;
import cuik.utilities.Strings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

record FieldInfo(String name, Field field, String typeName) {}

record TypeInfo(Constructor<?> constructor, List<FieldInfo> fields) {}

public abstract class AutoMapper {

    private static Map<Class<?>, TypeInfo> _typeCache =
        new ConcurrentHashMap<>();

    public static List<String> getColumns(Class<?> classT)
        throws CuikInternalException {
        return getTypeInfo(classT)
            .fields()
            .stream()
            .map(f -> f.name())
            .toList();
    }

    private static TypeInfo getTypeInfo(Class<?> classT)
        throws CuikInternalException {
        if (_typeCache.containsKey(classT)) {
            return _typeCache.get(classT);
        }

        var constructor = Arrays.stream(classT.getConstructors())
            .filter(c -> c.getParameters().length == 0)
            .findFirst();

        if (constructor.isEmpty()) throw new CuikInternalException(
            String.format(
                "Type %s needs a public, empty constructor",
                classT.getSimpleName()
            )
        );

        var columns = new ArrayList<FieldInfo>();
        for (var field : classT.getDeclaredFields()) {
            var column = field.getAnnotation(Column.class);
            if (column == null) continue;
            var columnName = Strings.emptyOr(column.value(), field.getName());

            field.setAccessible(true);
            columns.add(
                new FieldInfo(
                    columnName,
                    field,
                    field.getType().getSimpleName()
                )
            );
        }

        var typeInfo = new TypeInfo(constructor.get(), columns);

        _typeCache.put(classT, typeInfo);
        return typeInfo;
    }

    private static Map<String, Object> getValues(ResultSet rs)
        throws SQLException {
        var map = new HashMap<String, Object>();
        var md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i += 1) {
            var label = md.getColumnLabel(i);
            var value = rs.getObject(i);
            map.put(label, value);
        }

        return map;
    }

    public static <T> T map(Class<T> classT, ResultSet rs)
        throws CuikInternalException {
        try {
            var typeInfo = getTypeInfo(classT);

            var obj = typeInfo.constructor().newInstance();
            var values = getValues(rs);

            for (var fieldInfo : typeInfo.fields()) {
                var field = fieldInfo.field();
                if (values.containsKey(fieldInfo.name())) {
                    var value = values.get(fieldInfo.name());

                    if (field.getType() == UUID.class) {
                        field.set(obj, UUID.fromString((String) value));
                    } else field.set(obj, value);
                }
            }

            return classT.cast(obj);
        } catch (
            SQLException
            | InvocationTargetException
            | InstantiationException
            | IllegalAccessException ex
        ) {
            throw new CuikInternalException(
                String.format(
                    "Error while trying to map %s",
                    classT.getSimpleName()
                ),
                ex
            );
        }
    }
}
