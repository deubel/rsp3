package org.rspeer.commons;

import java.util.function.Function;

/**
 * Represents a function that produces an boolean-valued result.  This is the
 * {@code boolean}-producing primitive specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsBoolean(Object)}.
 *
 * @param <K> the type of the input to the function
 * @see Function
 */
@FunctionalInterface
public interface ToBooleanFunction<K> {
    boolean applyAsBoolean(K value);
}