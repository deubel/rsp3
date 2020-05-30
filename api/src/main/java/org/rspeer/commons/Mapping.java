package org.rspeer.commons;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 * Mapping utilities
 */
public class Mapping {

    /**
     * @param supplier The supplier
     * @param function The function to apply
     * @param fallback The value to return if the supplied arg is null
     * @param <T>      argument type
     * @param <R>      return type
     * @return Applies a single argument function to the supplied argument
     * and returns the result if the arg is not null, else returns the fallback value
     */
    public static <T, R> R orDefault(Supplier<T> supplier, Function<T, R> function, R fallback) {
        T value = supplier.get();
        return value != null ? function.apply(value) : fallback;
    }

    public static <T, R> R orNull(Supplier<T> supplier, Function<T, R> function) {
        return orDefault(supplier, function, null);
    }

    public static <T> boolean orElse(Supplier<T> supplier, ToBooleanFunction<T> function, boolean fallback) {
        T value = supplier.get();
        return value != null ? function.applyAsBoolean(value) : fallback;
    }

    public static <T> boolean orElse(Supplier<T> supplier, ToBooleanFunction<T> function) {
        return orElse(supplier, function, false);
    }

    public static <T> int orDefault(Supplier<T> supplier, ToIntFunction<T> function, int fallback) {
        T value = supplier.get();
        return value != null ? function.applyAsInt(value) : fallback;
    }

    public static <T> int orM1(Supplier<T> supplier, ToIntFunction<T> function) {
        return orDefault(supplier, function, -1);
    }

    public static <T> void ifPresent(Supplier<T> supplier, Consumer<T> consumer) {
        T value = supplier.get();
        if (value != null) {
            consumer.accept(value);
        }
    }
}
