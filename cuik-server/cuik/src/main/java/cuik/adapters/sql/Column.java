package cuik.adapters.sql;

import java.lang.annotation.*;

enum SqlType {
    NONE,
    ARRAY,
    BIGINT,
    BINARY,
    BIT,
    BLOB,
    BOOLEAN,
    CHAR,
    DATE,
    DECIMAL,
    DOUBLE,
    FLOAT,
    INTEGER,
    NULL,
    NVARCHAR,
    REAL,
    TIME,
    TIME_WITH_TIMEZONE,
    TIMESTAMP,
    TIMESTAMP_WITH_TIMEZONE,
    TINYINT,
    VARBINARY,
    VARCHAR,
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    public String value();

    public int sqlType() default -1;

    public int length() default -1;
}
