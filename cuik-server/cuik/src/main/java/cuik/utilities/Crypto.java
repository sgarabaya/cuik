package cuik.utilities;

import de.mkammerer.argon2.Argon2Factory;

// TODO: review
// https://codingtechroom.com/tutorial/java-using-argon2-password-hashing-java
public abstract class Crypto {
    public static String hash(String password) {
        return Argon2Factory.create().hash(10, 65536, 1, Transform.toBytes(password));
    }

    public static boolean verify(String passwordHash, String password) {
        return Argon2Factory.create().verify(passwordHash, Transform.toBytes(password));
    }
}
