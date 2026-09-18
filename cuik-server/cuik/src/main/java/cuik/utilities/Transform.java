package cuik.utilities;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.Gson;

public abstract class Transform {
    private static final Gson gson = new Gson();

    public static String fromBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    public static <T> T fromJson(byte[] bytes, Class<T> classT) {
        return fromJson(fromBytes(bytes), classT);
    }

    public static <T> T fromJson(String string, Class<T> classT) {
        return gson.fromJson(string, classT);
    }

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static <T> byte[] toJsonBytes(T object) {
        return toBytes(toJson(object));
    }

    public static String toBase64(byte[] bytes) {
        return fromBytes(Base64.getEncoder().encode(bytes));
    }

    public static byte[] fromBase64(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public static UUID bytesToUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] uuidToBytes(UUID id) {
        var byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(id.getMostSignificantBits());
        byteBuffer.putLong(id.getLeastSignificantBits());
        return byteBuffer.array();
    }
}
