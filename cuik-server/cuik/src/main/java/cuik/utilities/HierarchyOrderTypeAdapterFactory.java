package cuik.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HierarchyOrderTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        var delegate = gson.getDelegateAdapter(this, type);
        var elementAdapter = gson.getAdapter(JsonElement.class);

        var rawType = type.getRawType();

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                var tree = delegate.toJsonTree(value);

                if (tree.isJsonObject()) {
                    var original = tree.getAsJsonObject();
                    var sorted = new JsonObject();

                    var hierarchy = new ArrayList<Class<?>>();
                    for (
                        var c = rawType;
                        c != null && c != Object.class;
                        c = c.getSuperclass()
                    ) {
                        hierarchy.add(c);
                    }
                    Collections.reverse(hierarchy);

                    for (var c : hierarchy) {
                        for (var field : c.getDeclaredFields()) {
                            var annotation = field.getAnnotation(
                                SerializedName.class
                            );
                            String fieldName =
                                annotation != null
                                    ? annotation.value()
                                    : field.getName();

                            if (original.has(fieldName)) {
                                sorted.add(
                                    fieldName,
                                    original.remove(fieldName)
                                );
                            }
                        }
                    }

                    for (var entry : original.entrySet()) {
                        sorted.add(entry.getKey(), entry.getValue());
                    }

                    elementAdapter.write(out, sorted);
                } else {
                    delegate.write(out, value);
                }
            }

            @Override
            public T read(JsonReader in) throws IOException {
                return delegate.read(in);
            }
        };
    }
}
