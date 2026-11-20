package cuik.server.annotations;

import java.lang.annotation.*;

import cuik.data.Role;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Authenticate {
    Role value() default Role.Guest;
}
