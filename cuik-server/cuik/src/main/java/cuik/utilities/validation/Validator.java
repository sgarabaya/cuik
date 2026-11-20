package cuik.utilities.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import cuik.exceptions.CuikValidationException;
import cuik.utilities.Messages;

public final class Validator<T> {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final T value;
    private final String field;
    private final List<String> errors = new ArrayList<>();

    private Validator(T value, String field) {
        this.value = value;
        this.field = field == null || field.isBlank() ? "value" : field;
    }

    public static <T> Validator<T> of(T value, String field) {
        return new Validator<>(value, field);
    }

    public Validator<T> notNull() {
        if (value == null)
            add(Messages.ValidationRequired, field);
        return this;
    }

    public Validator<T> notBlank() {
        if (value instanceof String text && text.isBlank())
            add(Messages.ValidationBlank, field);
        return this;
    }

    public Validator<T> minLength(int min) {
        if (value instanceof String text && text.length() < min)
            add(Messages.ValidationMinLength, field, min);
        return this;
    }

    public Validator<T> maxLength(int max) {
        if (value instanceof String text && text.length() > max)
            add(Messages.ValidationMaxLength, field, max);
        return this;
    }

    public Validator<T> lengthBetween(int min, int max) {
        return minLength(min).maxLength(max);
    }

    public Validator<T> uuid() {
        if (value == null)
            return this;

        boolean valid = value instanceof UUID;
        if (!valid && value instanceof String text) {
            try {
                UUID.fromString(text);
                valid = true;
            } catch (IllegalArgumentException ignored) {
                // reported below
            }
        }

        if (!valid)
            add(Messages.ValidationInvalidUuid, field);
        return this;
    }

    public Validator<T> email() {
        if (value instanceof String text && !EMAIL.matcher(text).matches())
            add(Messages.ValidationInvalidEmail, field);
        return this;
    }

    public Validator<T> min(double min) {
        if (value instanceof Number number && number.doubleValue() < min)
            add(Messages.ValidationMinValue, field, min);
        return this;
    }

    public Validator<T> max(double max) {
        if (value instanceof Number number && number.doubleValue() > max)
            add(Messages.ValidationMaxValue, field, max);
        return this;
    }

    public Validator<T> positive() {
        if (value instanceof Number number && number.doubleValue() <= 0)
            add(Messages.ValidationPositive, field);
        return this;
    }

    public Validator<T> matches(Predicate<T> predicate, String message) {
        if (value != null && !predicate.test(value))
            errors.add(message);
        return this;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> errors() {
        return List.copyOf(errors);
    }

    public void check() throws CuikValidationException {
        if (!errors.isEmpty())
            throw new CuikValidationException(String.join("; ", errors));
    }

    private void add(String template, Object... args) {
        errors.add(String.format(template, args));
    }
}
